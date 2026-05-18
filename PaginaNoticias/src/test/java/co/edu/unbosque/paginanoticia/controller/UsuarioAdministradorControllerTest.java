package co.edu.unbosque.paginanoticia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.paginanoticia.dto.UsuarioAdministradorDTO;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.service.UsuarioAdministradorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class UsuarioAdministradoControllerTest {

    @Mock
    private UsuarioAdministradorService uAdminService;

    @InjectMocks
    private UsuarioAdministradoController usuarioAdministradoController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuarioAdministradorCorrectamente() {

        when(uAdminService.create(org.mockito.ArgumentMatchers.any()))
                .thenReturn(0);

        ResponseEntity<String> response =
                usuarioAdministradoController.crearUsuarioAdministrador(
                        "admin",
                        "12345678",
                        TipoUsuario.ADMIN);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(
                "Usuario administrador creado correctamente",
                response.getBody());
    }

    @Test
    void testCrearUsuarioAdministradorNombreVacio() {

        when(uAdminService.create(org.mockito.ArgumentMatchers.any()))
                .thenReturn(1);

        ResponseEntity<String> response =
                usuarioAdministradoController.crearUsuarioAdministrador(
                        "",
                        "12345678",
                        TipoUsuario.ADMIN);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testObtenerTodoConContenido() {

        List<UsuarioAdministradorDTO> lista = new ArrayList<>();

        lista.add(
                new UsuarioAdministradorDTO(
                        "admin",
                        "12345678",
                        TipoUsuario.ADMIN));

        when(uAdminService.getAll())
                .thenReturn(lista);

        ResponseEntity<List<UsuarioAdministradorDTO>> response =
                usuarioAdministradoController.obtenerTodo();

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void testObtenerTodoVacio() {

        when(uAdminService.getAll())
                .thenReturn(new ArrayList<>());

        ResponseEntity<List<UsuarioAdministradorDTO>> response =
                usuarioAdministradoController.obtenerTodo();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testActualizarAdministradorCorrectamente() {

        when(uAdminService.updateById(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(0);

        ResponseEntity<String> response =
                usuarioAdministradoController.actualizarCarta(
                        1L,
                        "nuevoAdmin",
                        "12345678",
                        TipoUsuario.ADMIN);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void testActualizarAdministradorNoExiste() {

        when(uAdminService.updateById(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(4);

        ResponseEntity<String> response =
                usuarioAdministradoController.actualizarCarta(
                        1L,
                        "nuevoAdmin",
                        "12345678",
                        TipoUsuario.ADMIN);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testEliminarAdministradorCorrectamente() {

        when(uAdminService.deleteById(1L))
                .thenReturn(0);

        ResponseEntity<String> response =
                usuarioAdministradoController.eliminarCarta(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEliminarAdministradorNoExiste() {

        when(uAdminService.deleteById(1L))
                .thenReturn(1);

        ResponseEntity<String> response =
                usuarioAdministradoController.eliminarCarta(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}