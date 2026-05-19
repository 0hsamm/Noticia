import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { TipoHoroscopo } from '../enums/tipo-horoscopo.enum';
import { TipoPublicacion } from '../enums/tipo-publicacion.enum';
import { Comentario } from '../models/comentario.model';
import { Horoscopo } from '../models/horoscopo.model';
import { Noticia } from '../models/noticia.model';
import { AuthService } from '../services/auth.service';
import { ComentarioService } from '../services/comentario.service';
import { HoroscopoService } from '../services/horoscopo.service';
import { NoticiaService } from '../services/noticia.service';

/**
 * Componente encargado de la gestión de comentarios
 * realizados por los usuarios comentaristas.
 */
@Component({
  selector: 'app-panel-comentarista',
  standalone: false,
  templateUrl: './panel-comentarista.html',
  styleUrl: './panel-comentarista.css',
})
export class PanelComentarista implements OnInit {

  /**
   * Evento emitido cuando el usuario cierra sesión.
   */
  @Output() addCerrarSesion = new EventEmitter<void>();

  /**
   * Servicios utilizados dentro del componente.
   */
  private readonly noticiaService = inject(NoticiaService);
  private readonly horoscopoService = inject(HoroscopoService);
  private readonly comentarioService = inject(ComentarioService);
  private readonly authService = inject(AuthService);
  private readonly toastr = inject(ToastrService);

  /**
   * Listas principales de información.
   */
  noticias: Noticia[] = [];
  horoscopos: Horoscopo[] = [];
  comentarios: Comentario[] = [];

  /**
   * Vista actual seleccionada en el panel.
   */
  vistaActual: 'NOTICIAS' | 'HOROSCOPOS' | 'MIS_COMENTARIOS' = 'NOTICIAS';

  /**
   * Nombre del usuario con sesión activa.
   */
  nombreSesion = this.authService.obtenerNombreActual() ?? '';

  /**
   * Objeto utilizado como formulario de comentarios.
   */
  formulario: Comentario = this.crearFormulario();

  /**
   * Referencia protegida al enum TipoPublicacion
   * para utilizarlo desde la plantilla HTML.
   */
  protected readonly tiposPublicacion = TipoPublicacion;

  /**
   * Método ejecutado al inicializar el componente.
   * Carga noticias, horóscopos y comentarios.
   */
  ngOnInit(): void {
    this.cargarNoticias();
    this.cargarHoroscopos();
    this.cargarComentarios();
  }

  /**
   * Retorna únicamente los comentarios realizados
   * por el usuario con sesión activa.
   */
  get comentariosPropios(): Comentario[] {
    return this.comentarios.filter((comentario) => comentario.nombreComentarista === this.nombreSesion);
  }

  /**
   * Cambia la vista actual del panel.
   *
   * @param vista Vista seleccionada.
   */
  cambiarVista(vista: 'NOTICIAS' | 'HOROSCOPOS' | 'MIS_COMENTARIOS'): void {

    this.vistaActual = vista;

    /**
     * Ajusta automáticamente el tipo de publicación
     * según la vista seleccionada.
     */
    if (vista === 'NOTICIAS') {
      this.formulario.tipoPublicacion = TipoPublicacion.NOTICIA;
    }

    if (vista === 'HOROSCOPOS') {
      this.formulario.tipoPublicacion = TipoPublicacion.HOROSCOPO;
    }

    /**
     * Asigna un destino por defecto al comentario.
     */
    this.asignarDestinoPorDefecto();
  }

  /**
   * Recarga toda la información del panel.
   */
  refrescarPanel(): void {
    this.cargarNoticias();
    this.cargarHoroscopos();
    this.cargarComentarios();
  }

  /**
   * Guarda un nuevo comentario o actualiza uno existente.
   */
  guardarComentario(): void {

    /**
     * Validación del contenido del comentario.
     */
    if (!this.formulario.contenido.trim()) {
      this.toastr.warning('Debes escribir el comentario.', 'Campos requeridos');
      return;
    }

    /**
     * Validación de noticia seleccionada.
     */
    if (this.formulario.tipoPublicacion === TipoPublicacion.NOTICIA && !this.formulario.tituloNoticia) {
      this.toastr.warning('Selecciona una noticia para comentar.', 'Destino requerido');
      return;
    }

    /**
     * Validación de horóscopo seleccionado.
     */
    if (this.formulario.tipoPublicacion === TipoPublicacion.HOROSCOPO && !this.formulario.horoscopoId) {
      this.toastr.warning('Selecciona un horoscopo para comentar.', 'Destino requerido');
      return;
    }

    /**
     * Obtiene el horóscopo seleccionado actualmente.
     */
    const horoscopoSeleccionado = this.obtenerHoroscopoSeleccionado();

    /**
     * Construcción del objeto final del comentario.
     */
    const payload: Comentario = {
      ...this.formulario,
      contenido: this.formulario.contenido.trim(),
      fecha: this.formulario.fecha || this.generarFechaActual(),
      nombreComentarista: this.nombreSesion,

      tituloNoticia:
        this.formulario.tipoPublicacion === TipoPublicacion.NOTICIA
          ? this.formulario.tituloNoticia
          : '',

      signoHoroscopo:
        this.formulario.tipoPublicacion === TipoPublicacion.HOROSCOPO
          ? horoscopoSeleccionado?.tipoHoroscopo ?? this.formulario.signoHoroscopo
          : this.formulario.signoHoroscopo || TipoHoroscopo.ARIES,

      horoscopoId:
        this.formulario.tipoPublicacion === TipoPublicacion.HOROSCOPO
          ? this.formulario.horoscopoId
          : undefined,
    };

    /**
     * Determina si se debe crear o actualizar.
     */
    const peticion = payload.id
      ? this.comentarioService.updateById(payload.id, payload)
      : this.comentarioService.create(payload);

    /**
     * Ejecución de la petición.
     */
    peticion.subscribe({

      next: (mensaje) => {
        this.toastr.success(mensaje, 'Comentario guardado');
        this.formulario = this.crearFormulario();
        this.asignarDestinoPorDefecto();
        this.cargarComentarios();
      },

      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'No fue posible guardar el comentario');
      },
    });
  }

  /**
   * Carga un comentario dentro del formulario
   * para permitir su edición.
   *
   * @param comentario Comentario seleccionado.
   */
  editarComentario(comentario: Comentario): void {
    this.formulario = { ...comentario };
    this.vistaActual = 'MIS_COMENTARIOS';
  }

  /**
   * Elimina un comentario existente.
   *
   * @param comentario Comentario seleccionado.
   */
  eliminarComentario(comentario: Comentario): void {

    if (!comentario.id) {
      return;
    }

    this.comentarioService.deleteById(comentario.id).subscribe({

      next: (mensaje) => {
        this.toastr.success(mensaje, 'Comentario eliminado');
        this.cargarComentarios();
      },

      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'No fue posible eliminar el comentario');
      },
    });
  }

  /**
   * Restablece el formulario
   * a su estado inicial.
   */
  cancelarEdicion(): void {
    this.formulario = this.crearFormulario();
    this.asignarDestinoPorDefecto();
  }

  /**
   * Cierra la sesión del usuario actual.
   */
  cerrarSesion(): void {
    this.authService.postCerrarSesion().subscribe(() => {
      this.toastr.info('Sesion cerrada', 'Informacion');
      this.addCerrarSesion.emit();
    });
  }

  /**
   * Carga todas las noticias disponibles.
   */
  private cargarNoticias(): void {

    this.noticiaService.getAll().subscribe({

      next: (noticias) => {
        this.noticias = noticias;
        this.asignarDestinoPorDefecto();
      },

      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  /**
   * Carga todos los horóscopos disponibles.
   */
  private cargarHoroscopos(): void {

    this.horoscopoService.getAll().subscribe({

      next: (horoscopos) => {
        this.horoscopos = horoscopos;
        this.asignarDestinoPorDefecto();
      },

      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  /**
   * Carga todos los comentarios registrados.
   */
  private cargarComentarios(): void {

    this.comentarioService.getAll().subscribe({

      next: (comentarios) => {
        this.comentarios = comentarios;
      },

      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  /**
   * Asigna automáticamente un destino por defecto
   * según el tipo de publicación seleccionada.
   */
  private asignarDestinoPorDefecto(): void {

    /**
     * Asigna la primera noticia disponible.
     */
    if (
      this.formulario.tipoPublicacion === TipoPublicacion.NOTICIA &&
      !this.formulario.tituloNoticia &&
      this.noticias.length > 0
    ) {
      this.formulario.tituloNoticia = this.noticias[0].titulo;
    }

    /**
     * Asigna el primer horóscopo disponible.
     */
    if (
      this.formulario.tipoPublicacion === TipoPublicacion.HOROSCOPO &&
      !this.formulario.horoscopoId &&
      this.horoscopos.length > 0
    ) {
      this.formulario.horoscopoId = this.horoscopos[0].id;
      this.formulario.signoHoroscopo = this.horoscopos[0].tipoHoroscopo;
    }
  }

  /**
   * Genera un formulario vacío para inicializar
   * o reiniciar la edición de comentarios.
   */
  private crearFormulario(): Comentario {

    return {
      contenido: '',
      fecha: '',
      nombreComentarista: this.nombreSesion,
      tituloNoticia: '',
      signoHoroscopo: TipoHoroscopo.ARIES,
      horoscopoId: this.horoscopos[0]?.id,
      tipoPublicacion: TipoPublicacion.NOTICIA,
    };
  }

  /**
   * Genera la fecha actual en formato ISO simplificado.
   */
  private generarFechaActual(): string {

    const ahora = new Date();

    const anio = ahora.getFullYear();
    const mes = String(ahora.getMonth() + 1).padStart(2, '0');
    const dia = String(ahora.getDate()).padStart(2, '0');

    const horas = String(ahora.getHours()).padStart(2, '0');
    const minutos = String(ahora.getMinutes()).padStart(2, '0');
    const segundos = String(ahora.getSeconds()).padStart(2, '0');

    return `${anio}-${mes}-${dia}T${horas}:${minutos}:${segundos}`;
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

  /**
   * Retorna el horóscopo seleccionado actualmente.
   */
  private obtenerHoroscopoSeleccionado(): Horoscopo | undefined {

    return this.horoscopos.find(
      (horoscopo) => horoscopo.id === this.formulario.horoscopoId,
    );
  }
}
