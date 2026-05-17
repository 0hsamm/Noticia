import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Horoscopo } from '../models/horoscopo.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class HoroscopoService {
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);
  private readonly apiUrl = 'http://localhost:8081/horoscopo';

  getAll(): Observable<Horoscopo[]> {
    return this.http
      .get<Horoscopo[]>(`${this.apiUrl}/mostrartodo`, {
        headers: this.authService.obtenerEncabezadosAutenticacion(),
      })
      .pipe(map((horoscopos) => horoscopos ?? []));
  }

  create(horoscopo: Horoscopo): Observable<string> {
    return this.http.post(`${this.apiUrl}/crear`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(horoscopo),
      responseType: 'text',
    });
  }

  updateById(id: number, horoscopo: Horoscopo): Observable<string> {
    return this.http.put(`${this.apiUrl}/actualizar`, null, {
      headers: this.authService.obtenerEncabezadosAutenticacion(),
      params: this.construirParametros(horoscopo, id),
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

  private construirParametros(horoscopo: Horoscopo, id?: number): HttpParams {
    let params = new HttpParams()
      .set('tipoHoroscopo', horoscopo.tipoHoroscopo)
      .set('contenido', horoscopo.contenido.trim())
      .set('tipoPublicacion', horoscopo.tipoPublicacion)
      .set('usuarioEditor', horoscopo.usuarioEditor);

    if (id !== undefined) {
      params = params.set('id', id.toString());
    }

    return params;
  }
}
