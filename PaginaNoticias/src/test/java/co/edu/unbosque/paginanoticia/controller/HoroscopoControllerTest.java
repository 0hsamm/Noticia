package co.edu.unbosque.paginanoticia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.paginanoticia.dto.HoroscopoDTO;
import co.edu.unbosque.paginanoticia.enums.TipoHoroscopo;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import co.edu.unbosque.paginanoticia.service.HoroscopoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Clase de prueba para el controlador de horóscopos.
 * Permite validar la creación, actualización, eliminación y consulta de horóscopos,
 * verificando las respuestas del controlador según los diferentes casos de uso.
 */
class HoroscopoControllerTest {

    @Mock
    private HoroscopoService hService;

    @InjectMocks
    private HoroscopoController horoscopoController;

    /**
     * Inicializa los mocks antes de cada prueba.
     */
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba que valida la creación correcta de un horóscopo.
     */
    @Test
    void testCrearHoroscopoCorrectamente() {

        when(hService.create(org.mockito.ArgumentMatchers.any()))
                .thenReturn(0);

        ResponseEntity<String> response =
                horoscopoController.crearHoroscopo(
                        TipoHoroscopo.ARIES,
                        "contenido",
                        TipoPublicacion.HOROSCOPO,
                        "editor");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Horoscopo creado correctamente", response.getBody());
    }

    /**
     * Prueba que valida la creación de un horóscopo con contenido vacío.
     */
    @Test
    void testCrearHoroscopoContenidoVacio() {

        when(hService.create(org.mockito.ArgumentMatchers.any()))
                .thenReturn(1);

        ResponseEntity<String> response =
                horoscopoController.crearHoroscopo(
                        TipoHoroscopo.ARIES,
                        "",
                        TipoPublicacion.HOROSCOPO,
                        "editor");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Prueba que valida la obtención de todos los horóscopos cuando hay contenido.
     */
    @Test
    void testObtenerTodoConContenido() {

        List<HoroscopoDTO> lista = new ArrayList<>();

        lista.add(
                new HoroscopoDTO(
                        TipoHoroscopo.ARIES,
                        "contenido",
                        TipoPublicacion.HOROSCOPO,
                        "editor"));

        when(hService.getAll())
                .thenReturn(lista);

        ResponseEntity<List<HoroscopoDTO>> response =
                horoscopoController.obtenerTodo();

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    /**
     * Prueba que valida la obtención de horóscopos cuando no hay registros.
     */
    @Test
    void testObtenerTodoVacio() {

        when(hService.getAll())
                .thenReturn(new ArrayList<>());

        ResponseEntity<List<HoroscopoDTO>> response =
                horoscopoController.obtenerTodo();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Prueba que valida la actualización correcta de un horóscopo.
     */
    @Test
    void testActualizarHoroscopoCorrectamente() {

        when(hService.updateById(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(0);

        ResponseEntity<String> response =
                horoscopoController.actualizarHoroscopo(
                        1L,
                        TipoHoroscopo.ARIES,
                        "nuevo contenido",
                        TipoPublicacion.HOROSCOPO,
                        "editor");

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    /**
     * Prueba que valida el caso en el que el horóscopo no existe.
     */
    @Test
    void testActualizarHoroscopoNoExiste() {

        when(hService.updateById(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(4);

        ResponseEntity<String> response =
                horoscopoController.actualizarHoroscopo(
                        1L,
                        TipoHoroscopo.ARIES,
                        "nuevo contenido",
                        TipoPublicacion.HOROSCOPO,
                        "editor");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Prueba que valida la eliminación correcta de un horóscopo.
     */
    @Test
    void testEliminarHoroscopoCorrectamente() {

        when(hService.deleteById(1L))
                .thenReturn(0);

        ResponseEntity<String> response =
                horoscopoController.eliminarHoroscopo(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Prueba que valida el caso en el que el horóscopo a eliminar no existe.
     */
    @Test
    void testEliminarHoroscopoNoExiste() {

        when(hService.deleteById(1L))
                .thenReturn(1);

        ResponseEntity<String> response =
                horoscopoController.eliminarHoroscopo(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}