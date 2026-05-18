package co.edu.unbosque.paginanoticia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.paginanoticia.dto.NoticiaDTO;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import co.edu.unbosque.paginanoticia.service.NoticiaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class NoticiaControllerTest {

    @Mock
    private NoticiaService nService;

    @InjectMocks
    private NoticiaController noticiaController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearNoticiaCorrectamente() {

        when(nService.create(org.mockito.ArgumentMatchers.any()))
                .thenReturn(0);

        ResponseEntity<String> response =
                noticiaController.crearNoticia(
                        "titulo",
                        "contenido",
                        TipoPublicacion.NOTICIA,
                        "editor");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Noticia creada correctamente", response.getBody());
    }

    @Test
    void testCrearNoticiaContenidoVacio() {

        when(nService.create(org.mockito.ArgumentMatchers.any()))
                .thenReturn(1);

        ResponseEntity<String> response =
                noticiaController.crearNoticia(
                        "",
                        "",
                        TipoPublicacion.NOTICIA,
                        "editor");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testObtenerTodoConContenido() {

        List<NoticiaDTO> lista = new ArrayList<>();

        lista.add(
                new NoticiaDTO(
                        "titulo",
                        "contenido",
                        TipoPublicacion.NOTICIA,
                        "editor"));

        when(nService.getAll())
                .thenReturn(lista);

        ResponseEntity<List<NoticiaDTO>> response =
                noticiaController.obtenerTodo();

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void testObtenerTodoVacio() {

        when(nService.getAll())
                .thenReturn(new ArrayList<>());

        ResponseEntity<List<NoticiaDTO>> response =
                noticiaController.obtenerTodo();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testActualizarNoticiaCorrectamente() {

        when(nService.updateById(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(0);

        ResponseEntity<String> response =
                noticiaController.actualizarNoticia(
                        1L,
                        "nuevo titulo",
                        "nuevo contenido",
                        TipoPublicacion.NOTICIA,
                        "editor");

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void testActualizarNoticiaNoExiste() {

        when(nService.updateById(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(4);

        ResponseEntity<String> response =
                noticiaController.actualizarNoticia(
                        1L,
                        "nuevo titulo",
                        "nuevo contenido",
                        TipoPublicacion.NOTICIA,
                        "editor");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testEliminarNoticiaCorrectamente() {

        when(nService.deleteById(1L))
                .thenReturn(0);

        ResponseEntity<String> response =
                noticiaController.eliminarNoticia(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEliminarNoticiaNoExiste() {

        when(nService.deleteById(1L))
                .thenReturn(1);

        ResponseEntity<String> response =
                noticiaController.eliminarNoticia(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}