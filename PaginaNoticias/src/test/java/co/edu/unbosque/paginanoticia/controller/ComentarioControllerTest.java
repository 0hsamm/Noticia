package co.edu.unbosque.paginanoticia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.paginanoticia.dto.ComentarioDTO;
import co.edu.unbosque.paginanoticia.enums.TipoHoroscopo;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import co.edu.unbosque.paginanoticia.service.ComentarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ComentarioControllerTest {

    @Mock
    private ComentarioService comentarioService;

    @InjectMocks
    private ComentarioController comentarioController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearComentarioCorrectamente() {

        when(comentarioService.create(org.mockito.ArgumentMatchers.any())).thenReturn(0);

        ResponseEntity<String> response =
                comentarioController.crearComentario(
                        "contenido",
                        LocalDateTime.now(),
                        "samuel",
                        "noticia",
                        null,
                        null,
                        TipoPublicacion.NOTICIA);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Comentario creado correctamente", response.getBody());
    }

    @Test
    void testCrearComentarioContenidoVacio() {

        when(comentarioService.create(org.mockito.ArgumentMatchers.any()))
                .thenReturn(1);

        ResponseEntity<String> response =
                comentarioController.crearComentario(
                        "",
                        LocalDateTime.now(),
                        "samuel",
                        "noticia",
                        null,
                        null,
                        TipoPublicacion.NOTICIA);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testDeleteCorrectamente() {

        when(comentarioService.deleteById(1L))
                .thenReturn(0);

        ResponseEntity<String> response =
                comentarioController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteComentarioNoExiste() {

        when(comentarioService.deleteById(1L))
                .thenReturn(1);

        ResponseEntity<String> response =
                comentarioController.delete(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testActualizarComentarioCorrectamente() {

        when(comentarioService.updateById(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(0);

        ResponseEntity<String> response =
                comentarioController.actualizarComentario(
                        1L,
                        "nuevo contenido",
                        LocalDateTime.now(),
                        "samuel",
                        "titulo",
                        null,
                        null,
                        TipoPublicacion.NOTICIA);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testActualizarComentarioNoExiste() {

        when(comentarioService.updateById(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(5);

        ResponseEntity<String> response =
                comentarioController.actualizarComentario(
                        1L,
                        "nuevo contenido",
                        LocalDateTime.now(),
                        "samuel",
                        "titulo",
                        null,
                        null,
                        TipoPublicacion.NOTICIA);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testObtenerTodoConContenido() {

        List<ComentarioDTO> lista = new ArrayList<>();

        lista.add(
                new ComentarioDTO(
                        "contenido",
                        LocalDateTime.now(),
                        "samuel",
                        "titulo",
                        TipoHoroscopo.ARIES,
                        TipoPublicacion.HOROSCOPO));

        when(comentarioService.getAll())
                .thenReturn(lista);

        ResponseEntity<List<ComentarioDTO>> response =
                comentarioController.obtenerTodo();

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void testObtenerTodoVacio() {

        when(comentarioService.getAll())
                .thenReturn(new ArrayList<>());

        ResponseEntity<List<ComentarioDTO>> response =
                comentarioController.obtenerTodo();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetByNoticiaConContenido() {

        List<ComentarioDTO> lista = new ArrayList<>();

        lista.add(
                new ComentarioDTO(
                        "contenido",
                        LocalDateTime.now(),
                        "samuel",
                        "titulo",
                        null,
                        TipoPublicacion.NOTICIA));

        when(comentarioService.getByNoticia(1L))
                .thenReturn(lista);

        ResponseEntity<List<ComentarioDTO>> response =
                comentarioController.getByNoticia(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetByHoroscopoVacio() {

        when(comentarioService.getByHoroscopo(1L))
                .thenReturn(new ArrayList<>());

        ResponseEntity<List<ComentarioDTO>> response =
                comentarioController.getByHoroscopo(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}