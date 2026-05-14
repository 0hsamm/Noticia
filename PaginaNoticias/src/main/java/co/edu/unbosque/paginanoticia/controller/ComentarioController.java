package co.edu.unbosque.paginanoticia.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.paginanoticia.dto.ComentarioDTO;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import co.edu.unbosque.paginanoticia.service.ComentarioService;

@RestController
@RequestMapping("/comentario")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;


    @PostMapping("/create")
    public ResponseEntity<String> crearComentario(@RequestParam String contenido,@RequestParam LocalDateTime fecha,@RequestParam Long comentaristaId,@RequestParam Long noticiaId,@RequestParam
			Long horoscopoId,@RequestParam TipoPublicacion tipoPublicacion) {
    	ComentarioDTO  nuevo = new ComentarioDTO (contenido, fecha, comentaristaId, noticiaId, horoscopoId, tipoPublicacion);
    	int status = comentarioService.create(nuevo);

		if (status == 0) {
			return new ResponseEntity<String>("Creado satisfactoriamente", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        int status = comentarioService.deleteById(id);

        if (status == 0) {

            return new ResponseEntity<>("Comentario eliminado",HttpStatus.OK);
        }

        return new ResponseEntity<>("Comentario no encontrado",HttpStatus.NOT_FOUND);
    }

  

    @PutMapping("/actualizar")
	public ResponseEntity<String> actualizarComentario(@RequestParam Long id, @RequestParam String contenido,@RequestParam LocalDateTime fecha,@RequestParam Long comentaristaId,@RequestParam Long noticiaId,@RequestParam
			Long horoscopoId,@RequestParam TipoPublicacion tipoPublicacion) {
    	ComentarioDTO actualizar = new ComentarioDTO(contenido, fecha, comentaristaId, noticiaId, horoscopoId, tipoPublicacion);
		int status = comentarioService.updateById(id, actualizar);

		if (status == 0) {
			return new ResponseEntity<String>("Actualizado satisfactoriamente", HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<List<ComentarioDTO>> getByHoroscopo(
                    @PathVariable Long id) {

        List<ComentarioDTO> lista = comentarioService.getByHoroscopo(id);

        if (lista.isEmpty()) {

            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}