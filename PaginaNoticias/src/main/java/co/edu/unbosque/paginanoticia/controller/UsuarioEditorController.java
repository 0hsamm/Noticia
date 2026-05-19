package co.edu.unbosque.paginanoticia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.paginanoticia.dto.UsuarioEditorDTO;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.service.UsuarioEditorService;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con usuarios editores.
 * Permite crear, listar, actualizar y eliminar usuarios editores dentro del sistema.
 */
@RestController
@RequestMapping("/usuarioeditor")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UsuarioEditorController {

	@Autowired
	private UsuarioEditorService uEditorService;

	/**
	 * Crea un nuevo usuario editor en el sistema.
	 *
	 * @param nombre nombre del usuario editor
	 * @param contrasena contraseña del usuario
	 * @param tipoUsuario tipo de usuario editor
	 * @return respuesta con el estado de la operación
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crearUsuarioEditor(@RequestParam String nombre,
			@RequestParam String contrasena,
			@RequestParam TipoUsuario tipoUsuario) {

		UsuarioEditorDTO nuevo = new UsuarioEditorDTO(nombre, contrasena, tipoUsuario);

		int status = uEditorService.create(nuevo);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Usuario editor creado correctamente", HttpStatus.CREATED);
			case 1:
				return new ResponseEntity<String>("El nombre no puede estar vacio", HttpStatus.BAD_REQUEST);
			case 2:
				return new ResponseEntity<String>("La contrasena no cumple con los requisitos", HttpStatus.BAD_REQUEST);
			case 3:
				return new ResponseEntity<String>("El nombre de usuario ya esta en uso", HttpStatus.CONFLICT);
			case 4:
				return new ResponseEntity<String>("Usuario administrador no encontrado en sesion", HttpStatus.UNAUTHORIZED);
			default:
				return new ResponseEntity<String>("Error al crear el usuario editor", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Obtiene todos los usuarios editores registrados.
	 *
	 * @return lista de usuarios editores o estado sin contenido si está vacía
	 */
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<UsuarioEditorDTO>> obtenerTodo() {

		List<UsuarioEditorDTO> usuarios = uEditorService.getAll();

		if (usuarios.isEmpty()) {
			return new ResponseEntity<List<UsuarioEditorDTO>>(usuarios, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<UsuarioEditorDTO>>(usuarios, HttpStatus.ACCEPTED);
	}

	/**
	 * Actualiza un usuario editor existente.
	 *
	 * @param id identificador del usuario editor
	 * @param nombre nuevo nombre
	 * @param contrasena nueva contraseña
	 * @param tipoUsuario tipo de usuario editor
	 * @return respuesta con el estado de la actualización
	 */
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarUsuarioEditor(@RequestParam Long id,
			@RequestParam String nombre,
			@RequestParam String contrasena,
			@RequestParam TipoUsuario tipoUsuario) {

		UsuarioEditorDTO actualizar = new UsuarioEditorDTO(nombre, contrasena, tipoUsuario);

		int status = uEditorService.updateById(id, actualizar);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Usuario editor actualizado correctamente", HttpStatus.ACCEPTED);
			case 1:
				return new ResponseEntity<String>("El nombre no puede estar vacio", HttpStatus.BAD_REQUEST);
			case 2:
				return new ResponseEntity<String>("La contrasena no cumple con los requisitos", HttpStatus.BAD_REQUEST);
			case 3:
				return new ResponseEntity<String>("El nombre de usuario ya esta en uso", HttpStatus.CONFLICT);
			case 4:
				return new ResponseEntity<String>("El usuario editor no existe", HttpStatus.NOT_FOUND);
			case 5:
				return new ResponseEntity<String>("Usuario administrador no encontrado en sesion", HttpStatus.UNAUTHORIZED);
			default:
				return new ResponseEntity<String>("Error al actualizar el usuario editor", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Elimina un usuario editor por su identificador.
	 *
	 * @param id identificador del usuario editor
	 * @return respuesta con el estado de la eliminación
	 */
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarUsuarioEditor(@RequestParam Long id) {

		int status = uEditorService.deleteById(id);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Usuario editor eliminado correctamente", HttpStatus.OK);
			case 1:
				return new ResponseEntity<String>("El usuario editor no existe", HttpStatus.NOT_FOUND);
			case 2:
				return new ResponseEntity<String>("Usuario administrador no encontrado en sesion", HttpStatus.UNAUTHORIZED);
			default:
				return new ResponseEntity<String>("Error al eliminar el usuario editor", HttpStatus.BAD_REQUEST);
		}
	}
}