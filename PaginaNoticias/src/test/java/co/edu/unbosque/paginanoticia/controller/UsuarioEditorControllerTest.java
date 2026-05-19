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

import co.edu.unbosque.paginanoticia.dto.UsuarioEditorDTO;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.service.UsuarioEditorService;

/**
 * Clase de prueba para el controlador de usuarios editor.
 * Permite validar la creación, actualización, eliminación y consulta de usuarios editor,
 * verificando las respuestas del controlador según los diferentes casos de uso.
 */
class UsuarioEditorControllerTest {

	private UsuarioEditorController controller;
	private UsuarioEditorService service;

	/**
	 * Configuración inicial de los mocks y del controlador antes de cada prueba.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		service = Mockito.mock(UsuarioEditorService.class);

		controller = new UsuarioEditorController();

		try {
			java.lang.reflect.Field field = UsuarioEditorController.class.getDeclaredField("uEditorService");
			field.setAccessible(true);
			field.set(controller, service);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prueba que valida la creación correcta de un usuario editor.
	 */
	@Test
	void testCrearUsuarioEditorCreado() {

		when(service.create(Mockito.any(UsuarioEditorDTO.class))).thenReturn(0);

		ResponseEntity<String> response = controller.crearUsuarioEditor(
				"samuel",
				"12345678",
				TipoUsuario.EDITOR
		);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Usuario editor creado correctamente", response.getBody());
	}

	/**
	 * Prueba que valida la creación de un usuario editor con nombre vacío.
	 */
	@Test
	void testCrearUsuarioEditorNombreVacio() {

		when(service.create(Mockito.any(UsuarioEditorDTO.class))).thenReturn(1);

		ResponseEntity<String> response = controller.crearUsuarioEditor(
				"",
				"12345678",
				TipoUsuario.EDITOR
		);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("El nombre no puede estar vacio", response.getBody());
	}

	/**
	 * Prueba que valida la obtención de usuarios editor cuando hay datos.
	 */
	@Test
	void testObtenerTodoConDatos() {

		ArrayList<UsuarioEditorDTO> lista = new ArrayList<>();

		lista.add(
				new UsuarioEditorDTO(
						"samuel",
						"12345678",
						TipoUsuario.EDITOR
				)
		);

		when(service.getAll()).thenReturn(lista);

		ResponseEntity<java.util.List<UsuarioEditorDTO>> response = controller.obtenerTodo();

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertEquals(1, response.getBody().size());
	}

	/**
	 * Prueba que valida la obtención de usuarios editor cuando no hay datos.
	 */
	@Test
	void testObtenerTodoVacio() {

		when(service.getAll()).thenReturn(new ArrayList<UsuarioEditorDTO>());

		ResponseEntity<java.util.List<UsuarioEditorDTO>> response = controller.obtenerTodo();

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	/**
	 * Prueba que valida la actualización correcta de un usuario editor.
	 */
	@Test
	void testActualizarUsuarioEditorCorrectamente() {

		when(service.updateById(Mockito.eq(1L), Mockito.any(UsuarioEditorDTO.class))).thenReturn(0);

		ResponseEntity<String> response = controller.actualizarUsuarioEditor(
				1L,
				"samuel",
				"12345678",
				TipoUsuario.EDITOR
		);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertEquals("Usuario editor actualizado correctamente", response.getBody());
	}

	/**
	 * Prueba que valida la eliminación correcta de un usuario editor.
	 */
	@Test
	void testEliminarUsuarioEditorCorrectamente() {

		when(service.deleteById(1L)).thenReturn(0);

		ResponseEntity<String> response = controller.eliminarUsuarioEditor(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Usuario editor eliminado correctamente", response.getBody());
	}

	/**
	 * Prueba que valida el caso en el que el usuario editor no existe.
	 */
	@Test
	void testEliminarUsuarioEditorNoExiste() {

		when(service.deleteById(1L)).thenReturn(1);

		ResponseEntity<String> response = controller.eliminarUsuarioEditor(1L);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("El usuario editor no existe", response.getBody());
	}
}