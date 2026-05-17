import { Component, OnInit, inject } from '@angular/core';
import { TipoUsuario } from './enums/tipo-usuario.enum';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css',
})
export class App implements OnInit {
  sesionValida = false;
  crearCuenta = false;
  rolActual: TipoUsuario | null = null;
  nombreActual = '';

  protected readonly tiposUsuario = TipoUsuario;
  private readonly authService = inject(AuthService);

  ngOnInit(): void {
    if (!this.authService.haySesionActiva()) {
      return;
    }

    const rolGuardado = this.authService.obtenerRolActual();
    const nombreGuardado = this.authService.obtenerNombreActual();

    if (!rolGuardado || !nombreGuardado) {
      this.authService.limpiarSesionLocal();
      return;
    }

    this.sesionValida = true;
    this.rolActual = rolGuardado;
    this.nombreActual = nombreGuardado;
  }

  getEstado(evento: { rol: TipoUsuario; nombre: string }): void {
    this.sesionValida = true;
    this.crearCuenta = false;
    this.rolActual = evento.rol;
    this.nombreActual = evento.nombre;
  }

  getCrearCuenta(): void {
    this.crearCuenta = true;
  }

  getVolverLogIn(): void {
    this.crearCuenta = false;
  }

  cerrarSesion(): void {
    this.authService.limpiarSesionLocal();
    this.sesionValida = false;
    this.crearCuenta = false;
    this.rolActual = null;
    this.nombreActual = '';
  }
}
