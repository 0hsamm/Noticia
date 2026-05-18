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

import co.edu.unbosque.paginanoticia.dto.UsuarioNormalDTO;
import co.edu.unbosque.paginanoticia.entity.UsuarioNormal;
import co.edu.unbosque.paginanoticia.repository.UsuarioAdministradorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioComentaristaRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioEditorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioNormalRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioNormalServiceTest {

    @Mock
    private UsuarioNormalRepository usuarioNormalRepo;

    @Mock
    private UsuarioAdministradorRepository usuarioAdministradorRepo;

    @Mock
    private UsuarioEditorRepository usuarioEditorRepo;

    @Mock
    private UsuarioComentaristaRepository usuarioComentaristaRepo;

    @Mock
    private ModelMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioNormalService service;

    @BeforeEach
    void setup() {

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("usuario", null);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void deberiaRetornar1SiNombreEsVacio() {

        UsuarioNormalDTO dto = new UsuarioNormalDTO();
        dto.setNombre("");

        int resultado = service.create(dto);

        assertEquals(1, resultado);
    }
    
    @Test
    void deberiaRetornar2SiContrasenaEsInvalida() {

        UsuarioNormalDTO dto = new UsuarioNormalDTO();
        dto.setNombre("Samuel");
        dto.setContrasena("123");

        int resultado = service.create(dto);

        assertEquals(2, resultado);
    }

    
    @Test
    void deberiaRetornar3SiNombreYaExiste() {

        UsuarioNormalDTO dto = new UsuarioNormalDTO();
        dto.setNombre("Samuel");
        dto.setContrasena("12345678");

        when(usuarioNormalRepo.findByNombre("Samuel"))
                .thenReturn(Optional.of(new UsuarioNormal()));

        int resultado = service.create(dto);

        assertEquals(3, resultado);
    }
    
    @Test
    void deberiaCrearUsuarioCorrectamente() {

        UsuarioNormalDTO dto = new UsuarioNormalDTO();
        dto.setNombre("Samuel");
        dto.setContrasena("12345678");

        UsuarioNormal usuario = new UsuarioNormal();

        when(mapper.map(dto, UsuarioNormal.class))
                .thenReturn(usuario);

        when(passwordEncoder.encode("12345678"))
                .thenReturn("HASH");

        int resultado = service.create(dto);

        assertEquals(0, resultado);

        verify(usuarioNormalRepo)
                .save(any(UsuarioNormal.class));
    }
    
    @Test
    void deberiaRetornar1SiUsuarioNoExiste() {

        when(usuarioNormalRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.deleteById(1L);

        assertEquals(1, resultado);
    }
    
    @Test
    void deberiaRetornar2SiUsuarioAutenticadoNoExiste() {

        UsuarioNormal usuario = new UsuarioNormal();

        when(usuarioNormalRepo.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioNormalRepo.findByNombre("usuario"))
                .thenReturn(Optional.empty());

        int resultado = service.deleteById(1L);

        assertEquals(2, resultado);
    }
    
    @Test
    void deberiaRetornar3SiUsuarioNoEsPropietario() {

        UsuarioNormal usuarioActual = new UsuarioNormal();
        usuarioActual.setId(2L);

        UsuarioNormal usuarioEliminar = new UsuarioNormal();
        usuarioEliminar.setId(1L);

        when(usuarioNormalRepo.findById(1L))
                .thenReturn(Optional.of(usuarioEliminar));

        when(usuarioNormalRepo.findByNombre("usuario"))
                .thenReturn(Optional.of(usuarioActual));

        int resultado = service.deleteById(1L);

        assertEquals(3, resultado);
    }
    
    @Test
    void deberiaEliminarUsuarioCorrectamente() {

        UsuarioNormal usuario = new UsuarioNormal();
        usuario.setId(1L);

        when(usuarioNormalRepo.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioNormalRepo.findByNombre("usuario"))
                .thenReturn(Optional.of(usuario));

        int resultado = service.deleteById(1L);

        assertEquals(0, resultado);

        verify(usuarioNormalRepo).delete(usuario);
    }
    
    @Test
    void deberiaRetornar4SiUsuarioNoExisteEnUpdate() {

        UsuarioNormalDTO dto = new UsuarioNormalDTO();

        when(usuarioNormalRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.updateById(1L, dto);

        assertEquals(4, resultado);
    }
    
    @Test
    void deberiaActualizarUsuarioCorrectamente() {

        UsuarioNormal usuario = new UsuarioNormal();
        usuario.setId(1L);
        usuario.setNombre("Viejo");

        UsuarioNormalDTO dto = new UsuarioNormalDTO();
        dto.setNombre("Nuevo");
        dto.setContrasena("12345678");

        when(usuarioNormalRepo.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioNormalRepo.findByNombre("usuario"))
                .thenReturn(Optional.of(usuario));

        when(passwordEncoder.encode("12345678"))
                .thenReturn("HASH");

        int resultado = service.updateById(1L, dto);

        assertEquals(0, resultado);

        verify(usuarioNormalRepo).save(usuario);
    }
}