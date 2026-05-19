import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Noticia } from '../models/noticia.model';
import { AuthService } from './auth.service';

/**
 * Servicio encargado de gestionar las operaciones CRUD
 * relacionadas con las noticias consumiendo la API backend.
 *
 * Este servicio utiliza autenticación mediante encabezados
 * proporcionados por AuthService.
 */
@Injectable({
  providedIn: 'root',
})
export class NoticiaService {

  /**
   * Cliente HTTP utilizado para realizar solicitudes al backend.
   */
  private readonly http = inject(HttpClient);

  /**
   * Servicio encargado de generar los encabezados
   * de autenticación para las peticiones.
   */
  private readonly authService = inject(AuthService);

  /**
   * URL base del endpoint de noticias.
   */
  private readonly apiUrl = 'http://localhost:8081/noticia';

  /**
   * Obtiene todas las noticias registradas en el sistema.
   *
   * @returns Observable con un arreglo de noticias.
   */
  getAll(): Observable<Noticia[]> {
    return this.http
      .get<Noticia[]>(`${this.apiUrl}/mostrartodo`, {
        headers: this.authService.obtenerEncabezadosAutenticacion(),
      })
      .pipe(
        /**
         * Garantiza que siempre se retorne un arreglo,
         * evitando valores null o undefined.
         */
        map((noticias) => noticias ?? [])
      );
  }

  /**
   * Crea una nueva noticia en el sistema.
   *
   * @param noticia Datos de la noticia a registrar.
   * @returns Observable con el mensaje de respuesta del servidor.
   */
  create(noticia: Noticia): Observable<string> {
    return this.http.post(`${this.apiUrl}/crear`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(noticia),
      responseType: 'text',
    });
  }

  /**
   * Actualiza una noticia existente mediante su identificador.
   *
   * @param id Identificador de la noticia.
   * @param noticia Nuevos datos de la noticia.
   * @returns Observable con el mensaje de respuesta del servidor.
   */
  updateById(id: number, noticia: Noticia): Observable<string> {
    return this.http.put(`${this.apiUrl}/actualizar`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(noticia, id),
      responseType: 'text',
    });
  }

  /**
   * Elimina una noticia según su identificador.
   *
   * @param id Identificador de la noticia a eliminar.
   * @returns Observable con el mensaje de respuesta del servidor.
   */
  deleteById(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/eliminar`, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: new HttpParams().set('id', id.toString()),
      responseType: 'text',
    });
  }

  /**
   * Construye los parámetros HTTP necesarios para
   * las operaciones de creación y actualización.
   *
   * @param noticia Objeto con la información de la noticia.
   * @param id Identificador opcional utilizado en actualizaciones.
   * @returns Objeto HttpParams con los parámetros configurados.
   */
  private construirParametros(noticia: Noticia, id?: number): HttpParams {

    /**
     * Creación de parámetros base enviados al backend.
     */
    let params = new HttpParams()
      .set('titulo', noticia.titulo.trim())
      .set('contenido', noticia.contenido.trim())
      .set('tipoPublicacion', noticia.tipoPublicacion)
      .set('usuarioComentarista', noticia.usuarioEditor);

    /**
     * Agrega el identificador cuando se trata
     * de una operación de actualización.
     */
    if (id !== undefined) {
      params = params.set('id', id.toString());
    }

    return params;
  }
}
