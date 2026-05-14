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

import co.edu.unbosque.paginanoticia.dto.NoticiaDTO;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import co.edu.unbosque.paginanoticia.service.NoticiaService;


@RestController
@RequestMapping("/noticia")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class NoticiaController {

	
	@Autowired
	private NoticiaService nService;
	
	
	@PostMapping("/crear")
	public ResponseEntity<String> crearNoticia(@RequestParam String contenido, @RequestParam TipoPublicacion tipoPublicacion, @RequestParam long usuarioComentaristaId) {
		NoticiaDTO nuevo = new NoticiaDTO(contenido, tipoPublicacion, usuarioComentaristaId);
		int status = nService.create(nuevo);

		if (status == 0) {
			return new ResponseEntity<String>("Creado satisfactoriamente", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<NoticiaDTO>> obtenerTodo(){
		List<NoticiaDTO> noticialist = nService.getAll();
		if(noticialist.isEmpty()){
			return new ResponseEntity<List<NoticiaDTO>>(noticialist, HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<List<NoticiaDTO>>(noticialist, HttpStatus.ACCEPTED);
		}
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarNoticia(@RequestParam Long id, @RequestParam String contenido, @RequestParam TipoPublicacion tipoPublicacion, @RequestParam long usuarioComentaristaId) {
		NoticiaDTO actualizar = new NoticiaDTO(contenido, tipoPublicacion, usuarioComentaristaId);
		int status = nService.updateById(id, actualizar);

		if (status == 0) {
			return new ResponseEntity<String>("Actualizado satisfactoriamente", HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarNoticia(@RequestParam Long id){
		int status = nService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<String>("Eliminado satisfactoriamente", HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	
	
}
