package co.edu.unbosque.paginanoticia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.edu.unbosque.paginanoticia.dto.UsuarioNormalDTO;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.service.UsuarioNormalService;

class UsuarioNormalControllerTest {

	private UsuarioNormalController controller;
	private UsuarioNormalService service;

	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		service = Mockito.mock(UsuarioNormalService.class);

		controller = new UsuarioNormalController();

		try {

			java.lang.reflect.Field field =
					UsuarioNormalController.class.getDeclaredField("usuarioNService");

			field.setAccessible(true);
			field.set(controller, service);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Test
	void testCrearUsuarioCorrectamente() {

		when(service.create(Mockito.any(UsuarioNormalDTO.class))).thenReturn(0);

		ResponseEntity<String> response = controller.crearUsuario(
				"samuel",
				"12345678",
				TipoUsuario.USUARIO
		);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Usuario normal creado correctamente", response.getBody());
	}

	@Test
	void testCrearUsuarioNombreVacio() {

		when(service.create(Mockito.any(UsuarioNormalDTO.class))).thenReturn(1);

		ResponseEntity<String> response = controller.crearUsuario(
				"",
				"12345678",
				TipoUsuario.USUARIO
		);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("El nombre no puede estar vacio", response.getBody());
	}

	@Test
	void testCrearUsuarioContrasenaInvalida() {

		when(service.create(Mockito.any(UsuarioNormalDTO.class))).thenReturn(2);

		ResponseEntity<String> response = controller.crearUsuario(
				"samuel",
				"123",
				TipoUsuario.USUARIO
		);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("La contrasena no cumple con los requisitos", response.getBody());
	}

	@Test
	void testObtenerTodoConDatos() {

		ArrayList<UsuarioNormalDTO> lista = new ArrayList<>();

		lista.add(
				new UsuarioNormalDTO(
						"samuel",
						"12345678",
						TipoUsuario.USUARIO
				)
		);

		when(service.getAll()).thenReturn(lista);

		ResponseEntity<java.util.List<UsuarioNormalDTO>> response =
				controller.obtenerTodo();

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertEquals(1, response.getBody().size());
	}

	@Test
	void testObtenerTodoVacio() {

		when(service.getAll()).thenReturn(new ArrayList<UsuarioNormalDTO>());

		ResponseEntity<java.util.List<UsuarioNormalDTO>> response =
				controller.obtenerTodo();

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	void testActualizarUsuarioCorrectamente() {

		when(service.updateById(Mockito.eq(1L), Mockito.any(UsuarioNormalDTO.class)))
				.thenReturn(0);

		ResponseEntity<String> response = controller.actualizarUsuario(
				1L,
				"samuel",
				"12345678",
				TipoUsuario.USUARIO
		);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertEquals("Usuario normal actualizado correctamente", response.getBody());
	}

	@Test
	void testActualizarUsuarioNoExiste() {

		when(service.updateById(Mockito.eq(1L), Mockito.any(UsuarioNormalDTO.class)))
				.thenReturn(4);

		ResponseEntity<String> response = controller.actualizarUsuario(
				1L,
				"samuel",
				"12345678",
				TipoUsuario.USUARIO
		);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("El usuario no existe", response.getBody());
	}

	@Test
	void testEliminarUsuarioCorrectamente() {

		when(service.deleteById(1L)).thenReturn(0);

		ResponseEntity<String> response = controller.eliminarUsuario(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Usuario normal eliminado correctamente", response.getBody());
	}

	@Test
	void testEliminarUsuarioSinPermisos() {

		when(service.deleteById(1L)).thenReturn(3);

		ResponseEntity<String> response = controller.eliminarUsuario(1L);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		assertEquals("No tienes permisos para eliminar este usuario", response.getBody());
	}
}