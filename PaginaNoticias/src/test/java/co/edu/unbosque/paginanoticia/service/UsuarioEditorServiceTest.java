package co.edu.unbosque.paginanoticia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.paginanoticia.dto.UsuarioEditorDTO;
import co.edu.unbosque.paginanoticia.entity.UsuarioAdministrador;
import co.edu.unbosque.paginanoticia.entity.UsuarioEditor;
import co.edu.unbosque.paginanoticia.repository.UsuarioAdministradorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioComentaristaRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioEditorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioNormalRepository;

/**
 * Pruebas unitarias del servicio de UsuarioEditor.
 * Valida la creación, actualización, eliminación y validaciones
 * de negocio relacionadas con el usuario editor.
 */
@ExtendWith(MockitoExtension.class)
class UsuarioEditorServiceTest {

    @Mock
    private UsuarioEditorRepository usuarioEditorRepo;

    @Mock
    private UsuarioAdministradorRepository usuarioAdministradorRepo;

    @Mock
    private UsuarioComentaristaRepository usuarioComentaristaRepo;

    @Mock
    private UsuarioNormalRepository usuarioNormalRepo;

    @Mock
    private ModelMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioEditorService service;

    @BeforeEach
    void setup() {

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("admin", null);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    /**
     * Verifica que retorne 4 cuando el administrador autenticado no existe.
     */
    @Test
    void deberiaRetornar4SiAdminNoExiste() {

        UsuarioEditorDTO dto = new UsuarioEditorDTO();

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.empty());

        int resultado = service.create(dto);

        assertEquals(4, resultado);
    }

    /**
     * Verifica que retorne 1 cuando el nombre del editor está vacío.
     */
    @Test
    void deberiaRetornar1SiNombreEsVacio() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioEditorDTO dto = new UsuarioEditorDTO();
        dto.setNombre("");

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        int resultado = service.create(dto);

        assertEquals(1, resultado);
    }

    /**
     * Verifica que retorne 2 cuando la contraseña no cumple los requisitos.
     */
    @Test
    void deberiaRetornar2SiContrasenaEsInvalida() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioEditorDTO dto = new UsuarioEditorDTO();
        dto.setNombre("Samuel");
        dto.setContrasena("123");

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        int resultado = service.create(dto);

        assertEquals(2, resultado);
    }

    /**
     * Verifica que retorne 3 cuando el nombre del editor ya existe.
     */
    @Test
    void deberiaRetornar3SiNombreYaExiste() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioEditorDTO dto = new UsuarioEditorDTO();
        dto.setNombre("Samuel");
        dto.setContrasena("12345678");

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        when(usuarioAdministradorRepo.findByNombre("Samuel"))
                .thenReturn(Optional.of(new UsuarioAdministrador()));

        int resultado = service.create(dto);

        assertEquals(3, resultado);
    }

    /**
     * Verifica la creación correcta de un usuario editor.
     */
    @Test
    void deberiaCrearEditorCorrectamente() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioEditorDTO dto = new UsuarioEditorDTO();
        dto.setNombre("Samuel");
        dto.setContrasena("12345678");

        UsuarioEditor editor = new UsuarioEditor();

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        when(mapper.map(dto, UsuarioEditor.class))
                .thenReturn(editor);

        when(passwordEncoder.encode("12345678"))
                .thenReturn("HASH");

        int resultado = service.create(dto);

        assertEquals(0, resultado);

        verify(usuarioEditorRepo)
                .save(any(UsuarioEditor.class));
    }

    /**
     * Verifica que retorne 1 cuando el editor no existe.
     */
    @Test
    void deberiaRetornar1SiEditorNoExiste() {

        when(usuarioEditorRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.deleteById(1L);

        assertEquals(1, resultado);
    }

    /**
     * Verifica que retorne 2 cuando el administrador no existe en la eliminación.
     */
    @Test
    void deberiaRetornar2SiAdminNoExisteEnDelete() {

        UsuarioEditor editor = new UsuarioEditor();

        when(usuarioEditorRepo.findById(1L))
                .thenReturn(Optional.of(editor));

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.empty());

        int resultado = service.deleteById(1L);

        assertEquals(2, resultado);
    }

    /**
     * Verifica la eliminación correcta de un editor.
     */
    @Test
    void deberiaEliminarEditorCorrectamente() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioEditor editor = new UsuarioEditor();

        when(usuarioEditorRepo.findById(1L))
                .thenReturn(Optional.of(editor));

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        int resultado = service.deleteById(1L);

        assertEquals(0, resultado);

        verify(usuarioEditorRepo).delete(editor);
    }

    /**
     * Verifica que retorne 4 cuando el editor no existe en actualización.
     */
    @Test
    void deberiaRetornar4SiEditorNoExisteEnUpdate() {

        UsuarioEditorDTO dto = new UsuarioEditorDTO();

        when(usuarioEditorRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.updateById(1L, dto);

        assertEquals(4, resultado);
    }

    /**
     * Verifica la actualización correcta de un editor.
     */
    @Test
    void deberiaActualizarEditorCorrectamente() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioEditor editor = new UsuarioEditor();
        editor.setNombre("Viejo");

        UsuarioEditorDTO dto = new UsuarioEditorDTO();
        dto.setNombre("Nuevo");
        dto.setContrasena("12345678");

        when(usuarioEditorRepo.findById(1L))
                .thenReturn(Optional.of(editor));

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        when(passwordEncoder.encode("12345678"))
                .thenReturn("HASH");

        int resultado = service.updateById(1L, dto);

        assertEquals(0, resultado);

        verify(usuarioEditorRepo).save(editor);
    }
}