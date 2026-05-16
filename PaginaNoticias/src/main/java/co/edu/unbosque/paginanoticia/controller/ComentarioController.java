package co.edu.unbosque.paginanoticia.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.paginanoticia.dto.ComentarioDTO;
import co.edu.unbosque.paginanoticia.enums.TipoHoroscopo;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import co.edu.unbosque.paginanoticia.service.ComentarioService;

@RestController
@RequestMapping("/comentario")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ComentarioController {

	@Autowired
	private ComentarioService comentarioService;


	@PostMapping("/create")
	public ResponseEntity<String> crearComentario(@RequestParam String contenido,@RequestParam LocalDateTime fecha,@RequestParam String nombreComentarista,@RequestParam String tituloNoticia,@RequestParam
			TipoHoroscopo signoHoroscopo,@RequestParam TipoPublicacion tipoPublicacion) {
		ComentarioDTO  nuevo = new ComentarioDTO (contenido, fecha, nombreComentarista, tituloNoticia, signoHoroscopo, tipoPublicacion);
		int status = comentarioService.create(nuevo);

		switch (status) {

		case 0:
			return new ResponseEntity<String>("Creado satisfactoriamente", HttpStatus.CREATED);

		case 1:
			return new ResponseEntity<String>("El contenido del comentario está vacío", HttpStatus.BAD_REQUEST);

		case 2:
			return new ResponseEntity<String>("El comentarista no existe", HttpStatus.NOT_FOUND);

		case 3:
			return new ResponseEntity<String>("La noticia no existe", HttpStatus.NOT_FOUND);

		case 4:
			return new ResponseEntity<String>("El horóscopo no existe", HttpStatus.NOT_FOUND);

		default:
			return new ResponseEntity<String>("Dato ingresado no válido", HttpStatus.BAD_REQUEST);
		}
	}



	@GetMapping("/mostrartodo")
	public ResponseEntity<List<ComentarioDTO>> obtenerTodo(){
		List<ComentarioDTO> noticialist = comentarioService.getAll();
		if(noticialist.isEmpty()){
			return new ResponseEntity<List<ComentarioDTO>>(noticialist, HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<List<ComentarioDTO>>(noticialist, HttpStatus.ACCEPTED);
		}
	}


	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {

		int status = comentarioService.deleteById(id);

		 switch (status) {

	        case 0:
	            return new ResponseEntity<String>("Comentario eliminado correctamente",HttpStatus.OK);

	        case 1:
	            return new ResponseEntity<String>("El comentario no existe", HttpStatus.NOT_FOUND);

	        case 2:
	            return new ResponseEntity<String>("Usuario no encontrado en sesión", HttpStatus.UNAUTHORIZED);

	        case 3:
	            return new ResponseEntity<String>("No tienes permisos para eliminar este comentario", HttpStatus.FORBIDDEN);

	        default:
	            return new ResponseEntity<String>("Error al eliminar el comentario", HttpStatus.BAD_REQUEST);
	    }
	}



	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarComentario(@RequestParam Long id, @RequestParam String contenido,@RequestParam LocalDateTime fecha,@RequestParam String nombreComentarista,@RequestParam String tituloNoticia,@RequestParam
			TipoHoroscopo signoHoroscopo,@RequestParam TipoPublicacion tipoPublicacion) {
		ComentarioDTO actualizar = new ComentarioDTO(contenido, fecha, nombreComentarista, tituloNoticia, signoHoroscopo, tipoPublicacion);
		int status = comentarioService.updateById(id, actualizar);

		switch (status) {

        case 0:
            return new ResponseEntity<String>("Comentario actualizado correctamente", HttpStatus.OK);

        case 1:
            return new ResponseEntity<String>("El contenido del comentario está vacío", HttpStatus.BAD_REQUEST);

        case 2:
            return new ResponseEntity<String>("Usuario no encontrado en sesión", HttpStatus.UNAUTHORIZED);

        case 3:
            return new ResponseEntity<String>("La noticia no existe", HttpStatus.NOT_FOUND);

        case 4:
            return new ResponseEntity<String>("El horóscopo no existe", HttpStatus.NOT_FOUND);

        case 5:
            return new ResponseEntity<String>("El comentario no existe", HttpStatus.NOT_FOUND);

        default:
            return new ResponseEntity<String>("Error al actualizar el comentario", HttpStatus.BAD_REQUEST);
		}
	}





	@GetMapping("/noticia/{id}")
	public ResponseEntity<List<ComentarioDTO>> getByNoticia(@PathVariable Long id) {

		List<ComentarioDTO> lista = comentarioService.getByNoticia(id);

		if (lista.isEmpty()) {

			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}



	@GetMapping("/horoscopo/{id}")
	public ResponseEntity<List<ComentarioDTO>> getByHoroscopo(@PathVariable Long id) {

		List<ComentarioDTO> lista = comentarioService.getByHoroscopo(id);

		if (lista.isEmpty()) {

			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
}