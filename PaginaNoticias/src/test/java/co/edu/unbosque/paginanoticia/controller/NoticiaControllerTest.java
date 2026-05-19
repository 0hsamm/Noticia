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

/**
 * Clase de prueba para el controlador de noticias.
 * Permite validar la creación, actualización, eliminación y consulta de noticias,
 * verificando las respuestas del controlador según los diferentes casos de uso.
 */
class NoticiaControllerTest {

    @Mock
    private NoticiaService nService;

    @InjectMocks
    private NoticiaController noticiaController;

    /**
     * Inicializa los mocks antes de cada prueba.
     */
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba que valida la creación correcta de una noticia.
     */
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

    /**
     * Prueba que valida la creación de una noticia con contenido vacío.
     */
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

    /**
     * Prueba que valida la obtención de noticias cuando hay contenido.
     */
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

    /**
     * Prueba que valida la obtención de noticias cuando no hay registros.
     */
    @Test
    void testObtenerTodoVacio() {

        when(nService.getAll())
                .thenReturn(new ArrayList<>());

        ResponseEntity<List<NoticiaDTO>> response =
                noticiaController.obtenerTodo();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Prueba que valida la actualización correcta de una noticia.
     */
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

    /**
     * Prueba que valida el caso en el que la noticia no existe.
     */
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

    /**
     * Prueba que valida la eliminación correcta de una noticia.
     */
    @Test
    void testEliminarNoticiaCorrectamente() {

        when(nService.deleteById(1L))
                .thenReturn(0);

        ResponseEntity<String> response =
                noticiaController.eliminarNoticia(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Prueba que valida el caso en el que la noticia a eliminar no existe.
     */
    @Test
    void testEliminarNoticiaNoExiste() {

        when(nService.deleteById(1L))
                .thenReturn(1);

        ResponseEntity<String> response =
                noticiaController.eliminarNoticia(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}