import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Usuario } from '../models/usuario.model';
import { AuthService } from './auth.service';

/**
 * Servicio encargado de gestionar las operaciones CRUD
 * relacionadas con los usuarios administradores
 * consumiendo la API backend.
 *
 * Este servicio utiliza autenticación mediante encabezados
 * proporcionados por AuthService.
 */
@Injectable({
  providedIn: 'root',
})
export class UsuarioAdministradorService {

  /**
   * Cliente HTTP utilizado para realizar las solicitudes al backend.
   */
  private readonly http = inject(HttpClient);

  /**
   * Servicio encargado de proporcionar los encabezados
   * de autenticación para las peticiones HTTP.
   */
  private readonly authService = inject(AuthService);

  /**
   * URL base del endpoint de usuarios administradores.
   */
  private readonly apiUrl = 'http://localhost:8081/usuarioadministrador';

  /**
   * Obtiene la lista completa de usuarios administradores.
   *
   * @returns Observable con un arreglo de usuarios.
   */
  getAll(): Observable<Usuario[]> {
    return this.http
      .get<Usuario[]>(`${this.apiUrl}/mostrartodo`, {
        headers: this.authService.obtenerEncabezadosAutenticacion(),
      })
      .pipe(
        /**
         * Garantiza que siempre se retorne un arreglo,
         * evitando valores null o undefined.
         */
        map((usuarios) => usuarios ?? [])
      );
  }

  /**
   * Crea un nuevo usuario administrador en el sistema.
   *
   * @param usuario Datos del usuario a registrar.
   * @returns Observable con el mensaje de respuesta del servidor.
   */
  create(usuario: Usuario): Observable<string> {
    return this.http.post(`${this.apiUrl}/crear`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(usuario),
      responseType: 'text',
    });
  }

  /**
   * Actualiza un usuario administrador existente mediante su identificador.
   *
   * @param id Identificador del usuario.
   * @param usuario Nuevos datos del usuario.
   * @returns Observable con el mensaje de respuesta del servidor.
   */
  updateById(id: number, usuario: Usuario): Observable<string> {
    return this.http.put(`${this.apiUrl}/actualizar`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(usuario, id),
      responseType: 'text',
    });
  }

  /**
   * Elimina un usuario administrador según su identificador.
   *
   * @param id Identificador del usuario a eliminar.
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
   * @param usuario Objeto con la información del usuario.
   * @param id Identificador opcional utilizado en actualizaciones.
   * @returns Objeto HttpParams con los parámetros configurados.
   */
  private construirParametros(usuario: Usuario, id?: number): HttpParams {

    /**
     * Creación de parámetros base enviados al backend.
     */
    let params = new HttpParams()
      .set('nombre', usuario.nombre.trim())
      .set('contrasena', usuario.contrasena)
      .set('tipoUsuario', usuario.tipoUsuario);

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
