import { TipoHoroscopo } from '../enums/tipo-horoscopo.enum';
import { TipoPublicacion } from '../enums/tipo-publicacion.enum';

/**
 * Interface que representa la estructura de un comentario
 * realizado dentro de la aplicación.
 */
export interface Comentario {

  /**
   * Identificador único del comentario.
   * Este campo es opcional porque puede ser generado automáticamente.
   */
  id?: number;

  /**
   * Contenido o texto del comentario realizado por el usuario.
   */
  contenido: string;

  /**
   * Fecha en la que se realizó el comentario.
   */
  fecha: string;

  /**
   * Nombre de la persona que realiza el comentario.
   */
  nombreComentarista: string;

  /**
   * Título de la noticia asociada al comentario.
   */
  tituloNoticia: string;

  /**
   * Signo zodiacal relacionado con el comentario.
   */
  signoHoroscopo: TipoHoroscopo;

  /**
   * Identificador del horóscopo relacionado.
   * Campo opcional dependiendo del contexto de uso.
   */
  horoscopoId?: number;

  /**
   * Tipo de publicación al que pertenece el comentario.
   */
  tipoPublicacion: TipoPublicacion;
}
