/**
 * Respuesta del backend al realizar autenticación de usuario.
 *
 * Contiene el token JWT generado para la sesión y el rol
 * asignado al usuario autenticado.
 */
export interface AuthResponse {
  token: string;
  role: string;
}
