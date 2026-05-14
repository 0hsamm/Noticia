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

		if (status == 0) {
			return new ResponseEntity<String>("Creado satisfactoriamente", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
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

		if (status == 0) {
			return new ResponseEntity<String>("Actualizado satisfactoriamente", HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarUsuarioComentarista(@RequestParam Long id){
		int status = uComentaristaService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<String>("Eliminado satisfactoriamente", HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
		}
	}
	
}
