// Importa las clases necesarias para realizar peticiones HTTP
import { HttpClient, HttpHeaders } from '@angular/common/http';

// Importa los decoradores y utilidades de Angular
import { inject, Injectable } from '@angular/core';

// Importa herramientas de RxJS para manejo de observables
import { Observable, of, tap } from 'rxjs';

// Importa el enum de tipos de usuario
import { TipoUsuario } from '../enums/tipo-usuario.enum';

// Importa el modelo de respuesta de autenticacion
import { AuthResponse } from '../models/auth-response.model';

// Decorador que define el servicio como inyectable globalmente
@Injectable({
  providedIn: 'root',
})

// Servicio encargado de la autenticacion y manejo de sesion
export class AuthService {

  // Cliente HTTP para realizar solicitudes al backend
  private readonly http = inject(HttpClient);

  // URL base del modulo de autenticacion
  private readonly apiUrl = 'http://localhost:8081/auth';

  // Clave utilizada para almacenar el token en localStorage
  private readonly tokenKey = 'pagina-noticias-token';

  // Clave utilizada para almacenar el rol del usuario
  private readonly roleKey = 'pagina-noticias-role';

  // Clave utilizada para almacenar el nombre del usuario
  private readonly userKey = 'pagina-noticias-user';

  // Metodo para iniciar sesion
  postLogin(nombre: string, contrasena: string): Observable<AuthResponse> {

    // Realiza la peticion POST al endpoint de login
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/login`, {

        // Nombre del usuario
        nombre,

        // Contrasena del usuario
        contrasena,
      })

      // Ejecuta acciones adicionales cuando la respuesta es exitosa
      .pipe(
        tap((respuesta) =>

          // Guarda la sesion en el almacenamiento local
          this.guardarSesion(respuesta.token, respuesta.role as TipoUsuario, nombre),
        ),
      );
  }

  // Metodo para registrar un nuevo usuario
  postRegister(nombre: string, contrasena: string): Observable<string> {

    // Realiza la peticion POST al endpoint de registro
    return this.http.post(
      `${this.apiUrl}/register`,
      {
        nombre,
        contrasena,
      },

      // Configura la respuesta como texto plano
      { responseType: 'text' },
    );
  }

  // Metodo para cerrar sesion
  postCerrarSesion(): Observable<boolean> {

    // Simula una respuesta exitosa y limpia la sesion local
    return of(true).pipe(tap(() => this.limpiarSesionLocal()));
  }

  // Verifica si existe una sesion activa
  haySesionActiva(): boolean {

    // Retorna true si existe token almacenado
    return !!localStorage.getItem(this.tokenKey);
  }

  // Obtiene el token almacenado en localStorage
  obtenerToken(): string | null {

    // Retorna el token actual
    return localStorage.getItem(this.tokenKey);
  }

  // Obtiene el rol actual del usuario autenticado
  obtenerRolActual(): TipoUsuario | null {

    // Recupera el rol desde localStorage
    const rol = localStorage.getItem(this.roleKey);

    // Convierte el valor al enum TipoUsuario
    return rol ? (rol as TipoUsuario) : null;
  }

  // Obtiene el nombre del usuario autenticado
  obtenerNombreActual(): string | null {

    // Retorna el nombre almacenado
    return localStorage.getItem(this.userKey);
  }

  // Actualiza el nombre del usuario almacenado localmente
  actualizarNombreActual(nombre: string): void {

    // Guarda el nuevo nombre en localStorage
    localStorage.setItem(this.userKey, nombre);
  }

  // Genera los encabezados HTTP con autenticacion JWT
  obtenerEncabezadosAutenticacion(): HttpHeaders {

    // Obtiene el token almacenado
    const token = this.obtenerToken();

    // Si no existe token retorna encabezados vacios
    if (!token) {
      return new HttpHeaders();
    }

    // Retorna encabezados con token Bearer
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  // Elimina toda la informacion de sesion almacenada localmente
  limpiarSesionLocal(): void {

    // Elimina el token
    localStorage.removeItem(this.tokenKey);

    // Elimina el rol
    localStorage.removeItem(this.roleKey);

    // Elimina el nombre del usuario
    localStorage.removeItem(this.userKey);
  }

  // Metodo privado para almacenar la informacion de la sesion
  private guardarSesion(token: string, rol: TipoUsuario, nombre: string): void {

    // Guarda el token de autenticacion
    localStorage.setItem(this.tokenKey, token);

    // Guarda el rol del usuario
    localStorage.setItem(this.roleKey, rol);

    // Guarda el nombre del usuario
    localStorage.setItem(this.userKey, nombre);
  }
}
