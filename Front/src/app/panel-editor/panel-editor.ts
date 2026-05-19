import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { TipoHoroscopo } from '../enums/tipo-horoscopo.enum';
import { TipoPublicacion } from '../enums/tipo-publicacion.enum';
import { Horoscopo } from '../models/horoscopo.model';
import { Noticia } from '../models/noticia.model';
import { AuthService } from '../services/auth.service';
import { HoroscopoService } from '../services/horoscopo.service';
import { NoticiaService } from '../services/noticia.service';

/**
 * Componente encargado de gestionar las noticias
 * y los horóscopos creados por un editor.
 */
@Component({
  selector: 'app-panel-editor',
  standalone: false,
  templateUrl: './panel-editor.html',
  styleUrl: './panel-editor.css',
})
export class PanelEditor implements OnInit {

  /**
   * Evento emitido al cerrar sesión.
   */
  @Output() addCerrarSesion = new EventEmitter<void>();

  /**
   * Servicio para operaciones relacionadas con noticias.
   */
  private readonly noticiaService = inject(NoticiaService);

  /**
   * Servicio para operaciones relacionadas con horóscopos.
   */
  private readonly horoscopoService = inject(HoroscopoService);

  /**
   * Servicio encargado de autenticación y sesión.
   */
  private readonly authService = inject(AuthService);

  /**
   * Servicio utilizado para mostrar notificaciones emergentes.
   */
  private readonly toastr = inject(ToastrService);

  /**
   * Lista de noticias registradas.
   */
  noticias: Noticia[] = [];

  /**
   * Lista de horóscopos registrados.
   */
  horoscopos: Horoscopo[] = [];

  /**
   * Vista activa del panel.
   */
  vistaActual: 'NOTICIAS' | 'HOROSCOPOS' = 'NOTICIAS';

  /**
   * Nombre del usuario autenticado actualmente.
   */
  nombreSesion = this.authService.obtenerNombreActual() ?? '';

  /**
   * Formulario utilizado para crear o editar noticias.
   */
  formularioNoticia: Noticia = this.crearFormularioNoticia();

  /**
   * Formulario utilizado para crear o editar horóscopos.
   */
  formularioHoroscopo: Horoscopo = this.crearFormularioHoroscopo();

  /**
   * Lista de signos zodiacales disponibles.
   */
  protected readonly tiposHoroscopo = Object.values(TipoHoroscopo);

  /**
   * Método ejecutado al inicializar el componente.
   * Carga noticias y horóscopos disponibles.
   */
  ngOnInit(): void {
    this.cargarNoticias();
    this.cargarHoroscopos();
  }

  /**
   * Obtiene únicamente las noticias
   * creadas por el editor actual.
   */
  get noticiasPropias(): Noticia[] {

    return this.noticias.filter(
      (noticia) => noticia.usuarioEditor === this.nombreSesion,
    );
  }

  /**
   * Obtiene únicamente los horóscopos
   * creados por el editor actual.
   */
  get horoscoposPropios(): Horoscopo[] {

    return this.horoscopos.filter(
      (horoscopo) => horoscopo.usuarioEditor === this.nombreSesion,
    );
  }

  /**
   * Cambia la vista activa del panel.
   *
   * @param vista Vista seleccionada.
   */
  cambiarVista(vista: 'NOTICIAS' | 'HOROSCOPOS'): void {
    this.vistaActual = vista;
  }

  /**
   * Recarga noticias y horóscopos.
   */
  refrescarPanel(): void {
    this.cargarNoticias();
    this.cargarHoroscopos();
  }

  /**
   * Guarda o actualiza una noticia.
   */
  guardarNoticia(): void {

    /**
     * Validación de campos obligatorios.
     */
    if (
      !this.formularioNoticia.titulo.trim()
      || !this.formularioNoticia.contenido.trim()
    ) {

      this.toastr.warning(
        'Debes completar titulo y contenido.',
        'Campos requeridos',
      );

      return;
    }

    /**
     * Construcción del objeto final de la noticia.
     */
    const payload: Noticia = {
      ...this.formularioNoticia,
      titulo: this.formularioNoticia.titulo.trim(),
      contenido: this.formularioNoticia.contenido.trim(),
      tipoPublicacion: TipoPublicacion.NOTICIA,
      usuarioEditor: this.nombreSesion,
    };

    /**
     * Determina si se crea o actualiza la noticia.
     */
    const peticion = payload.id
      ? this.noticiaService.updateById(payload.id, payload)
      : this.noticiaService.create(payload);

    /**
     * Ejecución de la petición.
     */
    peticion.subscribe({

      next: (mensaje) => {

        this.toastr.success(mensaje, 'Noticia guardada');

        this.formularioNoticia = this.crearFormularioNoticia();

        this.cargarNoticias();
      },

      error: (error) => {

        this.toastr.error(
          this.obtenerMensajeError(error),
          'No fue posible guardar la noticia',
        );
      },
    });
  }

  /**
   * Guarda o actualiza un horóscopo.
   */
  guardarHoroscopo(): void {

    /**
     * Validación del contenido del horóscopo.
     */
    if (!this.formularioHoroscopo.contenido.trim()) {

      this.toastr.warning(
        'Debes escribir el contenido del horoscopo.',
        'Campos requeridos',
      );

      return;
    }

    /**
     * Construcción del objeto final del horóscopo.
     */
    const payload: Horoscopo = {
      ...this.formularioHoroscopo,
      contenido: this.formularioHoroscopo.contenido.trim(),
      tipoPublicacion: TipoPublicacion.HOROSCOPO,
      usuarioEditor: this.nombreSesion,
    };

    /**
     * Determina si se crea o actualiza el horóscopo.
     */
    const peticion = payload.id
      ? this.horoscopoService.updateById(payload.id, payload)
      : this.horoscopoService.create(payload);

    /**
     * Ejecución de la petición.
     */
    peticion.subscribe({

      next: (mensaje) => {

        this.toastr.success(mensaje, 'Horoscopo guardado');

        this.formularioHoroscopo = this.crearFormularioHoroscopo();

        this.cargarHoroscopos();
      },

      error: (error) => {

        this.toastr.error(
          this.obtenerMensajeError(error),
          'No fue posible guardar el horoscopo',
        );
      },
    });
  }

  /**
   * Carga una noticia en el formulario para edición.
   *
   * @param noticia Noticia seleccionada.
   */
  editarNoticia(noticia: Noticia): void {

    this.formularioNoticia = { ...noticia };

    this.vistaActual = 'NOTICIAS';
  }

  /**
   * Carga un horóscopo en el formulario para edición.
   *
   * @param horoscopo Horóscopo seleccionado.
   */
  editarHoroscopo(horoscopo: Horoscopo): void {

    this.formularioHoroscopo = { ...horoscopo };

    this.vistaActual = 'HOROSCOPOS';
  }

  /**
   * Elimina una noticia registrada.
   *
   * @param noticia Noticia seleccionada.
   */
  eliminarNoticia(noticia: Noticia): void {

    /**
     * Verifica que exista un identificador válido.
     */
    if (!noticia.id) {
      return;
    }

    this.noticiaService.deleteById(noticia.id).subscribe({

      next: (mensaje) => {

        this.toastr.success(mensaje, 'Noticia eliminada');

        this.cargarNoticias();
      },

      error: (error) => {

        this.toastr.error(
          this.obtenerMensajeError(error),
          'No fue posible eliminar la noticia',
        );
      },
    });
  }

  /**
   * Elimina un horóscopo registrado.
   *
   * @param horoscopo Horóscopo seleccionado.
   */
  eliminarHoroscopo(horoscopo: Horoscopo): void {

    /**
     * Verifica que exista un identificador válido.
     */
    if (!horoscopo.id) {
      return;
    }

    this.horoscopoService.deleteById(horoscopo.id).subscribe({

      next: (mensaje) => {

        this.toastr.success(mensaje, 'Horoscopo eliminado');

        this.cargarHoroscopos();
      },

      error: (error) => {

        this.toastr.error(
          this.obtenerMensajeError(error),
          'No fue posible eliminar el horoscopo',
        );
      },
    });
  }

  /**
   * Restablece el formulario de noticias.
   */
  cancelarEdicionNoticia(): void {
    this.formularioNoticia = this.crearFormularioNoticia();
  }

  /**
   * Restablece el formulario de horóscopos.
   */
  cancelarEdicionHoroscopo(): void {
    this.formularioHoroscopo = this.crearFormularioHoroscopo();
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
   * Obtiene todas las noticias desde el servicio.
   */
  private cargarNoticias(): void {

    this.noticiaService.getAll().subscribe({

      next: (noticias) => {
        this.noticias = noticias;
      },

      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  /**
   * Obtiene todos los horóscopos desde el servicio.
   */
  private cargarHoroscopos(): void {

    this.horoscopoService.getAll().subscribe({

      next: (horoscopos) => {
        this.horoscopos = horoscopos;
      },

      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  /**
   * Genera un formulario vacío para noticias.
   */
  private crearFormularioNoticia(): Noticia {

    return {
      titulo: '',
      contenido: '',
      tipoPublicacion: TipoPublicacion.NOTICIA,
      usuarioEditor: this.nombreSesion,
    };
  }

  /**
   * Genera un formulario vacío para horóscopos.
   */
  private crearFormularioHoroscopo(): Horoscopo {

    return {
      tipoHoroscopo: TipoHoroscopo.ARIES,
      contenido: '',
      tipoPublicacion: TipoPublicacion.HOROSCOPO,
      usuarioEditor: this.nombreSesion,
    };
  }

  /**
   * Obtiene un mensaje de error legible.
   *
   * @param error Objeto de error recibido.
   * @returns Mensaje de error procesado.
   */
  private obtenerMensajeError(error: { error?: unknown }): string {

    if (typeof error?.error === 'string') {
      return error.error;
    }

    return 'No fue posible completar la operacion';
  }
}
