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



@RestController
@RequestMapping("/usuarionormal")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UsuarioNormalController {

	
	@Autowired
	private UsuarioNormalService usuarioNService;
	
	
	@PostMapping("/crear")
	public ResponseEntity<String> crearUsuario(@RequestParam String nombre, @RequestParam String contrasena, @RequestParam TipoUsuario tipoUsuario) {
		UsuarioNormalDTO nuevo = new UsuarioNormalDTO(nombre, contrasena, tipoUsuario);
		int status = usuarioNService.create(nuevo);

		if (status == 0) {
			return new ResponseEntity<String>("Creado satisfactoriamente", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("Error desconocido", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarUsuario(@RequestParam Long id, @RequestParam String nombre, @RequestParam String contrasena, @RequestParam TipoUsuario tipoUsuario) {
		UsuarioNormalDTO actualizar = new UsuarioNormalDTO(nombre, contrasena, tipoUsuario);
		int status = usuarioNService.updateById(id, actualizar);

		if (status == 0) {
			return new ResponseEntity<String>("Actualizado satisfactoriamente", HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<String>("Error desconocido", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarUsuario(@RequestParam Long id) {
		int status = usuarioNService.deleteById(id);

		if (status == 0) {
			return new ResponseEntity<String>("Eliminado satisfactoriamente", HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<String>("Error desconocido", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<UsuarioNormalDTO>> obtenerTodo() {
		List<UsuarioNormalDTO> usuarionormallist = usuarioNService.getAll();
		if (usuarionormallist.isEmpty()) {
			return new ResponseEntity<List<UsuarioNormalDTO>>(usuarionormallist, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<UsuarioNormalDTO>>(usuarionormallist, HttpStatus.ACCEPTED);
		}
	}
	
}
