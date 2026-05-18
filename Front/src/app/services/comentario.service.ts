import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Comentario } from '../models/comentario.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class ComentarioService {
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);
  private readonly apiUrl = 'http://localhost:8081/comentario';

  getAll(): Observable<Comentario[]> {
    return this.http
      .get<Comentario[]>(`${this.apiUrl}/mostrartodo`, {
        headers: this.authService.obtenerEncabezadosAutenticacion(),
      })
      .pipe(map((comentarios) => comentarios ?? []));
  }

  create(comentario: Comentario): Observable<string> {
    return this.http.post(`${this.apiUrl}/create`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(comentario),
      responseType: 'text',
    });
  }

  updateById(id: number, comentario: Comentario): Observable<string> {
    return this.http.put(`${this.apiUrl}/actualizar`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(comentario, id),
      responseType: 'text',
    });
  }

  deleteById(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      responseType: 'text',
    });
  }

  private construirParametros(comentario: Comentario, id?: number): HttpParams {
    let params = new HttpParams()
      .set('contenido', comentario.contenido.trim())
      .set('fecha', comentario.fecha)
      .set('nombreComentarista', comentario.nombreComentarista.trim())
      .set('tituloNoticia', comentario.tituloNoticia)
      .set('signoHoroscopo', comentario.signoHoroscopo)
      .set('tipoPublicacion', comentario.tipoPublicacion);

    if (id !== undefined) {
      params = params.set('id', id.toString());
    }

    if (comentario.horoscopoId !== undefined) {
      params = params.set('horoscopoId', comentario.horoscopoId.toString());
    }

    return params;
  }
}
