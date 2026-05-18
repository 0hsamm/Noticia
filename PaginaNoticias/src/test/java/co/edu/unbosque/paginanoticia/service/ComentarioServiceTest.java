package co.edu.unbosque.paginanoticia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

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

import co.edu.unbosque.paginanoticia.dto.ComentarioDTO;
import co.edu.unbosque.paginanoticia.entity.Comentario;
import co.edu.unbosque.paginanoticia.entity.Noticia;
import co.edu.unbosque.paginanoticia.entity.UsuarioComentarista;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import co.edu.unbosque.paginanoticia.repository.ComentarioRepository;
import co.edu.unbosque.paginanoticia.repository.HoroscopoRepository;
import co.edu.unbosque.paginanoticia.repository.NoticiaRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioComentaristaRepository;

@ExtendWith(MockitoExtension.class)
class ComentarioServiceTest {

    @Mock
    private ComentarioRepository comentarioRepo;

    @Mock
    private UsuarioComentaristaRepository comentaristaRepo;

    @Mock
    private NoticiaRepository noticiaRepo;

    @Mock
    private HoroscopoRepository horoscopoRepo;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ComentarioService service;

    @BeforeEach
    void setup() {

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("samuel", null);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    
    @Test
    void deberiaRetornar1SiContenidoEsVacio() {

        ComentarioDTO dto = new ComentarioDTO();
        dto.setContenido("");

        int resultado = service.create(dto);

        assertEquals(1, resultado);
    }
    
    @Test
    void deberiaRetornar2SiUsuarioNoExiste() {

        ComentarioDTO dto = new ComentarioDTO();
        dto.setContenido("Hola");

        when(comentaristaRepo.findByNombre("samuel"))
                .thenReturn(Optional.empty());

        int resultado = service.create(dto);

        assertEquals(2, resultado);
    }
    
    @Test
    void deberiaRetornar3SiNoticiaNoExiste() {

        ComentarioDTO dto = new ComentarioDTO();
        dto.setContenido("Comentario");
        dto.setTipoPublicacion(TipoPublicacion.NOTICIA);
        dto.setTituloNoticia("Noticia falsa");

        UsuarioComentarista usuario = new UsuarioComentarista();

        when(comentaristaRepo.findByNombre("samuel"))
                .thenReturn(Optional.of(usuario));

        when(noticiaRepo.findByTitulo("Noticia falsa"))
                .thenReturn(Optional.empty());

        int resultado = service.create(dto);

        assertEquals(3, resultado);
    }
    
    @Test
    void deberiaCrearComentarioCorrectamente() {

        ComentarioDTO dto = new ComentarioDTO();
        dto.setContenido("Buen comentario");
        dto.setTipoPublicacion(TipoPublicacion.NOTICIA);
        dto.setTituloNoticia("Titulo");

        UsuarioComentarista usuario = new UsuarioComentarista();

        Noticia noticia = new Noticia();
        noticia.setTitulo("Titulo");

        when(comentaristaRepo.findByNombre("samuel"))
                .thenReturn(Optional.of(usuario));

        when(noticiaRepo.findByTitulo("Titulo"))
                .thenReturn(Optional.of(noticia));

        int resultado = service.create(dto);

        assertEquals(0, resultado);

        verify(comentarioRepo).save(org.mockito.ArgumentMatchers.any(Comentario.class));
    }
    
    
    @Test
    void deberiaRetornar1SiComentarioNoExiste() {

        when(comentarioRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.deleteById(1L);

        assertEquals(1, resultado);
    }
    
    @Test
    void deberiaRetornar5SiComentarioNoExisteEnUpdate() {

        ComentarioDTO dto = new ComentarioDTO();

        when(comentarioRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.updateById(1L, dto);

        assertEquals(5, resultado);
    }
}