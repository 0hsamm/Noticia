import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Usuario } from '../models/usuario.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class UsuarioAdministradorService {
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);
  private readonly apiUrl = 'http://localhost:8081/usuarioadministrador';

  getAll(): Observable<Usuario[]> {
    return this.http
      .get<Usuario[]>(`${this.apiUrl}/mostrartodo`, {
        headers: this.authService.obtenerEncabezadosAutenticacion(),
      })
      .pipe(map((usuarios) => usuarios ?? []));
  }

  create(usuario: Usuario): Observable<string> {
    return this.http.post(`${this.apiUrl}/crear`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(usuario),
      responseType: 'text',
    });
  }

  updateById(id: number, usuario: Usuario): Observable<string> {
    return this.http.put(`${this.apiUrl}/actualizar`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(usuario, id),
      responseType: 'text',
    });
  }

  deleteById(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/eliminar`, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: new HttpParams().set('id', id.toString()),
      responseType: 'text',
    });
  }

  private construirParametros(usuario: Usuario, id?: number): HttpParams {
    let params = new HttpParams()
      .set('nombre', usuario.nombre.trim())
      .set('contrasena', usuario.contrasena)
      .set('tipoUsuario', usuario.tipoUsuario);

    if (id !== undefined) {
      params = params.set('id', id.toString());
    }

    return params;
  }
}
