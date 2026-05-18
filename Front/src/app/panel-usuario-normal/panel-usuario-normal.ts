import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Comentario } from '../models/comentario.model';
import { Horoscopo } from '../models/horoscopo.model';
import { Noticia } from '../models/noticia.model';
import { AuthService } from '../services/auth.service';
import { ComentarioService } from '../services/comentario.service';
import { HoroscopoService } from '../services/horoscopo.service';
import { NoticiaService } from '../services/noticia.service';

@Component({
  selector: 'app-panel-usuario-normal',
  standalone: false,
  templateUrl: './panel-usuario-normal.html',
  styleUrl: './panel-usuario-normal.css',
})
export class PanelUsuarioNormal implements OnInit {
  @Output() addCerrarSesion = new EventEmitter<void>();

  private readonly noticiaService = inject(NoticiaService);
  private readonly horoscopoService = inject(HoroscopoService);
  private readonly comentarioService = inject(ComentarioService);
  private readonly authService = inject(AuthService);
  private readonly toastr = inject(ToastrService);

  noticias: Noticia[] = [];
  horoscopos: Horoscopo[] = [];
  comentarios: Comentario[] = [];
  nombreSesion = this.authService.obtenerNombreActual() ?? '';

  ngOnInit(): void {
    this.cargarNoticias();
    this.cargarHoroscopos();
    this.cargarComentarios();
  }

  refrescarPanel(): void {
    this.cargarNoticias();
    this.cargarHoroscopos();
    this.cargarComentarios();
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

  private obtenerMensajeError(error: { error?: unknown }): string {
    if (typeof error?.error === 'string') {
      return error.error;
    }

    return 'No fue posible cargar la informacion';
  }
}
