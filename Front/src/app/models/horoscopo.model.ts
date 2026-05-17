import { TipoHoroscopo } from '../enums/tipo-horoscopo.enum';
import { TipoPublicacion } from '../enums/tipo-publicacion.enum';

export interface Horoscopo {
  id?: number;
  tipoHoroscopo: TipoHoroscopo;
  contenido: string;
  tipoPublicacion: TipoPublicacion;
  usuarioEditor: string;
}
