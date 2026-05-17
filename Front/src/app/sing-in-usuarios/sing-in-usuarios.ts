import { Component, EventEmitter, Output, inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-sing-in-usuarios',
  standalone: false,
  templateUrl: './sing-in-usuarios.html',
  styleUrl: './sing-in-usuarios.css',
})
export class SingInUsuarios {
  @Output() addVolver = new EventEmitter<void>();

  nombre = '';
  contrasena = '';
  cargando = false;

  private readonly authService = inject(AuthService);
  private readonly toastr = inject(ToastrService);

  registrarUsuario(): void {
    if (!this.nombre.trim() || !this.contrasena) {
      this.toastr.warning('Debes completar nombre y contrasena.', 'Campos requeridos');
      return;
    }

    this.cargando = true;

    this.authService.postRegister(this.nombre.trim(), this.contrasena).subscribe({
      next: (mensaje) => {
        this.cargando = false;
        this.toastr.success(mensaje || 'Cuenta creada correctamente', 'Exito');
        this.addVolver.emit();
      },
      error: (error) => {
        this.cargando = false;
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  volver(): void {
    this.addVolver.emit();
  }

  private obtenerMensajeError(error: { error?: unknown }): string {
    if (typeof error?.error === 'string') {
      return error.error;
    }

    return 'No fue posible crear la cuenta';
  }
}
