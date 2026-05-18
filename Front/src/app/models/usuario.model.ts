import { TipoUsuario } from '../enums/tipo-usuario.enum';

export interface Usuario {
  id?: number;
  nombre: string;
  contrasena: string;
  tipoUsuario: TipoUsuario;
}
