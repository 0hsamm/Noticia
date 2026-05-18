import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { TipoUsuario } from '../enums/tipo-usuario.enum';
import { Usuario } from '../models/usuario.model';
import { AuthService } from '../services/auth.service';
import { UsuarioAdministradorService } from '../services/usuario-administrador.service';
import { UsuarioComentaristaService } from '../services/usuario-comentarista.service';
import { UsuarioEditorService } from '../services/usuario-editor.service';
import { UsuarioNormalService } from '../services/usuario-normal.service';

@Component({
  selector: 'app-panel-admin',
  standalone: false,
  templateUrl: './panel-admin.html',
  styleUrl: './panel-admin.css',
})
export class PanelAdmin implements OnInit {
  @Output() addCerrarSesion = new EventEmitter<void>();

  private readonly usuarioAdministradorService = inject(UsuarioAdministradorService);
  private readonly usuarioEditorService = inject(UsuarioEditorService);
  private readonly usuarioComentaristaService = inject(UsuarioComentaristaService);
  private readonly usuarioNormalService = inject(UsuarioNormalService);
  private readonly authService = inject(AuthService);
  private readonly toastr = inject(ToastrService);

  usuariosAdministradores: Usuario[] = [];
  usuariosEditores: Usuario[] = [];
  usuariosComentaristas: Usuario[] = [];
  usuariosNormales: Usuario[] = [];
  seccionActual: TipoUsuario = TipoUsuario.EDITOR;
  formulario: Usuario = this.crearFormularioVacio(TipoUsuario.EDITOR);
  nombreSesion = this.authService.obtenerNombreActual() ?? 'Administrador';
  private cargaSeccionId = 0;

  protected readonly tiposUsuario = TipoUsuario;

  ngOnInit(): void {
    this.cargarSeccionActiva();
  }

  get usuariosActuales(): Usuario[] {
    switch (this.seccionActual) {
      case TipoUsuario.ADMIN:
        return this.usuariosAdministradores;
      case TipoUsuario.COMENTARISTA:
        return this.usuariosComentaristas;
      case TipoUsuario.USUARIO:
        return this.usuariosNormales;
      default:
        return this.usuariosEditores;
    }
  }

  get tituloSeccion(): string {
    switch (this.seccionActual) {
      case TipoUsuario.ADMIN:
        return 'Administradores';
      case TipoUsuario.COMENTARISTA:
        return 'Comentaristas';
      case TipoUsuario.USUARIO:
        return 'Usuarios normales';
      default:
        return 'Editores';
    }
  }

  get permiteGestion(): boolean {
    return this.seccionActual === TipoUsuario.EDITOR || this.seccionActual === TipoUsuario.COMENTARISTA;
  }

  cambiarSeccion(seccion: TipoUsuario): void {
    this.seccionActual = seccion;
    this.cancelarEdicion();
    this.cargarSeccionActiva();
  }

  refrescarPanel(): void {
    this.cargarSeccionActiva();
  }

  guardarUsuario(): void {
    if (!this.permiteGestion) {
      return;
    }

    if (!this.formulario.nombre.trim() || !this.formulario.contrasena) {
      this.toastr.warning('Nombre y contrasena son obligatorios.', 'Campos requeridos');
      return;
    }

    const payload: Usuario = {
      ...this.formulario,
      nombre: this.formulario.nombre.trim(),
      tipoUsuario: this.seccionActual,
    };

    const peticion = this.formulario.id
      ? this.actualizarUsuario(this.formulario.id, payload)
      : this.crearUsuario(payload);

    peticion.subscribe({
      next: (mensaje) => {
        this.toastr.success(mensaje || 'Operacion realizada correctamente', 'Exito');
        this.cancelarEdicion();
        this.cargarSeccionActiva();
      },
      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  editarUsuario(usuario: Usuario): void {
    if (!this.permiteGestion) {
      return;
    }

    this.formulario = {
      ...usuario,
      tipoUsuario: this.seccionActual,
    };
  }

  eliminarUsuario(usuario: Usuario): void {
    if (!this.permiteGestion || !usuario.id) {
      return;
    }

    this.eliminarUsuarioPorId(usuario.id).subscribe({
      next: (mensaje) => {
        this.toastr.success(mensaje || 'Usuario eliminado correctamente', 'Exito');
        this.cargarSeccionActiva();
      },
      error: (error) => {
        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  cancelarEdicion(): void {
    this.formulario = this.crearFormularioVacio(this.seccionActual);
  }

  cerrarSesion(): void {
    this.authService.postCerrarSesion().subscribe(() => {
      this.toastr.info('Sesion cerrada', 'Informacion');
      this.addCerrarSesion.emit();
    });
  }

  private cargarSeccionActiva(): void {
    const seccionSolicitada = this.seccionActual;
    const cargaActual = ++this.cargaSeccionId;

    this.obtenerConsultaActual(seccionSolicitada).subscribe({
      next: (usuarios) => {
        if (cargaActual !== this.cargaSeccionId || seccionSolicitada !== this.seccionActual) {
          return;
        }

        switch (seccionSolicitada) {
          case TipoUsuario.ADMIN:
            this.usuariosAdministradores = usuarios;
            break;
          case TipoUsuario.COMENTARISTA:
            this.usuariosComentaristas = usuarios;
            break;
          case TipoUsuario.USUARIO:
            this.usuariosNormales = usuarios;
            break;
          default:
            this.usuariosEditores = usuarios;
        }
      },
      error: (error) => {
        if (cargaActual !== this.cargaSeccionId) {
          return;
        }

        this.toastr.error(this.obtenerMensajeError(error), 'Error');
      },
    });
  }

  private obtenerConsultaActual(seccion: TipoUsuario): Observable<Usuario[]> {
    switch (seccion) {
      case TipoUsuario.ADMIN:
        return this.usuarioAdministradorService.getAll();
      case TipoUsuario.COMENTARISTA:
        return this.usuarioComentaristaService.getAll();
      case TipoUsuario.USUARIO:
        return this.usuarioNormalService.getAll();
      default:
        return this.usuarioEditorService.getAll();
    }
  }

  private crearUsuario(usuario: Usuario): Observable<string> {
    return this.seccionActual === TipoUsuario.COMENTARISTA
      ? this.usuarioComentaristaService.create(usuario)
      : this.usuarioEditorService.create(usuario);
  }

  private actualizarUsuario(id: number, usuario: Usuario): Observable<string> {
    return this.seccionActual === TipoUsuario.COMENTARISTA
      ? this.usuarioComentaristaService.updateById(id, usuario)
      : this.usuarioEditorService.updateById(id, usuario);
  }

  private eliminarUsuarioPorId(id: number): Observable<string> {
    return this.seccionActual === TipoUsuario.COMENTARISTA
      ? this.usuarioComentaristaService.deleteById(id)
      : this.usuarioEditorService.deleteById(id);
  }

  private crearFormularioVacio(tipoUsuario: TipoUsuario): Usuario {
    return {
      nombre: '',
      contrasena: '',
      tipoUsuario,
    };
  }

  private obtenerMensajeError(error: { error?: unknown }): string {
    if (typeof error?.error === 'string') {
      return error.error;
    }

    return 'No fue posible completar la operacion';
  }
}
