import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Horoscopo } from '../models/horoscopo.model';
import { AuthService } from './auth.service';

/**
 * Servicio encargado de gestionar las operaciones CRUD
 * relacionadas con los horóscopos consumiendo la API backend.
 *
 * Este servicio utiliza autenticación mediante encabezados
 * proporcionados por AuthService.
 */
@Injectable({
  providedIn: 'root',
})
export class HoroscopoService {

  /**
   * Cliente HTTP utilizado para realizar las peticiones al backend.
   */
  private readonly http = inject(HttpClient);

  /**
   * Servicio encargado de proporcionar los encabezados
   * de autenticación para cada solicitud.
   */
  private readonly authService = inject(AuthService);

  /**
   * URL base del endpoint de horóscopos.
   */
  private readonly apiUrl = 'http://localhost:8081/horoscopo';

  /**
   * Obtiene la lista completa de horóscopos registrados.
   *
   * @returns Observable con un arreglo de horóscopos.
   */
  getAll(): Observable<Horoscopo[]> {
    return this.http
      .get<Horoscopo[]>(`${this.apiUrl}/mostrartodo`, {
        headers: this.authService.obtenerEncabezadosAutenticacion(),
      })
      .pipe(
        /**
         * Garantiza que siempre se retorne un arreglo,
         * evitando valores null o undefined.
         */
        map((horoscopos) => horoscopos ?? [])
      );
  }

  /**
   * Crea un nuevo horóscopo en el sistema.
   *
   * @param horoscopo Datos del horóscopo a registrar.
   * @returns Observable con el mensaje de respuesta del servidor.
   */
  create(horoscopo: Horoscopo): Observable<string> {
    return this.http.post(`${this.apiUrl}/crear`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(horoscopo),
      responseType: 'text',
    });
  }

  /**
   * Actualiza un horóscopo existente mediante su identificador.
   *
   * @param id Identificador del horóscopo.
   * @param horoscopo Nuevos datos del horóscopo.
   * @returns Observable con el mensaje de respuesta del servidor.
   */
  updateById(id: number, horoscopo: Horoscopo): Observable<string> {
    return this.http.put(`${this.apiUrl}/actualizar`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(horoscopo, id),
      responseType: 'text',
    });
  }

  /**
   * Elimina un horóscopo según su identificador.
   *
   * @param id Identificador del horóscopo a eliminar.
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
   * @param horoscopo Objeto con la información del horóscopo.
   * @param id Identificador opcional utilizado en actualizaciones.
   * @returns Objeto HttpParams con los parámetros configurados.
   */
  private construirParametros(horoscopo: Horoscopo, id?: number): HttpParams {

    /**
     * Creación de parámetros base enviados al backend.
     */
    let params = new HttpParams()
      .set('tipoHoroscopo', horoscopo.tipoHoroscopo)
      .set('contenido', horoscopo.contenido.trim())
      .set('tipoPublicacion', horoscopo.tipoPublicacion)
      .set('usuarioEditor', horoscopo.usuarioEditor);

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
