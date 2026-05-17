import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, of, tap } from 'rxjs';
import { TipoUsuario } from '../enums/tipo-usuario.enum';
import { AuthResponse } from '../models/auth-response.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8081/auth';
  private readonly tokenKey = 'pagina-noticias-token';
  private readonly roleKey = 'pagina-noticias-role';
  private readonly userKey = 'pagina-noticias-user';

  postLogin(nombre: string, contrasena: string): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/login`, {
        nombre,
        contrasena,
      })
      .pipe(
        tap((respuesta) =>
          this.guardarSesion(respuesta.token, respuesta.role as TipoUsuario, nombre),
        ),
      );
  }

  postRegister(nombre: string, contrasena: string): Observable<string> {
    return this.http.post(`${this.apiUrl}/register`, { nombre, contrasena }, { responseType: 'text' });
  }

  postCerrarSesion(): Observable<boolean> {
    return of(true).pipe(tap(() => this.limpiarSesionLocal()));
  }

  haySesionActiva(): boolean {
    return !!localStorage.getItem(this.tokenKey);
  }

  obtenerToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  obtenerRolActual(): TipoUsuario | null {
    const rol = localStorage.getItem(this.roleKey);
    return rol ? (rol as TipoUsuario) : null;
  }

  obtenerNombreActual(): string | null {
    return localStorage.getItem(this.userKey);
  }

  actualizarNombreActual(nombre: string): void {
    localStorage.setItem(this.userKey, nombre);
  }

  obtenerEncabezadosAutenticacion(): HttpHeaders {
    const token = this.obtenerToken();
    if (!token) {
      return new HttpHeaders();
    }

    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  limpiarSesionLocal(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.roleKey);
    localStorage.removeItem(this.userKey);
  }

  private guardarSesion(token: string, rol: TipoUsuario, nombre: string): void {
    localStorage.setItem(this.tokenKey, token);
    localStorage.setItem(this.roleKey, rol);
    localStorage.setItem(this.userKey, nombre);
  }
}
