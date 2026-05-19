import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { TipoUsuario } from '../enums/tipo-usuario.enum';
import { Usuario } from '../models/usuario.model';
import { AuthService } from '../services/auth.service';
import { UsuarioAdministradorService } from '../services/usuario-administrador.service';
import { UsuarioComentaristaService } from '../services/usuario-comentarista.service';
import { UsuarioEditorService } from '../services/usuario-editor.service';
import { UsuarioNormalService } from '../services/usuario-normal.service';

/**
 * Componente encargado de administrar el panel principal
 * de gestión de usuarios del sistema.
 */
@Component({
  selector: 'app-panel-admin',
  standalone: false,
  templateUrl: './panel-admin.html',
  styleUrl: './panel-admin.css',
})
export class PanelAdmin implements OnInit {

  /**
   * Evento emitido cuando el usuario cierra sesión.
   */
  @Output() addCerrarSesion = new EventEmitter<void>();

  /**
   * Servicios inyectados para la gestión de usuarios,
   * autenticación y notificaciones.
   */
  private readonly usuarioAdministradorService = inject(UsuarioAdministradorService);
  private readonly usuarioEditorService = inject(UsuarioEditorService);
  private readonly usuarioComentaristaService = inject(UsuarioComentaristaService);
  private readonly usuarioNormalService = inject(UsuarioNormalService);
  private readonly authService = inject(AuthService);
  private readonly toastr = inject(ToastrService);

  /**
   * Listas de usuarios organizadas por tipo.
   */
  usuariosAdministradores: Usuario[] = [];
  usuariosEditores: Usuario[] = [];
  usuariosComentaristas: Usuario[] = [];
  usuariosNormales: Usuario[] = [];

  /**
   * Sección actualmente seleccionada en el panel.
   */
  seccionActual: TipoUsuario = TipoUsuario.EDITOR;

  /**
   * Objeto utilizado como formulario de creación y edición.
   */
  formulario: Usuario = this.crearFormularioVacio(TipoUsuario.EDITOR);

  /**
   * Nombre del usuario con sesión activa.
   */
  nombreSesion = this.authService.obtenerNombreActual() ?? 'Administrador';

  /**
   * Identificador utilizado para controlar cargas activas
   * y evitar sobrescritura de datos desactualizados.
   */
  private cargaSeccionId = 0;

  /**
   * Referencia protegida al enum TipoUsuario
   * para ser utilizado en la plantilla HTML.
   */
  protected readonly tiposUsuario = TipoUsuario;

  /**
   * Método ejecutado al inicializar el componente.
   * Carga la sección activa por defecto.
   */
  ngOnInit(): void {
    this.cargarSeccionActiva();
  }

  /**
   * Retorna la lista de usuarios correspondiente
   * a la sección actualmente seleccionada.
   */
  get usuariosActuales(): Usuario[] {
    switch (this.seccionActual) {
      case TipoUsuario.ADMIN:
        return this.usuariosAdministradores;
      case TipoUsuario.COMENTARISTA:
        return this.usuariosComentaristas;
      case TipoUsuario.USUARIO:
        return this.usuariosNormales;
      default:
        return this.usuariosEditores;
    }
  }

  /**
   * Retorna el título dinámico de la sección actual.
   */
  get tituloSeccion(): string {
    switch (this.seccionActual) {
      case TipoUsuario.ADMIN:
        return 'Administradores';
      case TipoUsuario.COMENTARISTA:
        return 'Comentaristas';
      case TipoUsuario.USUARIO:
        return 'Usuarios normales';
      default:
        return 'Editores';
    }
  }

  /**
   * Determina si la sección actual permite gestión
   * de usuarios (crear, editar y eliminar).
   */
  get permiteGestion(): boolean {
    return this.seccionActual === TipoUsuario.EDITOR || this.seccionActual === TipoUsuario.COMENTARISTA;
  }

  /**
   * Cambia la sección actual del panel
   * y recarga la información correspondiente.
   *
   * @param seccion Tipo de usuario seleccionado.
   */
  cambiarSeccion(seccion: TipoUsuario): void {
    this.seccionActual = seccion;
    this.cancelarEdicion();
    this.cargarSeccionActiva();
  }

  /**
   * Recarga manualmente la información
   * de la sección activa.
   */
  refrescarPanel(): void {
    this.cargarSeccionActiva();
  }

  /**
   * Guarda un usuario nuevo o actualiza uno existente.
   * También realiza validaciones básicas del formulario.
   */
  guardarUsuario(): void {

    // Verifica permisos de gestión
    if (!this.permiteGestion) {
      return;
    }

    // Validación de campos obligatorios
    if (!this.formulario.nombre.trim() || !this.formulario.contrasena) {
      this.toastr.warning('Nombre y contrasena son obligatorios.', 'Campos requeridos');
      return;
    }

    /**
     * Construcción del objeto a enviar.
     */
    const payload: Usuario = {
      ...this.formulario,
      nombre: this.formulario.nombre.trim(),
      tipoUsuario: this.seccionActual,
    };

    /**
     * Selección automática entre creación y actualización.
     */
    const peticion = this.formulario.id
      ? this.actualizarUsuario(this.formulario.id, payload)
      : this.crearUsuario(payload);

    /**
     * Ejecución de la petición.
     */
    peticion.subscribe({
      next: (mensaje) => {
        this.toastr.success(mensaje || 'Operacion realizada correctamente', 'Exito');
        this.cancelarEdicion();
        this.cargarSeccionActiva();
      },
      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  /**
   * Carga la información de un usuario
   * dentro del formulario para edición.
   *
   * @param usuario Usuario seleccionado.
   */
  editarUsuario(usuario: Usuario): void {

    if (!this.permiteGestion) {
      return;
    }

    this.formulario = {
      ...usuario,
      tipoUsuario: this.seccionActual,
    };
  }

  /**
   * Elimina un usuario del sistema.
   *
   * @param usuario Usuario seleccionado.
   */
  eliminarUsuario(usuario: Usuario): void {

    if (!this.permiteGestion || !usuario.id) {
      return;
    }

    this.eliminarUsuarioPorId(usuario.id).subscribe({
      next: (mensaje) => {
        this.toastr.success(mensaje || 'Usuario eliminado correctamente', 'Exito');
        this.cargarSeccionActiva();
      },
      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  /**
   * Restablece el formulario
   * a su estado inicial.
   */
  cancelarEdicion(): void {
    this.formulario = this.crearFormularioVacio(this.seccionActual);
  }

  /**
   * Cierra la sesión actual del usuario.
   */
  cerrarSesion(): void {
    this.authService.postCerrarSesion().subscribe(() => {
      this.toastr.info('Sesion cerrada', 'Informacion');
      this.addCerrarSesion.emit();
    });
  }

  /**
   * Carga los usuarios correspondientes
   * a la sección activa.
   *
   * También evita que respuestas antiguas
   * sobrescriban datos actuales.
   */
  private cargarSeccionActiva(): void {

    const seccionSolicitada = this.seccionActual;
    const cargaActual = ++this.cargaSeccionId;

    this.obtenerConsultaActual(seccionSolicitada).subscribe({

      next: (usuarios) => {

        /**
         * Verifica si la respuesta sigue siendo válida.
         */
        if (cargaActual !== this.cargaSeccionId || seccionSolicitada !== this.seccionActual) {
          return;
        }

        /**
         * Asigna los usuarios según la sección.
         */
        switch (seccionSolicitada) {
          case TipoUsuario.ADMIN:
            this.usuariosAdministradores = usuarios;
            break;

          case TipoUsuario.COMENTARISTA:
            this.usuariosComentaristas = usuarios;
            break;

          case TipoUsuario.USUARIO:
            this.usuariosNormales = usuarios;
            break;

          default:
            this.usuariosEditores = usuarios;
        }
      },

      error: (error) => {

        /**
         * Evita mostrar errores de cargas antiguas.
         */
        if (cargaActual !== this.cargaSeccionId) {
          return;
        }

        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  /**
   * Retorna la consulta correspondiente
   * al tipo de usuario seleccionado.
   *
   * @param seccion Tipo de usuario.
   */
  private obtenerConsultaActual(seccion: TipoUsuario): Observable<Usuario[]> {

    switch (seccion) {

      case TipoUsuario.ADMIN:
        return this.usuarioAdministradorService.getAll();

      case TipoUsuario.COMENTARISTA:
        return this.usuarioComentaristaService.getAll();

      case TipoUsuario.USUARIO:
        return this.usuarioNormalService.getAll();

      default:
        return this.usuarioEditorService.getAll();
    }
  }

  /**
   * Crea un nuevo usuario según la sección actual.
   *
   * @param usuario Información del usuario.
   */
  private crearUsuario(usuario: Usuario): Observable<string> {

    return this.seccionActual === TipoUsuario.COMENTARISTA
      ? this.usuarioComentaristaService.create(usuario)
      : this.usuarioEditorService.create(usuario);
  }

  /**
   * Actualiza un usuario existente.
   *
   * @param id Identificador del usuario.
   * @param usuario Información actualizada.
   */
  private actualizarUsuario(id: number, usuario: Usuario): Observable<string> {

    return this.seccionActual === TipoUsuario.COMENTARISTA
      ? this.usuarioComentaristaService.updateById(id, usuario)
      : this.usuarioEditorService.updateById(id, usuario);
  }

  /**
   * Elimina un usuario por su identificador.
   *
   * @param id Identificador del usuario.
   */
  private eliminarUsuarioPorId(id: number): Observable<string> {

    return this.seccionActual === TipoUsuario.COMENTARISTA
      ? this.usuarioComentaristaService.deleteById(id)
      : this.usuarioEditorService.deleteById(id);
  }

  /**
   * Genera un formulario vacío
   * para inicializar o reiniciar la edición.
   *
   * @param tipoUsuario Tipo de usuario inicial.
   */
  private crearFormularioVacio(tipoUsuario: TipoUsuario): Usuario {

    return {
      nombre: '',
      contrasena: '',
      tipoUsuario,
    };
  }

  /**
   * Obtiene un mensaje de error legible
   * a partir de la respuesta recibida.
   *
   * @param error Objeto de error recibido.
   */
  private obtenerMensajeError(error: { error?: unknown }): string {

    if (typeof error?.error === 'string') {
      return error.error;
    }

    return 'No fue posible completar la operacion';
  }
}
