package co.edu.unbosque.paginanoticia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.paginanoticia.dto.UsuarioComentaristaDTO;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.service.UsuarioComentaristaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Clase de prueba para el controlador de usuarios comentaristas.
 * Permite validar la creación, actualización, eliminación y consulta de usuarios comentaristas,
 * verificando las respuestas del controlador según los diferentes casos de uso.
 */
class UsuarioComentaristaControllerTest {

    @Mock
    private UsuarioComentaristaService uComentaristaService;

    @InjectMocks
    private UsuarioComentaristaController usuarioComentaristaController;

    /**
     * Inicializa los mocks antes de cada prueba.
     */
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba que valida la creación correcta de un usuario comentarista.
     */
    @Test
    void testCrearUsuarioComentaristaCorrectamente() {

        when(uComentaristaService.create(org.mockito.ArgumentMatchers.any()))
                .thenReturn(0);

        ResponseEntity<String> response =
                usuarioComentaristaController.crearUsuarioComentarista(
                        "comentarista",
                        "12345678",
                        TipoUsuario.COMENTARISTA);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(
                "Usuario comentarista creado correctamente",
                response.getBody());
    }

    /**
     * Prueba que valida la creación de un usuario comentarista con nombre vacío.
     */
    @Test
    void testCrearUsuarioComentaristaNombreVacio() {

        when(uComentaristaService.create(org.mockito.ArgumentMatchers.any()))
                .thenReturn(1);

        ResponseEntity<String> response =
                usuarioComentaristaController.crearUsuarioComentarista(
                        "",
                        "12345678",
                        TipoUsuario.COMENTARISTA);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Prueba que valida la obtención de usuarios comentaristas cuando hay contenido.
     */
    @Test
    void testObtenerTodoConContenido() {

        List<UsuarioComentaristaDTO> lista = new ArrayList<>();

        lista.add(
                new UsuarioComentaristaDTO(
                        "comentarista",
                        "12345678",
                        TipoUsuario.COMENTARISTA));

        when(uComentaristaService.getAll())
                .thenReturn(lista);

        ResponseEntity<List<UsuarioComentaristaDTO>> response =
                usuarioComentaristaController.obtenerTodo();

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    /**
     * Prueba que valida la obtención de usuarios comentaristas cuando no hay registros.
     */
    @Test
    void testObtenerTodoVacio() {

        when(uComentaristaService.getAll())
                .thenReturn(new ArrayList<>());

        ResponseEntity<List<UsuarioComentaristaDTO>> response =
                usuarioComentaristaController.obtenerTodo();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Prueba que valida la actualización correcta de un usuario comentarista.
     */
    @Test
    void testActualizarUsuarioComentaristaCorrectamente() {

        when(uComentaristaService.updateById(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(0);

        ResponseEntity<String> response =
                usuarioComentaristaController.actualizarUsuarioComentarista(
                        1L,
                        "nuevoComentarista",
                        "12345678",
                        TipoUsuario.COMENTARISTA);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    /**
     * Prueba que valida el caso en el que el usuario comentarista no existe.
     */
    @Test
    void testActualizarUsuarioComentaristaNoExiste() {

        when(uComentaristaService.updateById(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(4);

        ResponseEntity<String> response =
                usuarioComentaristaController.actualizarUsuarioComentarista(
                        1L,
                        "nuevoComentarista",
                        "12345678",
                        TipoUsuario.COMENTARISTA);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Prueba que valida la eliminación correcta de un usuario comentarista.
     */
    @Test
    void testEliminarUsuarioComentaristaCorrectamente() {

        when(uComentaristaService.deleteById(1L))
                .thenReturn(0);

        ResponseEntity<String> response =
                usuarioComentaristaController.eliminarUsuarioComentarista(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Prueba que valida el caso en el que el usuario comentarista no existe.
     */
    @Test
    void testEliminarUsuarioComentaristaNoExiste() {

        when(uComentaristaService.deleteById(1L))
                .thenReturn(1);

        ResponseEntity<String> response =
                usuarioComentaristaController.eliminarUsuarioComentarista(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}