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

import co.edu.unbosque.paginanoticia.dto.HoroscopoDTO;
import co.edu.unbosque.paginanoticia.entity.Horoscopo;
import co.edu.unbosque.paginanoticia.entity.UsuarioEditor;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import co.edu.unbosque.paginanoticia.repository.HoroscopoRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioEditorRepository;

/**
 * Clase de prueba para el servicio de horóscopos.
 * Permite validar la lógica de creación, eliminación, actualización y consulta,
 * verificando los diferentes casos de negocio.
 */
@ExtendWith(MockitoExtension.class)
class HoroscopoServiceTest {

    @Mock
    private HoroscopoRepository horoscopoRepo;

    @Mock
    private UsuarioEditorRepository usuarioEditorRepo;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private HoroscopoService service;

    /**
     * Configuración inicial del contexto de seguridad antes de cada prueba.
     */
    @BeforeEach
    void setup() {

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("samuel", null);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    /**
     * Prueba que valida el caso en el que el contenido está vacío.
     */
    @Test
    void deberiaRetornar1SiContenidoEsVacio() {

        HoroscopoDTO dto = new HoroscopoDTO();
        dto.setContenido("");

        int resultado = service.create(dto);

        assertEquals(1, resultado);
    }

    /**
     * Prueba que valida el caso en el que el tipo de publicación es nulo.
     */
    @Test
    void deberiaRetornar2SiTipoPublicacionEsNull() {

        HoroscopoDTO dto = new HoroscopoDTO();
        dto.setContenido("Contenido válido");

        int resultado = service.create(dto);

        assertEquals(2, resultado);
    }

    /**
     * Prueba que valida el caso en el que el editor no existe.
     */
    @Test
    void deberiaRetornar3SiEditorNoExiste() {

        HoroscopoDTO dto = new HoroscopoDTO();
        dto.setContenido("Contenido");
        dto.setTipoPublicacion(TipoPublicacion.HOROSCOPO);

        when(usuarioEditorRepo.findByNombre("samuel"))
                .thenReturn(Optional.empty());

        int resultado = service.create(dto);

        assertEquals(3, resultado);
    }

    /**
     * Prueba que valida la creación correcta de un horóscopo.
     */
    @Test
    void deberiaCrearHoroscopoCorrectamente() {

        HoroscopoDTO dto = new HoroscopoDTO();
        dto.setContenido("Buen horóscopo");
        dto.setTipoPublicacion(TipoPublicacion.HOROSCOPO);

        UsuarioEditor editor = new UsuarioEditor();

        Horoscopo horoscopo = new Horoscopo();

        when(usuarioEditorRepo.findByNombre("samuel"))
                .thenReturn(Optional.of(editor));

        when(mapper.map(dto, Horoscopo.class))
                .thenReturn(horoscopo);

        int resultado = service.create(dto);

        assertEquals(0, resultado);

        verify(horoscopoRepo).save(any(Horoscopo.class));
    }

    /**
     * Prueba que valida el caso en el que el horóscopo no existe.
     */
    @Test
    void deberiaRetornar1SiHoroscopoNoExiste() {

        when(horoscopoRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.deleteById(1L);

        assertEquals(1, resultado);
    }

    /**
     * Prueba que valida el caso en el que el editor no es propietario del horóscopo.
     */
    @Test
    void deberiaRetornar3SiEditorNoEsPropietario() {

        UsuarioEditor editorComentario = new UsuarioEditor();
        editorComentario.setId(1L);

        UsuarioEditor editorActual = new UsuarioEditor();
        editorActual.setId(2L);

        Horoscopo horoscopo = new Horoscopo();
        horoscopo.setUsuarioEditor(editorComentario);

        when(horoscopoRepo.findById(1L))
                .thenReturn(Optional.of(horoscopo));

        when(usuarioEditorRepo.findByNombre("samuel"))
                .thenReturn(Optional.of(editorActual));

        int resultado = service.deleteById(1L);

        assertEquals(3, resultado);
    }

    /**
     * Prueba que valida la eliminación correcta de un horóscopo.
     */
    @Test
    void deberiaEliminarCorrectamente() {

        UsuarioEditor editor = new UsuarioEditor();
        editor.setId(1L);

        Horoscopo horoscopo = new Horoscopo();
        horoscopo.setUsuarioEditor(editor);

        when(horoscopoRepo.findById(1L))
                .thenReturn(Optional.of(horoscopo));

        when(usuarioEditorRepo.findByNombre("samuel"))
                .thenReturn(Optional.of(editor));

        int resultado = service.deleteById(1L);

        assertEquals(0, resultado);

        verify(horoscopoRepo).delete(horoscopo);
    }

    /**
     * Prueba que valida el caso en el que el horóscopo no existe al actualizar.
     */
    @Test
    void deberiaRetornar4SiHoroscopoNoExisteEnUpdate() {

        HoroscopoDTO dto = new HoroscopoDTO();

        when(horoscopoRepo.findById(1L))
                .thenReturn(Optional.empty());

        int resultado = service.updateById(1L, dto);

        assertEquals(4, resultado);
    }

    /**
     * Prueba que valida la actualización correcta de un horóscopo.
     */
    @Test
    void deberiaActualizarCorrectamente() {

        UsuarioEditor editor = new UsuarioEditor();
        editor.setId(1L);

        Horoscopo horoscopo = new Horoscopo();
        horoscopo.setUsuarioEditor(editor);

        HoroscopoDTO dto = new HoroscopoDTO();
        dto.setContenido("Nuevo contenido");
        dto.setTipoPublicacion(TipoPublicacion.HOROSCOPO);

        when(horoscopoRepo.findById(1L))
                .thenReturn(Optional.of(horoscopo));

        when(usuarioEditorRepo.findByNombre("samuel"))
                .thenReturn(Optional.of(editor));

        int resultado = service.updateById(1L, dto);

        assertEquals(0, resultado);

        verify(horoscopoRepo).save(horoscopo);
    }

    /**
     * Prueba que valida el caso en el que el editor no existe al consultar.
     */
    @Test
    void deberiaRetornarNullSiEditorNoExisteEnGetByUsuario() {

        when(usuarioEditorRepo.existsById(1L))
                .thenReturn(false);

        assertNull(service.getByUsuarioEditor(1L));
    }
}