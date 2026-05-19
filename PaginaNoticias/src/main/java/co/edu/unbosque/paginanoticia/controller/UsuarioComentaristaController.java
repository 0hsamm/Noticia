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

import co.edu.unbosque.paginanoticia.dto.UsuarioComentaristaDTO;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.service.UsuarioComentaristaService;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con usuarios comentaristas.
 * Permite crear, listar, actualizar y eliminar usuarios comentaristas dentro del sistema.
 */
@RestController
@RequestMapping("/usuariocomentarista")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UsuarioComentaristaController {

	@Autowired
	private UsuarioComentaristaService uComentaristaService;

	/**
	 * Crea un nuevo usuario comentarista en el sistema.
	 *
	 * @param nombre nombre del usuario comentarista
	 * @param contrasena contraseña del usuario
	 * @param tipoUsuario tipo de usuario comentarista
	 * @return respuesta con el estado de la operación
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crearUsuarioComentarista(@RequestParam String nombre,
			@RequestParam String contrasena,
			@RequestParam TipoUsuario tipoUsuario) {

		UsuarioComentaristaDTO nuevo = new UsuarioComentaristaDTO(nombre, contrasena, tipoUsuario);

		int status = uComentaristaService.create(nuevo);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Usuario comentarista creado correctamente", HttpStatus.CREATED);
			case 1:
				return new ResponseEntity<String>("El nombre no puede estar vacio", HttpStatus.BAD_REQUEST);
			case 2:
				return new ResponseEntity<String>("La contrasena no cumple con los requisitos", HttpStatus.BAD_REQUEST);
			case 3:
				return new ResponseEntity<String>("El nombre de usuario ya esta en uso", HttpStatus.CONFLICT);
			case 4:
				return new ResponseEntity<String>("Usuario administrador no encontrado en sesion", HttpStatus.UNAUTHORIZED);
			default:
				return new ResponseEntity<String>("Error al crear el usuario comentarista", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Obtiene todos los usuarios comentaristas registrados.
	 *
	 * @return lista de usuarios comentaristas o estado sin contenido si está vacía
	 */
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<UsuarioComentaristaDTO>> obtenerTodo() {

		List<UsuarioComentaristaDTO> usuarios = uComentaristaService.getAll();

		if (usuarios.isEmpty()) {
			return new ResponseEntity<List<UsuarioComentaristaDTO>>(usuarios, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<UsuarioComentaristaDTO>>(usuarios, HttpStatus.ACCEPTED);
	}

	/**
	 * Actualiza un usuario comentarista existente.
	 *
	 * @param id identificador del usuario comentarista
	 * @param nombre nuevo nombre
	 * @param contrasena nueva contraseña
	 * @param tipoUsuario tipo de usuario comentarista
	 * @return respuesta con el estado de la actualización
	 */
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarUsuarioComentarista(@RequestParam Long id,
			@RequestParam String nombre,
			@RequestParam String contrasena,
			@RequestParam TipoUsuario tipoUsuario) {

		UsuarioComentaristaDTO actualizar = new UsuarioComentaristaDTO(nombre, contrasena, tipoUsuario);

		int status = uComentaristaService.updateById(id, actualizar);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Usuario comentarista actualizado correctamente", HttpStatus.ACCEPTED);
			case 1:
				return new ResponseEntity<String>("El nombre no puede estar vacio", HttpStatus.BAD_REQUEST);
			case 2:
				return new ResponseEntity<String>("La contrasena no cumple con los requisitos", HttpStatus.BAD_REQUEST);
			case 3:
				return new ResponseEntity<String>("El nombre de usuario ya esta en uso", HttpStatus.CONFLICT);
			case 4:
				return new ResponseEntity<String>("El usuario comentarista no existe", HttpStatus.NOT_FOUND);
			case 5:
				return new ResponseEntity<String>("Usuario administrador no encontrado en sesion", HttpStatus.UNAUTHORIZED);
			default:
				return new ResponseEntity<String>("Error al actualizar el usuario comentarista", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Elimina un usuario comentarista por su identificador.
	 *
	 * @param id identificador del usuario comentarista
	 * @return respuesta con el estado de la eliminación
	 */
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarUsuarioComentarista(@RequestParam Long id) {

		int status = uComentaristaService.deleteById(id);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Usuario comentarista eliminado correctamente", HttpStatus.OK);
			case 1:
				return new ResponseEntity<String>("El usuario comentarista no existe", HttpStatus.NOT_FOUND);
			case 2:
				return new ResponseEntity<String>("Usuario administrador no encontrado en sesion", HttpStatus.UNAUTHORIZED);
			default:
				return new ResponseEntity<String>("Error al eliminar el usuario comentarista", HttpStatus.BAD_REQUEST);
		}
	}
}