// Importa los decoradores y utilidades principales de Angular
import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';

// Importa el servicio de notificaciones emergentes
import { ToastrService } from 'ngx-toastr';

// Importa los modelos utilizados en el componente
import { Comentario } from '../models/comentario.model';
import { Horoscopo } from '../models/horoscopo.model';
import { Noticia } from '../models/noticia.model';

// Importa los servicios necesarios para autenticacion y consumo de datos
import { AuthService } from '../services/auth.service';
import { ComentarioService } from '../services/comentario.service';
import { HoroscopoService } from '../services/horoscopo.service';
import { NoticiaService } from '../services/noticia.service';

// Decorador que define la configuracion del componente
@Component({

  // Selector utilizado en las vistas HTML
  selector: 'app-panel-usuario-normal',

  // Indica que el componente no es standalone
  standalone: false,

  // Archivo HTML asociado al componente
  templateUrl: './panel-usuario-normal.html',

  // Archivo CSS asociado al componente
  styleUrl: './panel-usuario-normal.css',
})

// Clase principal del componente PanelUsuarioNormal
export class PanelUsuarioNormal implements OnInit {

  // Evento emitido cuando el usuario cierra sesion
  @Output() addCerrarSesion = new EventEmitter<void>();

  // Servicio para gestionar noticias
  private readonly noticiaService = inject(NoticiaService);

  // Servicio para gestionar horoscopos
  private readonly horoscopoService = inject(HoroscopoService);

  // Servicio para gestionar comentarios
  private readonly comentarioService = inject(ComentarioService);

  // Servicio de autenticacion
  private readonly authService = inject(AuthService);

  // Servicio de notificaciones toastr
  private readonly toastr = inject(ToastrService);

  // Lista de noticias disponibles
  noticias: Noticia[] = [];

  // Lista de horoscopos disponibles
  horoscopos: Horoscopo[] = [];

  // Lista de comentarios disponibles
  comentarios: Comentario[] = [];

  // Nombre del usuario con sesion activa
  nombreSesion = this.authService.obtenerNombreActual() ?? '';

  // Metodo del ciclo de vida que se ejecuta al inicializar el componente
  ngOnInit(): void {

    // Carga las noticias disponibles
    this.cargarNoticias();

    // Carga los horoscopos disponibles
    this.cargarHoroscopos();

    // Carga los comentarios disponibles
    this.cargarComentarios();
  }

  // Metodo que recarga toda la informacion del panel
  refrescarPanel(): void {

    // Recarga noticias
    this.cargarNoticias();

    // Recarga horoscopos
    this.cargarHoroscopos();

    // Recarga comentarios
    this.cargarComentarios();
  }

  // Metodo encargado de cerrar la sesion del usuario
  cerrarSesion(): void {

    // Realiza la peticion de cierre de sesion
    this.authService.postCerrarSesion().subscribe(() => {

      // Muestra mensaje informativo
      this.toastr.info('Sesion cerrada', 'Informacion');

      // Emite el evento de cierre de sesion
      this.addCerrarSesion.emit();
    });
  }

  // Metodo privado para obtener las noticias desde el servicio
  private cargarNoticias(): void {

    // Solicita todas las noticias
    this.noticiaService.getAll().subscribe({

      // Respuesta exitosa
      next: (noticias) => {

        // Guarda las noticias obtenidas
        this.noticias = noticias;
      },

      // Manejo de errores
      error: (error) => {

        // Muestra mensaje de error
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  // Metodo privado para obtener los horoscopos desde el servicio
  private cargarHoroscopos(): void {

    // Solicita todos los horoscopos
    this.horoscopoService.getAll().subscribe({

      // Respuesta exitosa
      next: (horoscopos) => {

        // Guarda los horoscopos obtenidos
        this.horoscopos = horoscopos;
      },

      // Manejo de errores
      error: (error) => {

        // Muestra mensaje de error
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  // Metodo privado para obtener los comentarios desde el servicio
  private cargarComentarios(): void {

    // Solicita todos los comentarios
    this.comentarioService.getAll().subscribe({

      // Respuesta exitosa
      next: (comentarios) => {

        // Guarda los comentarios obtenidos
        this.comentarios = comentarios;
      },

      // Manejo de errores
      error: (error) => {

        // Muestra mensaje de error
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  // Metodo privado para obtener mensajes de error personalizados
  private obtenerMensajeError(error: { error?: unknown }): string {

    // Verifica si el error recibido es una cadena de texto
    if (typeof error?.error === 'string') {

      // Retorna el mensaje recibido desde el backend
      return error.error;
    }

    // Mensaje generico en caso de no existir informacion detallada
    return 'No fue posible cargar la informacion';
  }
}
