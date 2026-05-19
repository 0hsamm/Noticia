/**
 * Enum que representa los tipos de usuario del sistema.
 *
 * Define los roles disponibles y controla el nivel de acceso
 * y permisos dentro de la aplicación.
 *
 * Cada rol tiene diferentes capacidades:
 * - ADMIN: acceso total al sistema
 * - EDITOR: puede crear y modificar contenido
 * - COMENTARISTA: puede comentar contenido
 * - USUARIO: usuario estándar con permisos básicos
 */
export enum TipoUsuario {
  ADMIN = 'ADMIN',
  EDITOR = 'EDITOR',
  COMENTARISTA = 'COMENTARISTA',
  USUARIO = 'USUARIO',
}
