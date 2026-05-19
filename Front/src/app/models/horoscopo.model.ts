import { TipoHoroscopo } from '../enums/tipo-horoscopo.enum';
import { TipoPublicacion } from '../enums/tipo-publicacion.enum';

/**
 * Interface que representa la estructura de un horóscopo
 * dentro de la aplicación.
 */
export interface Horoscopo {

  /**
   * Identificador único del horóscopo.
   * Este campo es opcional porque puede ser generado automáticamente.
   */
  id?: number;

  /**
   * Tipo de signo zodiacal al que pertenece el horóscopo.
   */
  tipoHoroscopo: TipoHoroscopo;

  /**
   * Contenido o descripción del horóscopo.
   */
  contenido: string;

  /**
   * Tipo de publicación asociada al horóscopo.
   */
  tipoPublicacion: TipoPublicacion;

  /**
   * Nombre del usuario encargado de editar o publicar el horóscopo.
   */
  usuarioEditor: string;
}
