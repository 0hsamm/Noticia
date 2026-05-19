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

import co.edu.unbosque.paginanoticia.dto.UsuarioAdministradorDTO;
import co.edu.unbosque.paginanoticia.entity.UsuarioAdministrador;
import co.edu.unbosque.paginanoticia.repository.UsuarioAdministradorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioComentaristaRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioEditorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioNormalRepository;

/**
 * Clase de prueba para el servicio de usuario administrador.
 * Permite validar la lógica de creación, actualización, eliminación y validaciones
 * de seguridad relacionadas con el administrador autenticado.
 */
@ExtendWith(MockitoExtension.class)
class UsuarioAdministradorServiceTest {

    @Mock
    private UsuarioAdministradorRepository usuarioAdministradorRepo;

    @Mock
    private UsuarioEditorRepository usuarioEditorRepo;

    @Mock
    private UsuarioComentaristaRepository usuarioComentaristaRepo;

    @Mock
    private UsuarioNormalRepository usuarioNormalRepo;

    @Mock
    private ModelMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioAdministradorService service;

    /**
     * Configuración inicial del contexto de seguridad antes de cada prueba.
     */
    @BeforeEach
    void setup() {

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("admin", null);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    /**
     * Prueba que valida el caso en el que el administrador autenticado no existe.
     */
    @Test
    void deberiaRetornar4SiAdminAutenticadoNoExiste() {

        UsuarioAdministradorDTO dto = new UsuarioAdministradorDTO();

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.empty());

        int resultado = service.create(dto);

        assertEquals(4, resultado);
    }

    /**
     * Prueba que valida el caso en el que el nombre está vacío.
     */
    @Test
    void deberiaRetornar1SiNombreEsVacio() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioAdministradorDTO dto = new UsuarioAdministradorDTO();
        dto.setNombre("");

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        int resultado = service.create(dto);

        assertEquals(1, resultado);
    }

    /**
     * Prueba que valida el caso en el que la contraseña es inválida.
     */
    @Test
    void deberiaRetornar2SiContrasenaEsInvalida() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioAdministradorDTO dto = new UsuarioAdministradorDTO();
        dto.setNombre("Samuel");
        dto.setContrasena("123");

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        int resultado = service.create(dto);

        assertEquals(2, resultado);
    }

    /**
     * Prueba que valida el caso en el que el nombre ya existe.
     */
    @Test
    void deberiaRetornar3SiNombreYaExiste() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioAdministradorDTO dto = new UsuarioAdministradorDTO();
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
     * Prueba que valida la creación correcta de un administrador.
     */
    @Test
    void deberiaCrearAdministradorCorrectamente() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioAdministradorDTO dto = new UsuarioAdministradorDTO();
        dto.setNombre("Samuel");
        dto.setContrasena("12345678");

        UsuarioAdministrador nuevoAdmin = new UsuarioAdministrador();

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        when(mapper.map(dto, UsuarioAdministrador.class))
                .thenReturn(nuevoAdmin);

        when(passwordEncoder.encode("12345678"))
                .thenReturn("HASH");

        int resultado = service.create(dto);

        assertEquals(0, resultado);

        verify(usuarioAdministradorRepo)
                .save(any(UsuarioAdministrador.class));
    }

    /**
     * Prueba que valida el caso en el que el administrador no existe al eliminar.
     */
    @Test
    void deberiaRetornar1SiAdministradorNoExiste() {

        when(usuarioAdministradorRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.deleteById(1L);

        assertEquals(1, resultado);
    }

    /**
     * Prueba que valida el caso en el que el administrador no es propietario.
     */
    @Test
    void deberiaRetornar3SiAdminNoEsPropietario() {

        UsuarioAdministrador adminActual = new UsuarioAdministrador();
        adminActual.setId(2L);

        UsuarioAdministrador adminEliminar = new UsuarioAdministrador();
        adminEliminar.setId(1L);

        when(usuarioAdministradorRepo.findById(1L))
                .thenReturn(Optional.of(adminEliminar));

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(adminActual));

        int resultado = service.deleteById(1L);

        assertEquals(3, resultado);
    }

    /**
     * Prueba que valida la eliminación correcta de un administrador.
     */
    @Test
    void deberiaEliminarAdministradorCorrectamente() {

        UsuarioAdministrador admin = new UsuarioAdministrador();
        admin.setId(1L);

        when(usuarioAdministradorRepo.findById(1L))
                .thenReturn(Optional.of(admin));

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        int resultado = service.deleteById(1L);

        assertEquals(0, resultado);

        verify(usuarioAdministradorRepo).delete(admin);
    }

    /**
     * Prueba que valida el caso en el que el administrador no existe al actualizar.
     */
    @Test
    void deberiaRetornar4SiAdminNoExisteEnUpdate() {

        UsuarioAdministradorDTO dto = new UsuarioAdministradorDTO();

        when(usuarioAdministradorRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.updateById(1L, dto);

        assertEquals(4, resultado);
    }

    /**
     * Prueba que valida la actualización correcta de un administrador.
     */
    @Test
    void deberiaActualizarAdministradorCorrectamente() {

        UsuarioAdministrador admin = new UsuarioAdministrador();
        admin.setId(1L);
        admin.setNombre("Viejo");

        UsuarioAdministradorDTO dto = new UsuarioAdministradorDTO();
        dto.setNombre("Nuevo");
        dto.setContrasena("12345678");

        when(usuarioAdministradorRepo.findById(1L))
                .thenReturn(Optional.of(admin));

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        when(passwordEncoder.encode("12345678"))
                .thenReturn("HASH");

        int resultado = service.updateById(1L, dto);

        assertEquals(0, resultado);

        verify(usuarioAdministradorRepo).save(admin);
    }
}