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


@RestController
@RequestMapping("/usuariocomentarista")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UsuarioComentaristaController {

	@Autowired
	private UsuarioComentaristaService uComentaristaService;


	@PostMapping("/crear")
	public ResponseEntity<String> crearUsuarioComentarista(@RequestParam String nombre,@RequestParam String contrasena,@RequestParam TipoUsuario tipoUsuario) {
		UsuarioComentaristaDTO nuevo = new UsuarioComentaristaDTO(nombre, contrasena, tipoUsuario);
		int status = uComentaristaService.create(nuevo);

		switch (status) {

		case 0:
			return new ResponseEntity<String>("Usuario comentarista creado correctamente", HttpStatus.CREATED);

		case 1:
			return new ResponseEntity<String>("El nombre no puede estar vacío", HttpStatus.BAD_REQUEST);

		case 2:
			return new ResponseEntity<String>("La contraseña no cumple con los requisitos", HttpStatus.BAD_REQUEST);

		case 3:
			return new ResponseEntity<String>("El nombre de usuario ya está en uso", HttpStatus.CONFLICT);

		case 4:
			return new ResponseEntity<String>("Usuario administrador no encontrado en sesión", HttpStatus.UNAUTHORIZED);

		default:
			return new ResponseEntity<String>("Error al crear el usuario comentarista", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/mostrartodo")
	public ResponseEntity<List<UsuarioComentaristaDTO>> obtenerTodo(){
		List<UsuarioComentaristaDTO> usuariocomentaristalist = uComentaristaService.getAll();
		if(usuariocomentaristalist.isEmpty()){
			return new ResponseEntity<List<UsuarioComentaristaDTO>>(usuariocomentaristalist, HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<List<UsuarioComentaristaDTO>>(usuariocomentaristalist, HttpStatus.ACCEPTED);
		}
	}

	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarUsuarioComentarista(@RequestParam Long id, @RequestParam String nombre,@RequestParam String contrasena,@RequestParam TipoUsuario tipoUsuario) {
		UsuarioComentaristaDTO actualizar = new UsuarioComentaristaDTO(nombre, contrasena, tipoUsuario);
		int status = uComentaristaService.updateById(id, actualizar);

		switch (status) {

		case 0:
			return new ResponseEntity<String>("Usuario comentarista actualizado correctamente", HttpStatus.ACCEPTED);

		case 1:
			return new ResponseEntity<String>("El nombre no puede estar vacío", HttpStatus.BAD_REQUEST);

		case 2:
			return new ResponseEntity<String>("La contraseña no cumple con los requisitos", HttpStatus.BAD_REQUEST);

		case 3:
			return new ResponseEntity<String>("El nombre de usuario ya está en uso", HttpStatus.CONFLICT);

		case 4:
			return new ResponseEntity<String>("El usuario comentarista no existe", HttpStatus.NOT_FOUND);

		case 5:
			return new ResponseEntity<String>("Usuario administrador no encontrado en sesión", HttpStatus.UNAUTHORIZED);

		default:
			return new ResponseEntity<String>("Error al actualizar el usuario comentarista", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarUsuarioComentarista(@RequestParam Long id){
		int status = uComentaristaService.deleteById(id);
		switch (status) {

		case 0:
			return new ResponseEntity<String>("Usuario comentarista eliminado correctamente", HttpStatus.OK);

		case 1:
			return new ResponseEntity<String>("El usuario comentarista no existe", HttpStatus.NOT_FOUND);

		case 2:
			return new ResponseEntity<String>("Usuario administrador no encontrado en sesión", HttpStatus.UNAUTHORIZED);

		default:
			return new ResponseEntity<String>("Error al eliminar el usuario comentarista", HttpStatus.BAD_REQUEST);
		}
	}

}
