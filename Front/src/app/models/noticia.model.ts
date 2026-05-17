import { TipoPublicacion } from '../enums/tipo-publicacion.enum';

export interface Noticia {
  id?: number;
  titulo: string;
  contenido: string;
  tipoPublicacion: TipoPublicacion;
  usuarioEditor: string;
}
