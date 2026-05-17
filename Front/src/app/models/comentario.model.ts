import { TipoHoroscopo } from '../enums/tipo-horoscopo.enum';
import { TipoPublicacion } from '../enums/tipo-publicacion.enum';

export interface Comentario {
  id?: number;
  contenido: string;
  fecha: string;
  nombreComentarista: string;
  tituloNoticia: string;
  signoHoroscopo: TipoHoroscopo;
  horoscopoId?: number;
  tipoPublicacion: TipoPublicacion;
}
