import { Component, OnInit, inject } from '@angular/core';
import { TipoUsuario } from './enums/tipo-usuario.enum';
import { AuthService } from './services/auth.service';

/**
 * Componente principal de la aplicación.
 *
 * Este componente administra:
 * - El estado de autenticación.
 * - La navegación entre login y registro.
 * - La gestión de roles de usuario.
 * - El cierre de sesión.
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css',
})
export class App implements OnInit {

  /**
   * Indica si existe una sesión activa válida.
   */
  sesionValida = false;

  /**
   * Controla si se debe mostrar
   * la vista de creación de cuenta.
   */
  crearCuenta = false;

  /**
   * Almacena el rol actual del usuario autenticado.
   */
  rolActual: TipoUsuario | null = null;

  /**
   * Almacena el nombre del usuario autenticado.
   */
  nombreActual = '';

  /**
   * Exposición del enum TipoUsuario
   * para ser utilizado desde la vista HTML.
   */
  protected readonly tiposUsuario = TipoUsuario;

  /**
   * Servicio encargado de gestionar
   * autenticación y sesiones.
   */
  private readonly authService = inject(AuthService);

  /**
   * Método ejecutado al inicializar el componente.
   *
   * Verifica si existe una sesión activa
   * almacenada localmente.
   */
  ngOnInit(): void {

    /**
     * Si no existe sesión activa,
     * se detiene la ejecución.
     */
    if (!this.authService.haySesionActiva()) {
      return;
    }

    /**
     * Obtiene el rol y nombre almacenados
     * en la sesión local.
     */
    const rolGuardado = this.authService.obtenerRolActual();
    const nombreGuardado = this.authService.obtenerNombreActual();

    /**
     * Si la información de sesión es inválida,
     * se limpia el almacenamiento local.
     */
    if (!rolGuardado || !nombreGuardado) {
      this.authService.limpiarSesionLocal();
      return;
    }

    /**
     * Restablece la sesión activa en la aplicación.
     */
    this.sesionValida = true;
    this.rolActual = rolGuardado;
    this.nombreActual = nombreGuardado;
  }

  /**
   * Actualiza el estado de autenticación
   * cuando el usuario inicia sesión.
   *
   * @param evento Contiene el rol y nombre del usuario autenticado.
   */
  getEstado(evento: { rol: TipoUsuario; nombre: string }): void {
    this.sesionValida = true;
    this.crearCuenta = false;
    this.rolActual = evento.rol;
    this.nombreActual = evento.nombre;
  }

  /**
   * Activa la visualización
   * del formulario de registro.
   */
  getCrearCuenta(): void {
    this.crearCuenta = true;
  }

  /**
   * Regresa a la vista
   * de inicio de sesión.
   */
  getVolverLogIn(): void {
    this.crearCuenta = false;
  }

  /**
   * Cierra la sesión actual del usuario.
   *
   * Limpia la información almacenada
   * y restablece los estados iniciales.
   */
  cerrarSesion(): void {
    this.authService.limpiarSesionLocal();

    this.sesionValida = false;
    this.crearCuenta = false;
    this.rolActual = null;
    this.nombreActual = '';
  }
}
