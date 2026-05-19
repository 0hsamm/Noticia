import { TipoPublicacion } from '../enums/tipo-publicacion.enum';

/**
 * Interface que representa la estructura de una noticia
 * dentro de la aplicación.
 */
export interface Noticia {

  /**
   * Identificador único de la noticia.
   * Este campo es opcional porque puede ser generado automáticamente.
   */
  id?: number;

  /**
   * Título principal de la noticia.
   */
  titulo: string;

  /**
   * Contenido o cuerpo de la noticia.
   */
  contenido: string;

  /**
   * Tipo de publicación al que pertenece la noticia.
   */
  tipoPublicacion: TipoPublicacion;

  /**
   * Nombre del usuario encargado de editar o publicar la noticia.
   */
  usuarioEditor: string;
}
