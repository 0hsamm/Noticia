import { Component, EventEmitter, Output, inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../services/auth.service';

/**
 * Componente encargado del registro de usuarios.
 *
 * Permite crear una nueva cuenta mediante nombre y contraseña,
 * mostrando notificaciones visuales según el resultado del proceso.
 */
@Component({
  selector: 'app-sing-in-usuarios',
  standalone: false,
  templateUrl: './sing-in-usuarios.html',
  styleUrl: './sing-in-usuarios.css',
})
export class SingInUsuarios {

  /**
   * Evento emitido para regresar a la vista anterior.
   */
  @Output() addVolver = new EventEmitter<void>();

  /**
   * Nombre ingresado por el usuario.
   */
  nombre = '';

  /**
   * Contraseña ingresada por el usuario.
   */
  contrasena = '';

  /**
   * Indica si el proceso de registro está en ejecución.
   */
  cargando = false;

  /**
   * Servicio encargado de las operaciones de autenticación.
   */
  private readonly authService = inject(AuthService);

  /**
   * Servicio utilizado para mostrar notificaciones emergentes.
   */
  private readonly toastr = inject(ToastrService);

  /**
   * Realiza el proceso de registro del usuario.
   *
   * Valida los campos requeridos y envía la información
   * al backend para crear la cuenta.
   */
  registrarUsuario(): void {

    // Verifica que los campos obligatorios estén completos.
    if (!this.nombre.trim() || !this.contrasena) {
      this.toastr.warning('Debes completar nombre y contrasena.', 'Campos requeridos');
      return;
    }

    // Activa el indicador de carga mientras se procesa la solicitud.
    this.cargando = true;

    // Envía los datos de registro al servicio de autenticación.
    this.authService.postRegister(this.nombre.trim(), this.contrasena).subscribe({

      // Se ejecuta cuando el registro es exitoso.
      next: (mensaje) => {
        this.cargando = false;

        this.toastr.success(mensaje || 'Cuenta creada correctamente', 'Exito');

        // Retorna a la vista anterior después del registro.
        this.addVolver.emit();
      },

      // Se ejecuta cuando ocurre un error durante el registro.
      error: (error) => {
        this.cargando = false;

        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  /**
   * Emite el evento para regresar a la pantalla anterior.
   */
  volver(): void {
    this.addVolver.emit();
  }

  /**
   * Obtiene un mensaje de error legible a partir de la respuesta del backend.
   *
   * @param error Objeto de error recibido.
   * @returns Mensaje de error a mostrar.
   */
  private obtenerMensajeError(error: { error?: unknown }): string {

    // Verifica si el backend devolvió un mensaje de texto.
    if (typeof error?.error === 'string') {
      return error.error;
    }

    // Mensaje genérico en caso de no recibir información específica.
    return 'No fue posible crear la cuenta';
  }
}
