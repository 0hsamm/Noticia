// Importa las clases necesarias para realizar solicitudes HTTP
import { HttpClient, HttpParams } from '@angular/common/http';

// Importa los decoradores y utilidades de Angular
import { inject, Injectable } from '@angular/core';

// Importa operadores y herramientas de RxJS
import { map, Observable } from 'rxjs';

// Importa el modelo Comentario
import { Comentario } from '../models/comentario.model';

// Importa el servicio de autenticacion
import { AuthService } from './auth.service';

// Decorador que define el servicio como disponible globalmente
@Injectable({
  providedIn: 'root',
})

// Servicio encargado de gestionar las operaciones CRUD de comentarios
export class ComentarioService {

  // Cliente HTTP para realizar solicitudes al backend
  private readonly http = inject(HttpClient);

  // Servicio de autenticacion para agregar encabezados JWT
  private readonly authService = inject(AuthService);

  // URL base de la API de comentarios
  private readonly apiUrl = 'http://localhost:8081/comentario';

  // Metodo para obtener todos los comentarios registrados
  getAll(): Observable<Comentario[]> {

    // Realiza una solicitud GET al backend
    return this.http
      .get<Comentario[]>(`${this.apiUrl}/mostrartodo`, {

        // Agrega encabezados de autenticacion
        headers: this.authService.obtenerEncabezadosAutenticacion(),
      })

      // Garantiza que siempre se retorne un arreglo
      .pipe(map((comentarios) => comentarios ?? []));
  }

  // Metodo para crear un nuevo comentario
  create(comentario: Comentario): Observable<string> {

    // Realiza una solicitud POST al backend
    return this.http.post(`${this.apiUrl}/create`, null, {

      // Agrega encabezados de autenticacion
      headers: this.authService.obtenerEncabezadosAutenticacion(),

      // Agrega parametros del comentario
      params: this.construirParametros(comentario),

      // Define la respuesta como texto
      responseType: 'text',
    });
  }

  // Metodo para actualizar un comentario existente
  updateById(id: number, comentario: Comentario): Observable<string> {

    // Realiza una solicitud PUT al backend
    return this.http.put(`${this.apiUrl}/actualizar`, null, {

      // Agrega encabezados de autenticacion
      headers: this.authService.obtenerEncabezadosAutenticacion(),

      // Agrega parametros del comentario incluyendo el ID
      params: this.construirParametros(comentario, id),

      // Define la respuesta como texto
      responseType: 'text',
    });
  }

  // Metodo para eliminar un comentario por ID
  deleteById(id: number): Observable<string> {

    // Realiza una solicitud DELETE al backend
    return this.http.delete(`${this.apiUrl}/delete/${id}`, {

      // Agrega encabezados de autenticacion
      headers: this.authService.obtenerEncabezadosAutenticacion(),

      // Define la respuesta como texto
      responseType: 'text',
    });
  }

  // Metodo privado para construir los parametros HTTP
  private construirParametros(comentario: Comentario, id?: number): HttpParams {

    // Inicializa los parametros obligatorios
    let params = new HttpParams()

      // Contenido del comentario
      .set('contenido', comentario.contenido.trim())

      // Fecha del comentario
      .set('fecha', comentario.fecha)

      // Nombre del comentarista
      .set('nombreComentarista', comentario.nombreComentarista.trim())

      // Titulo de la noticia relacionada
      .set('tituloNoticia', comentario.tituloNoticia)

      // Signo zodiacal relacionado
      .set('signoHoroscopo', comentario.signoHoroscopo)

      // Tipo de publicacion asociada
      .set('tipoPublicacion', comentario.tipoPublicacion);

    // Verifica si se recibio un ID
    if (id !== undefined) {

      // Agrega el ID a los parametros
      params = params.set('id', id.toString());
    }

    // Verifica si existe un ID de horoscopo
    if (comentario.horoscopoId !== undefined) {

      // Agrega el ID del horoscopo a los parametros
      params = params.set('horoscopoId', comentario.horoscopoId.toString());
    }

    // Retorna los parametros construidos
    return params;
  }
}
