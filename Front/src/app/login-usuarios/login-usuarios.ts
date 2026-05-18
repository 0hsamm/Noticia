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
  @Output() addEstado = new EventEmitter<{ rol: TipoUsuario; nombre: string }>();
  @Output() addCrearCuenta = new EventEmitter<void>();

  nombre = '';
  contrasena = '';
  cargando = false;

  private readonly authService = inject(AuthService);
  private readonly toastr = inject(ToastrService);

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

  mostrarRegistro(): void {
    this.addCrearCuenta.emit();
  }

  private obtenerMensajeError(error: { error?: unknown }): string {
    if (typeof error?.error === 'string') {
      return error.error;
    }

    return 'No fue posible iniciar sesion';
  }
}
