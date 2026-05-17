import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Noticia } from '../models/noticia.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class NoticiaService {
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);
  private readonly apiUrl = 'http://localhost:8081/noticia';

  getAll(): Observable<Noticia[]> {
    return this.http
      .get<Noticia[]>(`${this.apiUrl}/mostrartodo`, {
        headers: this.authService.obtenerEncabezadosAutenticacion(),
      })
      .pipe(map((noticias) => noticias ?? []));
  }

  create(noticia: Noticia): Observable<string> {
    return this.http.post(`${this.apiUrl}/crear`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(noticia),
      responseType: 'text',
    });
  }

  updateById(id: number, noticia: Noticia): Observable<string> {
    return this.http.put(`${this.apiUrl}/actualizar`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(noticia, id),
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

  private construirParametros(noticia: Noticia, id?: number): HttpParams {
    let params = new HttpParams()
      .set('titulo', noticia.titulo.trim())
      .set('contenido', noticia.contenido.trim())
      .set('tipoPublicacion', noticia.tipoPublicacion)
      .set('usuarioComentarista', noticia.usuarioEditor);

    if (id !== undefined) {
      params = params.set('id', id.toString());
    }

    return params;
  }
}
