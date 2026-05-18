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

@Component({
  selector: 'app-panel-comentarista',
  standalone: false,
  templateUrl: './panel-comentarista.html',
  styleUrl: './panel-comentarista.css',
})
export class PanelComentarista implements OnInit {
  @Output() addCerrarSesion = new EventEmitter<void>();

  private readonly noticiaService = inject(NoticiaService);
  private readonly horoscopoService = inject(HoroscopoService);
  private readonly comentarioService = inject(ComentarioService);
  private readonly authService = inject(AuthService);
  private readonly toastr = inject(ToastrService);

  noticias: Noticia[] = [];
  horoscopos: Horoscopo[] = [];
  comentarios: Comentario[] = [];
  vistaActual: 'NOTICIAS' | 'HOROSCOPOS' | 'MIS_COMENTARIOS' = 'NOTICIAS';
  nombreSesion = this.authService.obtenerNombreActual() ?? '';
  formulario: Comentario = this.crearFormulario();

  protected readonly tiposPublicacion = TipoPublicacion;

  ngOnInit(): void {
    this.cargarNoticias();
    this.cargarHoroscopos();
    this.cargarComentarios();
  }

  get comentariosPropios(): Comentario[] {
    return this.comentarios.filter((comentario) => comentario.nombreComentarista === this.nombreSesion);
  }

  cambiarVista(vista: 'NOTICIAS' | 'HOROSCOPOS' | 'MIS_COMENTARIOS'): void {
    this.vistaActual = vista;

    if (vista === 'NOTICIAS') {
      this.formulario.tipoPublicacion = TipoPublicacion.NOTICIA;
    }

    if (vista === 'HOROSCOPOS') {
      this.formulario.tipoPublicacion = TipoPublicacion.HOROSCOPO;
    }

    this.asignarDestinoPorDefecto();
  }

  refrescarPanel(): void {
    this.cargarNoticias();
    this.cargarHoroscopos();
    this.cargarComentarios();
  }

  guardarComentario(): void {
    if (!this.formulario.contenido.trim()) {
      this.toastr.warning('Debes escribir el comentario.', 'Campos requeridos');
      return;
    }

    if (this.formulario.tipoPublicacion === TipoPublicacion.NOTICIA && !this.formulario.tituloNoticia) {
      this.toastr.warning('Selecciona una noticia para comentar.', 'Destino requerido');
      return;
    }

    if (this.formulario.tipoPublicacion === TipoPublicacion.HOROSCOPO && !this.formulario.horoscopoId) {
      this.toastr.warning('Selecciona un horoscopo para comentar.', 'Destino requerido');
      return;
    }

    const horoscopoSeleccionado = this.obtenerHoroscopoSeleccionado();
    const payload: Comentario = {
      ...this.formulario,
      contenido: this.formulario.contenido.trim(),
      fecha: this.formulario.fecha || this.generarFechaActual(),
      nombreComentarista: this.nombreSesion,
      tituloNoticia:
        this.formulario.tipoPublicacion === TipoPublicacion.NOTICIA ? this.formulario.tituloNoticia : '',
      signoHoroscopo:
        this.formulario.tipoPublicacion === TipoPublicacion.HOROSCOPO
          ? horoscopoSeleccionado?.tipoHoroscopo ?? this.formulario.signoHoroscopo
          : this.formulario.signoHoroscopo || TipoHoroscopo.ARIES,
      horoscopoId:
        this.formulario.tipoPublicacion === TipoPublicacion.HOROSCOPO
          ? this.formulario.horoscopoId
          : undefined,
    };

    const peticion = payload.id
      ? this.comentarioService.updateById(payload.id, payload)
      : this.comentarioService.create(payload);

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

  editarComentario(comentario: Comentario): void {
    this.formulario = { ...comentario };
    this.vistaActual = 'MIS_COMENTARIOS';
  }

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

  cancelarEdicion(): void {
    this.formulario = this.crearFormulario();
    this.asignarDestinoPorDefecto();
  }

  cerrarSesion(): void {
    this.authService.postCerrarSesion().subscribe(() => {
      this.toastr.info('Sesion cerrada', 'Informacion');
      this.addCerrarSesion.emit();
    });
  }

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

  private asignarDestinoPorDefecto(): void {
    if (
      this.formulario.tipoPublicacion === TipoPublicacion.NOTICIA &&
      !this.formulario.tituloNoticia &&
      this.noticias.length > 0
    ) {
      this.formulario.tituloNoticia = this.noticias[0].titulo;
    }

    if (
      this.formulario.tipoPublicacion === TipoPublicacion.HOROSCOPO &&
      !this.formulario.horoscopoId &&
      this.horoscopos.length > 0
    ) {
      this.formulario.horoscopoId = this.horoscopos[0].id;
      this.formulario.signoHoroscopo = this.horoscopos[0].tipoHoroscopo;
    }
  }

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

  private obtenerMensajeError(error: { error?: unknown }): string {
    if (typeof error?.error === 'string') {
      return error.error;
    }

    return 'No fue posible completar la operacion';
  }

  private obtenerHoroscopoSeleccionado(): Horoscopo | undefined {
    return this.horoscopos.find((horoscopo) => horoscopo.id === this.formulario.horoscopoId);
  }
}
