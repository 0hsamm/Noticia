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

import co.edu.unbosque.paginanoticia.dto.UsuarioNormalDTO;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.service.UsuarioNormalService;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con usuarios normales.
 * Permite crear, listar, actualizar y eliminar usuarios normales dentro del sistema.
 */
@RestController
@RequestMapping("/usuarionormal")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UsuarioNormalController {

	@Autowired
	private UsuarioNormalService usuarioNService;

	/**
	 * Crea un nuevo usuario normal en el sistema.
	 *
	 * @param nombre nombre del usuario
	 * @param contrasena contraseña del usuario
	 * @param tipoUsuario tipo de usuario normal
	 * @return respuesta con el estado de la operación
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crearUsuario(@RequestParam String nombre,
			@RequestParam String contrasena,
			@RequestParam TipoUsuario tipoUsuario) {

		UsuarioNormalDTO nuevo = new UsuarioNormalDTO(nombre, contrasena, tipoUsuario);

		int status = usuarioNService.create(nuevo);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Usuario normal creado correctamente", HttpStatus.CREATED);
			case 1:
				return new ResponseEntity<String>("El nombre no puede estar vacio", HttpStatus.BAD_REQUEST);
			case 2:
				return new ResponseEntity<String>("La contrasena no cumple con los requisitos", HttpStatus.BAD_REQUEST);
			case 3:
				return new ResponseEntity<String>("El nombre de usuario ya esta en uso", HttpStatus.CONFLICT);
			default:
				return new ResponseEntity<String>("Error al crear el usuario normal", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Actualiza un usuario normal existente.
	 *
	 * @param id identificador del usuario
	 * @param nombre nuevo nombre
	 * @param contrasena nueva contraseña
	 * @param tipoUsuario tipo de usuario normal
	 * @return respuesta con el estado de la actualización
	 */
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarUsuario(@RequestParam Long id,
			@RequestParam String nombre,
			@RequestParam String contrasena,
			@RequestParam TipoUsuario tipoUsuario) {

		UsuarioNormalDTO actualizar = new UsuarioNormalDTO(nombre, contrasena, tipoUsuario);

		int status = usuarioNService.updateById(id, actualizar);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Usuario normal actualizado correctamente", HttpStatus.ACCEPTED);
			case 1:
				return new ResponseEntity<String>("El nombre no puede estar vacio", HttpStatus.BAD_REQUEST);
			case 2:
				return new ResponseEntity<String>("La contrasena no cumple con los requisitos", HttpStatus.BAD_REQUEST);
			case 3:
				return new ResponseEntity<String>("El nombre de usuario ya esta en uso", HttpStatus.CONFLICT);
			case 4:
				return new ResponseEntity<String>("El usuario no existe", HttpStatus.NOT_FOUND);
			case 5:
				return new ResponseEntity<String>("Usuario no encontrado en sesion", HttpStatus.UNAUTHORIZED);
			case 6:
				return new ResponseEntity<String>("No tienes permisos para actualizar este usuario", HttpStatus.FORBIDDEN);
			default:
				return new ResponseEntity<String>("Error al actualizar el usuario normal", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Elimina un usuario normal por su identificador.
	 *
	 * @param id identificador del usuario
	 * @return respuesta con el estado de la eliminación
	 */
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarUsuario(@RequestParam Long id) {

		int status = usuarioNService.deleteById(id);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Usuario normal eliminado correctamente", HttpStatus.OK);
			case 1:
				return new ResponseEntity<String>("El usuario no existe", HttpStatus.NOT_FOUND);
			case 2:
				return new ResponseEntity<String>("Usuario no encontrado en sesion", HttpStatus.UNAUTHORIZED);
			case 3:
				return new ResponseEntity<String>("No tienes permisos para eliminar este usuario", HttpStatus.FORBIDDEN);
			default:
				return new ResponseEntity<String>("Error al eliminar el usuario normal", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Obtiene todos los usuarios normales registrados.
	 *
	 * @return lista de usuarios normales o estado sin contenido si está vacía
	 */
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<UsuarioNormalDTO>> obtenerTodo() {

		List<UsuarioNormalDTO> usuarios = usuarioNService.getAll();

		if (usuarios.isEmpty()) {
			return new ResponseEntity<List<UsuarioNormalDTO>>(usuarios, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<UsuarioNormalDTO>>(usuarios, HttpStatus.ACCEPTED);
	}
}