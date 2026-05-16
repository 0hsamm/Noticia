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
	public ResponseEntity<String> crearNoticia(@RequestParam String titulo, @RequestParam String contenido, @RequestParam TipoPublicacion tipoPublicacion, @RequestParam String usuarioComentarista) {
		NoticiaDTO nuevo = new NoticiaDTO(titulo, contenido, tipoPublicacion, usuarioComentarista);
		int status = nService.create(nuevo);

		 switch (status) {

	        case 0:
	            return new ResponseEntity<String>("Noticia creada correctamente", HttpStatus.CREATED);

	        case 1:
	            return new ResponseEntity<String>("El contenido de la noticia está vacío", HttpStatus.BAD_REQUEST);

	        case 2:
	            return new ResponseEntity<String>("El tipo de publicación es obligatorio", HttpStatus.BAD_REQUEST);

	        case 3:
	            return new ResponseEntity<String>("El usuario editor no existe en sesión", HttpStatus.UNAUTHORIZED);

	        default:
	            return new ResponseEntity<String>("Error al crear la noticia", HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<String> actualizarNoticia(@RequestParam Long id, @RequestParam String titulo, @RequestParam String contenido, @RequestParam TipoPublicacion tipoPublicacion, @RequestParam String usuarioComentarista) {
		NoticiaDTO actualizar = new NoticiaDTO(titulo, contenido, tipoPublicacion, usuarioComentarista);
		int status = nService.updateById(id, actualizar);

		switch (status) {

        case 0:
            return new ResponseEntity<String>("Noticia actualizada correctamente", HttpStatus.ACCEPTED);

        case 1:
            return new ResponseEntity<String>("El contenido de la noticia está vacío", HttpStatus.BAD_REQUEST);

        case 2:
            return new ResponseEntity<String>("El tipo de publicación es obligatorio", HttpStatus.BAD_REQUEST);

        case 3:
            return new ResponseEntity<String>("Usuario editor no encontrado en sesión", HttpStatus.UNAUTHORIZED);

        case 4:
            return new ResponseEntity<String>("La noticia no existe", HttpStatus.NOT_FOUND);

        case 5:
            return new ResponseEntity<String>("No tienes permisos para modificar esta noticia", HttpStatus.FORBIDDEN);

        default:
            return new ResponseEntity<String>("Error al actualizar la noticia", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarNoticia(@RequestParam Long id){
		int status = nService.deleteById(id);
		switch (status) {

        case 0:
            return new ResponseEntity<String>("Noticia eliminada correctamente", HttpStatus.OK);

        case 1:
            return new ResponseEntity<String>("La noticia no existe", HttpStatus.NOT_FOUND);

        case 2:
            return new ResponseEntity<String>("Usuario editor no encontrado en sesión", HttpStatus.UNAUTHORIZED);

        case 3:
            return new ResponseEntity<String>("No tienes permisos para eliminar esta noticia", HttpStatus.FORBIDDEN);

        default:
            return new ResponseEntity<String>("Error al eliminar la noticia", HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	
	
}
