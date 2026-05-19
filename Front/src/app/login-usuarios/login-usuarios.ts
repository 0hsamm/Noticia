/**
 * Componente de inicio de sesión de usuarios.
 *
 * Permite autenticar un usuario mediante nombre y contraseña,
 * y emite eventos hacia el componente padre según el resultado:
 *
 * - addEstado: emite el rol y nombre del usuario autenticado
 * - addCrearCuenta: solicita abrir el formulario de registro
 *
 * Este componente consume AuthService para realizar la autenticación
 * y utiliza ToastrService para mostrar mensajes al usuario.
 */

import { Component, EventEmitter, Output, inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { TipoUsuario } from '../enums/tipo-usuario.enum';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login-usuarios',
  standalone: false,
  templateUrl: './login-usuarios.html',
  styleUrl: './login-usuarios.css',
})
export class LoginUsuarios {

  /**
   * Emite el estado del usuario autenticado (rol + nombre)
   */
  @Output() addEstado = new EventEmitter<{ rol: TipoUsuario; nombre: string }>();

  /**
   * Emite evento para mostrar la pantalla de registro
   */
  @Output() addCrearCuenta = new EventEmitter<void>();

  nombre = '';
  contrasena = '';
  cargando = false;

  private readonly authService = inject(AuthService);
  private readonly toastr = inject(ToastrService);

  /**
   * Realiza el inicio de sesión del usuario.
   *
   * Valida los campos, llama al servicio de autenticación
   * y emite el resultado al componente padre.
   */
  iniciarSesion(): void {
    if (!this.nombre.trim() || !this.contrasena) {
      this.toastr.warning('Debes ingresar nombre y contrasena.', 'Campos requeridos');
      return;
    }

    this.cargando = true;

    this.authService.postLogin(this.nombre.trim(), this.contrasena).subscribe({
      next: (respuesta) => {
        this.cargando = false;

        const rol = (respuesta.role as TipoUsuario) || TipoUsuario.USUARIO;

        this.toastr.success('Inicio de sesion correcto', 'Exito');

        this.addEstado.emit({
          rol,
          nombre: this.nombre.trim(),
        });
      },

      error: (error) => {
        this.cargando = false;
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  /**
   * Solicita abrir el formulario de registro
   */
  mostrarRegistro(): void {
    this.addCrearCuenta.emit();
  }

  /**
   * Extrae un mensaje legible desde el error del backend
   */
  private obtenerMensajeError(error: { error?: unknown }): string {
    if (typeof error?.error === 'string') {
      return error.error;
    }

    return 'No fue posible iniciar sesion';
  }
}
