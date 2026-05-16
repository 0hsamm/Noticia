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

import co.edu.unbosque.paginanoticia.dto.UsuarioAdministradorDTO;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.service.UsuarioAdministradorService;


@RestController
@RequestMapping("/usuarioadministrador")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UsuarioAdministradoController {


	@Autowired
	private UsuarioAdministradorService uAdminService;



	@PostMapping("/crear")
	public ResponseEntity<String> crearUsuarioAdministrador(@RequestParam String nombre,@RequestParam String contrasena,@RequestParam TipoUsuario tipoUsuario) {
		UsuarioAdministradorDTO nuevo = new UsuarioAdministradorDTO(nombre, contrasena, tipoUsuario);
		int status = uAdminService.create(nuevo);

		switch (status) {

		case 0:
			return new ResponseEntity<String>("Usuario administrador creado correctamente", HttpStatus.CREATED);

		case 1:
			return new ResponseEntity<String>("El nombre no puede estar vacío", HttpStatus.BAD_REQUEST);

		case 2:
			return new ResponseEntity<String>("La contraseña no cumple con los requisitos de tamaño", HttpStatus.BAD_REQUEST);

		case 3:
			return new ResponseEntity<String>("El nombre de usuario ya está en uso", HttpStatus.CONFLICT);

		case 4:
			return new ResponseEntity<String>("No tienes permisos para crear administradores", HttpStatus.UNAUTHORIZED);

		default:
			return new ResponseEntity<String>("Error al crear el usuario administrador", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/mostrartodo")
	public ResponseEntity<List<UsuarioAdministradorDTO>> obtenerTodo(){
		List<UsuarioAdministradorDTO> usuarioadministradorlist = uAdminService.getAll();
		if(usuarioadministradorlist.isEmpty()){
			return new ResponseEntity<List<UsuarioAdministradorDTO>>(usuarioadministradorlist, HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<List<UsuarioAdministradorDTO>>(usuarioadministradorlist, HttpStatus.ACCEPTED);
		}
	}

	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarCarta(@RequestParam Long id, @RequestParam String nombre,@RequestParam String contrasena,@RequestParam TipoUsuario tipoUsuario) {
		UsuarioAdministradorDTO actualizar = new UsuarioAdministradorDTO(nombre, contrasena, tipoUsuario);
		int status = uAdminService.updateById(id, actualizar);

		switch (status) {

		case 0:
			return new ResponseEntity<String>("Usuario administrador actualizado correctamente", HttpStatus.ACCEPTED);

		case 1:
			return new ResponseEntity<String>("El nombre no puede estar vacío", HttpStatus.BAD_REQUEST);

		case 2:
			return new ResponseEntity<String>("La contraseña no cumple con los requisitos", HttpStatus.BAD_REQUEST);

		case 3:
			return new ResponseEntity<String>("El nombre de usuario ya está en uso", HttpStatus.CONFLICT);

		case 4:
			return new ResponseEntity<String>("El usuario administrador no existe", HttpStatus.NOT_FOUND);

		case 5:
			return new ResponseEntity<String>("Usuario en sesión no encontrado", HttpStatus.UNAUTHORIZED);

		case 6:
			return new ResponseEntity<String>("No tienes permisos para actualizar este usuario administrador", HttpStatus.FORBIDDEN);

		default:
			return new ResponseEntity<String>("Error al actualizar el usuario administrador", HttpStatus.BAD_REQUEST);
		}
	}


	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarCarta(@RequestParam Long id){
		int status = uAdminService.deleteById(id);
		switch (status) {

		case 0:
			return new ResponseEntity<String>("Usuario administrador eliminado correctamente", HttpStatus.OK);

		case 1:
			return new ResponseEntity<String>("El usuario administrador no existe", HttpStatus.NOT_FOUND);

		case 2:
			return new ResponseEntity<String>("Usuario en sesión no encontrado", HttpStatus.UNAUTHORIZED);

		case 3:
			return new ResponseEntity<String>("No tienes permisos para eliminar este usuario administrador", HttpStatus.FORBIDDEN);

		default:
			return new ResponseEntity<String>("Error al eliminar el usuario administrador", HttpStatus.BAD_REQUEST);
		}
	}

}
