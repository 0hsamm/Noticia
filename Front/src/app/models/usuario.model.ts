import { TipoUsuario } from '../enums/tipo-usuario.enum';

/**
 * Interface que representa la estructura de un usuario
 * dentro de la aplicación.
 */
export interface Usuario {

  /**
   * Identificador único del usuario.
   * Este campo es opcional porque puede ser generado automáticamente.
   */
  id?: number;

  /**
   * Nombre del usuario registrado en el sistema.
   */
  nombre: string;

  /**
   * Contraseña asociada a la cuenta del usuario.
   */
  contrasena: string;

  /**
   * Tipo de usuario dentro de la aplicación.
   * Define los permisos o roles del usuario.
   */
  tipoUsuario: TipoUsuario;
}
