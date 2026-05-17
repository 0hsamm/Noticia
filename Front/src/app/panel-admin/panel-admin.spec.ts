import { TestBed } from '@angular/core/testing';
import { Subject, of } from 'rxjs';
import { vi } from 'vitest';
import { AppModule } from '../app-module';
import { TipoUsuario } from '../enums/tipo-usuario.enum';
import { Usuario } from '../models/usuario.model';
import { PanelAdmin } from './panel-admin';

describe('PanelAdmin', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppModule],
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(PanelAdmin);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });

  it('ignores stale table responses when switching sections quickly', () => {
    const fixture = TestBed.createComponent(PanelAdmin);
    const component = fixture.componentInstance;
    const mutableComponent = component as unknown as Record<string, unknown>;
    const editorSubject = new Subject<Usuario[]>();
    const comentaristaSubject = new Subject<Usuario[]>();

    mutableComponent['usuarioEditorService'] = {
      getAll: () => editorSubject.asObservable(),
      create: () => of(''),
      updateById: () => of(''),
      deleteById: () => of(''),
    };
    mutableComponent['usuarioComentaristaService'] = {
      getAll: () => comentaristaSubject.asObservable(),
      create: () => of(''),
      updateById: () => of(''),
      deleteById: () => of(''),
    };
    mutableComponent['usuarioAdministradorService'] = { getAll: () => of([]) };
    mutableComponent['usuarioNormalService'] = { getAll: () => of([]) };
    mutableComponent['toastr'] = {
      warning: vi.fn(),
      success: vi.fn(),
      error: vi.fn(),
      info: vi.fn(),
    };

    component.ngOnInit();
    component.cambiarSeccion(TipoUsuario.COMENTARISTA);

    editorSubject.next([
      {
        id: 1,
        nombre: 'editor-demo',
        contrasena: '12345678',
        tipoUsuario: TipoUsuario.EDITOR,
      },
    ]);

    expect(component.seccionActual).toBe(TipoUsuario.COMENTARISTA);
    expect(component.usuariosEditores).toEqual([]);
    expect(component.usuariosActuales).toEqual([]);

    comentaristaSubject.next([
      {
        id: 2,
        nombre: 'comentarista-demo',
        contrasena: '12345678',
        tipoUsuario: TipoUsuario.COMENTARISTA,
      },
    ]);

    expect(component.usuariosActuales).toEqual([
      {
        id: 2,
        nombre: 'comentarista-demo',
        contrasena: '12345678',
        tipoUsuario: TipoUsuario.COMENTARISTA,
      },
    ]);
  });
});
