import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { TipoHoroscopo } from '../enums/tipo-horoscopo.enum';
import { TipoPublicacion } from '../enums/tipo-publicacion.enum';
import { Horoscopo } from '../models/horoscopo.model';
import { Noticia } from '../models/noticia.model';
import { AuthService } from '../services/auth.service';
import { HoroscopoService } from '../services/horoscopo.service';
import { NoticiaService } from '../services/noticia.service';

@Component({
  selector: 'app-panel-editor',
  standalone: false,
  templateUrl: './panel-editor.html',
  styleUrl: './panel-editor.css',
})
export class PanelEditor implements OnInit {
  @Output() addCerrarSesion = new EventEmitter<void>();

  private readonly noticiaService = inject(NoticiaService);
  private readonly horoscopoService = inject(HoroscopoService);
  private readonly authService = inject(AuthService);
  private readonly toastr = inject(ToastrService);

  noticias: Noticia[] = [];
  horoscopos: Horoscopo[] = [];
  vistaActual: 'NOTICIAS' | 'HOROSCOPOS' = 'NOTICIAS';
  nombreSesion = this.authService.obtenerNombreActual() ?? '';
  formularioNoticia: Noticia = this.crearFormularioNoticia();
  formularioHoroscopo: Horoscopo = this.crearFormularioHoroscopo();

  protected readonly tiposHoroscopo = Object.values(TipoHoroscopo);

  ngOnInit(): void {
    this.cargarNoticias();
    this.cargarHoroscopos();
  }

  get noticiasPropias(): Noticia[] {
    return this.noticias.filter((noticia) => noticia.usuarioEditor === this.nombreSesion);
  }

  get horoscoposPropios(): Horoscopo[] {
    return this.horoscopos.filter((horoscopo) => horoscopo.usuarioEditor === this.nombreSesion);
  }

  cambiarVista(vista: 'NOTICIAS' | 'HOROSCOPOS'): void {
    this.vistaActual = vista;
  }

  refrescarPanel(): void {
    this.cargarNoticias();
    this.cargarHoroscopos();
  }

  guardarNoticia(): void {
    if (!this.formularioNoticia.titulo.trim() || !this.formularioNoticia.contenido.trim()) {
      this.toastr.warning('Debes completar titulo y contenido.', 'Campos requeridos');
      return;
    }

    const payload: Noticia = {
      ...this.formularioNoticia,
      titulo: this.formularioNoticia.titulo.trim(),
      contenido: this.formularioNoticia.contenido.trim(),
      tipoPublicacion: TipoPublicacion.NOTICIA,
      usuarioEditor: this.nombreSesion,
    };

    const peticion = payload.id
      ? this.noticiaService.updateById(payload.id, payload)
      : this.noticiaService.create(payload);

    peticion.subscribe({
      next: (mensaje) => {
        this.toastr.success(mensaje, 'Noticia guardada');
        this.formularioNoticia = this.crearFormularioNoticia();
        this.cargarNoticias();
      },
      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'No fue posible guardar la noticia');
      },
    });
  }

  guardarHoroscopo(): void {
    if (!this.formularioHoroscopo.contenido.trim()) {
      this.toastr.warning('Debes escribir el contenido del horoscopo.', 'Campos requeridos');
      return;
    }

    const payload: Horoscopo = {
      ...this.formularioHoroscopo,
      contenido: this.formularioHoroscopo.contenido.trim(),
      tipoPublicacion: TipoPublicacion.HOROSCOPO,
      usuarioEditor: this.nombreSesion,
    };

    const peticion = payload.id
      ? this.horoscopoService.updateById(payload.id, payload)
      : this.horoscopoService.create(payload);

    peticion.subscribe({
      next: (mensaje) => {
        this.toastr.success(mensaje, 'Horoscopo guardado');
        this.formularioHoroscopo = this.crearFormularioHoroscopo();
        this.cargarHoroscopos();
      },
      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'No fue posible guardar el horoscopo');
      },
    });
  }

  editarNoticia(noticia: Noticia): void {
    this.formularioNoticia = { ...noticia };
    this.vistaActual = 'NOTICIAS';
  }

  editarHoroscopo(horoscopo: Horoscopo): void {
    this.formularioHoroscopo = { ...horoscopo };
    this.vistaActual = 'HOROSCOPOS';
  }

  eliminarNoticia(noticia: Noticia): void {
    if (!noticia.id) {
      return;
    }

    this.noticiaService.deleteById(noticia.id).subscribe({
      next: (mensaje) => {
        this.toastr.success(mensaje, 'Noticia eliminada');
        this.cargarNoticias();
      },
      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'No fue posible eliminar la noticia');
      },
    });
  }

  eliminarHoroscopo(horoscopo: Horoscopo): void {
    if (!horoscopo.id) {
      return;
    }

    this.horoscopoService.deleteById(horoscopo.id).subscribe({
      next: (mensaje) => {
        this.toastr.success(mensaje, 'Horoscopo eliminado');
        this.cargarHoroscopos();
      },
      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'No fue posible eliminar el horoscopo');
      },
    });
  }

  cancelarEdicionNoticia(): void {
    this.formularioNoticia = this.crearFormularioNoticia();
  }

  cancelarEdicionHoroscopo(): void {
    this.formularioHoroscopo = this.crearFormularioHoroscopo();
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
      },
      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  private crearFormularioNoticia(): Noticia {
    return {
      titulo: '',
      contenido: '',
      tipoPublicacion: TipoPublicacion.NOTICIA,
      usuarioEditor: this.nombreSesion,
    };
  }

  private crearFormularioHoroscopo(): Horoscopo {
    return {
      tipoHoroscopo: TipoHoroscopo.ARIES,
      contenido: '',
      tipoPublicacion: TipoPublicacion.HOROSCOPO,
      usuarioEditor: this.nombreSesion,
    };
  }

  private obtenerMensajeError(error: { error?: unknown }): string {
    if (typeof error?.error === 'string') {
      return error.error;
    }

    return 'No fue posible completar la operacion';
  }
}
