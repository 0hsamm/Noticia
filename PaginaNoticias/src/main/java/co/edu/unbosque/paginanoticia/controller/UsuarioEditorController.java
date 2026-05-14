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



@RestController
@RequestMapping("/usuarioeditor")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UsuarioEditorController {

	
	@Autowired
	private UsuarioEditorService uEditorService;
	
	
	@PostMapping("/crear")
	public ResponseEntity<String> crearUsuarioEditor(@RequestParam String nombre,@RequestParam String contrasena,@RequestParam TipoUsuario tipoUsuario) {
		UsuarioEditorDTO nuevo = new UsuarioEditorDTO(nombre, contrasena, tipoUsuario);
		int status = uEditorService.create(nuevo);

		if (status == 0) {
			return new ResponseEntity<String>("Creado satisfactoriamente", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<UsuarioEditorDTO>> obtenerTodo(){
		List<UsuarioEditorDTO> usuarioeditorlist = uEditorService.getAll();
		if(usuarioeditorlist.isEmpty()){
			return new ResponseEntity<List<UsuarioEditorDTO>>(usuarioeditorlist, HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<List<UsuarioEditorDTO>>(usuarioeditorlist, HttpStatus.ACCEPTED);
		}
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarUsuarioEditor(@RequestParam Long id, @RequestParam String nombre,@RequestParam String contrasena,@RequestParam TipoUsuario tipoUsuario) {
		UsuarioEditorDTO actualizar = new UsuarioEditorDTO(nombre, contrasena, tipoUsuario);
		int status = uEditorService.updateById(id, actualizar);

		if (status == 0) {
			return new ResponseEntity<String>("Actualizado satisfactoriamente", HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarUsuarioEditor(@RequestParam Long id){
		int status = uEditorService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<String>("Eliminado satisfactoriamente", HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
		}
	}
	
}
