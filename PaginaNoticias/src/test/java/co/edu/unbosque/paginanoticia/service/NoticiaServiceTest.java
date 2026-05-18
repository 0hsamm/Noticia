package co.edu.unbosque.paginanoticia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import co.edu.unbosque.paginanoticia.dto.NoticiaDTO;
import co.edu.unbosque.paginanoticia.entity.Noticia;
import co.edu.unbosque.paginanoticia.entity.UsuarioEditor;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import co.edu.unbosque.paginanoticia.repository.NoticiaRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioEditorRepository;

@ExtendWith(MockitoExtension.class)
class NoticiaServiceTest {

    @Mock
    private NoticiaRepository noticiaRepo;

    @Mock
    private UsuarioEditorRepository usuarioEditorRepo;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private NoticiaService service;

    @BeforeEach
    void setup() {

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("samuel", null);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    @Test
    void deberiaRetornar1SiTituloEsVacio() {

        NoticiaDTO dto = new NoticiaDTO();
        dto.setTitulo("");
        dto.setContenido("Contenido");

        int resultado = service.create(dto);

        assertEquals(1, resultado);
    }
    
    @Test
    void deberiaRetornar1SiContenidoEsVacio() {

        NoticiaDTO dto = new NoticiaDTO();
        dto.setTitulo("Titulo");
        dto.setContenido("");

        int resultado = service.create(dto);

        assertEquals(1, resultado);
    }
    
    @Test
    void deberiaRetornar2SiTipoPublicacionEsNull() {

        NoticiaDTO dto = new NoticiaDTO();
        dto.setTitulo("Titulo");
        dto.setContenido("Contenido");

        int resultado = service.create(dto);

        assertEquals(2, resultado);
    }
    
    @Test
    void deberiaRetornar3SiEditorNoExiste() {

        NoticiaDTO dto = new NoticiaDTO();
        dto.setTitulo("Titulo");
        dto.setContenido("Contenido");
        dto.setTipoPublicacion(TipoPublicacion.NOTICIA);

        when(usuarioEditorRepo.findByNombre("samuel"))
                .thenReturn(Optional.empty());

        int resultado = service.create(dto);

        assertEquals(3, resultado);
    }
    
    @Test
    void deberiaCrearNoticiaCorrectamente() {

        NoticiaDTO dto = new NoticiaDTO();
        dto.setTitulo("Titulo");
        dto.setContenido("Contenido");
        dto.setTipoPublicacion(TipoPublicacion.NOTICIA);

        UsuarioEditor editor = new UsuarioEditor();

        Noticia noticia = new Noticia();

        when(usuarioEditorRepo.findByNombre("samuel"))
                .thenReturn(Optional.of(editor));

        when(mapper.map(dto, Noticia.class))
                .thenReturn(noticia);

        int resultado = service.create(dto);

        assertEquals(0, resultado);

        verify(noticiaRepo).save(any(Noticia.class));
    }
    
    @Test
    void deberiaRetornar1SiNoticiaNoExiste() {

        when(noticiaRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.deleteById(1L);

        assertEquals(1, resultado);
    }
    
    @Test
    void deberiaRetornar3SiUsuarioNoEsPropietario() {

        UsuarioEditor editorNoticia = new UsuarioEditor();
        editorNoticia.setId(1L);

        UsuarioEditor editorActual = new UsuarioEditor();
        editorActual.setId(2L);

        Noticia noticia = new Noticia();
        noticia.setUsuarioEditor(editorNoticia);

        when(noticiaRepo.findById(1L))
                .thenReturn(Optional.of(noticia));

        when(usuarioEditorRepo.findByNombre("samuel"))
                .thenReturn(Optional.of(editorActual));

        int resultado = service.deleteById(1L);

        assertEquals(3, resultado);
    }
    
    @Test
    void deberiaEliminarNoticiaCorrectamente() {

        UsuarioEditor editor = new UsuarioEditor();
        editor.setId(1L);

        Noticia noticia = new Noticia();
        noticia.setUsuarioEditor(editor);

        when(noticiaRepo.findById(1L))
                .thenReturn(Optional.of(noticia));

        when(usuarioEditorRepo.findByNombre("samuel"))
                .thenReturn(Optional.of(editor));

        int resultado = service.deleteById(1L);

        assertEquals(0, resultado);

        verify(noticiaRepo).delete(noticia);
    }
    
    @Test
    void deberiaRetornar4SiNoticiaNoExisteEnUpdate() {

        NoticiaDTO dto = new NoticiaDTO();

        when(noticiaRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.updateById(1L, dto);

        assertEquals(4, resultado);
    }
    
    @Test
    void deberiaActualizarNoticiaCorrectamente() {

        UsuarioEditor editor = new UsuarioEditor();
        editor.setId(1L);

        Noticia noticia = new Noticia();
        noticia.setUsuarioEditor(editor);

        NoticiaDTO dto = new NoticiaDTO();
        dto.setTitulo("Nuevo titulo");
        dto.setContenido("Nuevo contenido");
        dto.setTipoPublicacion(TipoPublicacion.NOTICIA);

        when(noticiaRepo.findById(1L))
                .thenReturn(Optional.of(noticia));

        when(usuarioEditorRepo.findByNombre("samuel"))
                .thenReturn(Optional.of(editor));

        int resultado = service.updateById(1L, dto);

        assertEquals(0, resultado);

        verify(noticiaRepo).save(noticia);
    }
    
    @Test
    void deberiaRetornarNullSiUsuarioEditorNoExiste() {

        when(usuarioEditorRepo.existsById(1L))
                .thenReturn(false);

        assertNull(service.getByUsuarioEditor(1L));
    }
}