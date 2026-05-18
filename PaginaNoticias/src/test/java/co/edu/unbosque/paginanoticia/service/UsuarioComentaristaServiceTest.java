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

import co.edu.unbosque.paginanoticia.dto.UsuarioComentaristaDTO;
import co.edu.unbosque.paginanoticia.entity.UsuarioAdministrador;
import co.edu.unbosque.paginanoticia.entity.UsuarioComentarista;
import co.edu.unbosque.paginanoticia.repository.UsuarioAdministradorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioComentaristaRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioEditorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioNormalRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioComentaristaServiceTest {

    @Mock
    private UsuarioComentaristaRepository usuarioComentaristaRepo;

    @Mock
    private UsuarioAdministradorRepository usuarioAdministradorRepo;

    @Mock
    private UsuarioEditorRepository usuarioEditorRepo;

    @Mock
    private UsuarioNormalRepository usuarioNormalRepo;

    @Mock
    private ModelMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioComentaristaService service;

    @BeforeEach
    void setup() {

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("admin", null);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    
    @Test
    void deberiaRetornar4SiAdminNoExiste() {

        UsuarioComentaristaDTO dto = new UsuarioComentaristaDTO();

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.empty());

        int resultado = service.create(dto);

        assertEquals(4, resultado);
    }
    
    @Test
    void deberiaRetornar1SiNombreEsVacio() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioComentaristaDTO dto = new UsuarioComentaristaDTO();
        dto.setNombre("");

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        int resultado = service.create(dto);

        assertEquals(1, resultado);
    }
    
    @Test
    void deberiaRetornar2SiContrasenaEsInvalida() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioComentaristaDTO dto = new UsuarioComentaristaDTO();
        dto.setNombre("Samuel");
        dto.setContrasena("123");

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        int resultado = service.create(dto);

        assertEquals(2, resultado);
    }
    
    @Test
    void deberiaRetornar3SiNombreYaExiste() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioComentaristaDTO dto = new UsuarioComentaristaDTO();
        dto.setNombre("Samuel");
        dto.setContrasena("12345678");

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        when(usuarioAdministradorRepo.findByNombre("Samuel"))
                .thenReturn(Optional.of(new UsuarioAdministrador()));

        int resultado = service.create(dto);

        assertEquals(3, resultado);
    }
    
    @Test
    void deberiaCrearComentaristaCorrectamente() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioComentaristaDTO dto = new UsuarioComentaristaDTO();
        dto.setNombre("Samuel");
        dto.setContrasena("12345678");

        UsuarioComentarista comentarista = new UsuarioComentarista();

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        when(mapper.map(dto, UsuarioComentarista.class))
                .thenReturn(comentarista);

        when(passwordEncoder.encode("12345678"))
                .thenReturn("HASH");

        int resultado = service.create(dto);

        assertEquals(0, resultado);

        verify(usuarioComentaristaRepo)
                .save(any(UsuarioComentarista.class));
    }
    
    @Test
    void deberiaRetornar1SiComentaristaNoExiste() {

        when(usuarioComentaristaRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.deleteById(1L);

        assertEquals(1, resultado);
    }
    
    @Test
    void deberiaRetornar2SiAdminNoExisteEnDelete() {

        UsuarioComentarista comentarista = new UsuarioComentarista();

        when(usuarioComentaristaRepo.findById(1L))
                .thenReturn(Optional.of(comentarista));

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.empty());

        int resultado = service.deleteById(1L);

        assertEquals(2, resultado);
    }
    
    @Test
    void deberiaEliminarComentaristaCorrectamente() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioComentarista comentarista = new UsuarioComentarista();

        when(usuarioComentaristaRepo.findById(1L))
                .thenReturn(Optional.of(comentarista));

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        int resultado = service.deleteById(1L);

        assertEquals(0, resultado);

        verify(usuarioComentaristaRepo).delete(comentarista);
    }
    
    @Test
    void deberiaRetornar4SiComentaristaNoExisteEnUpdate() {

        UsuarioComentaristaDTO dto = new UsuarioComentaristaDTO();

        when(usuarioComentaristaRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.updateById(1L, dto);

        assertEquals(4, resultado);
    }
    
    @Test
    void deberiaActualizarComentaristaCorrectamente() {

        UsuarioAdministrador admin = new UsuarioAdministrador();

        UsuarioComentarista comentarista = new UsuarioComentarista();
        comentarista.setNombre("Viejo");

        UsuarioComentaristaDTO dto = new UsuarioComentaristaDTO();
        dto.setNombre("Nuevo");
        dto.setContrasena("12345678");

        when(usuarioComentaristaRepo.findById(1L))
                .thenReturn(Optional.of(comentarista));

        when(usuarioAdministradorRepo.findByNombre("admin"))
                .thenReturn(Optional.of(admin));

        when(passwordEncoder.encode("12345678"))
                .thenReturn("HASH");

        int resultado = service.updateById(1L, dto);

        assertEquals(0, resultado);

        verify(usuarioComentaristaRepo).save(comentarista);
    }
}