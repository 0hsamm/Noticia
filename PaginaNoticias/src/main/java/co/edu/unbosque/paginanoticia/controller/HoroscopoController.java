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

import co.edu.unbosque.paginanoticia.dto.HoroscopoDTO;
import co.edu.unbosque.paginanoticia.enums.TipoHoroscopo;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import co.edu.unbosque.paginanoticia.service.HoroscopoService;



@RestController
@RequestMapping("/horoscopo")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class HoroscopoController {


	@Autowired
	private HoroscopoService hService;


	@PostMapping("/crear")
	public ResponseEntity<String> crearHoroscopo(@RequestParam TipoHoroscopo tipoHoroscopo, @RequestParam String contenido,@RequestParam TipoPublicacion tipoPublicacion,@RequestParam String usuarioEditor) {
		HoroscopoDTO nuevo = new HoroscopoDTO(tipoHoroscopo, contenido, tipoPublicacion, usuarioEditor);
		int status = hService.create(nuevo);

		switch (status) {

		case 0:
			return new ResponseEntity<String>("Horóscopo creado correctamente", HttpStatus.CREATED);

		case 1:
			return new ResponseEntity<String>("El contenido del horóscopo está vacío", HttpStatus.BAD_REQUEST);

		case 2:
			return new ResponseEntity<String>("El tipo de publicación es obligatorio", HttpStatus.BAD_REQUEST);

		case 3:
			return new ResponseEntity<String>("El usuario editor no existe en sesión", HttpStatus.UNAUTHORIZED);

		default:
			return new ResponseEntity<String>("Error al crear el horóscopo", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/mostrartodo")
	public ResponseEntity<List<HoroscopoDTO>> obtenerTodo(){
		List<HoroscopoDTO> horoscopolist = hService.getAll();
		if(horoscopolist.isEmpty()){
			return new ResponseEntity<List<HoroscopoDTO>>(horoscopolist, HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<List<HoroscopoDTO>>(horoscopolist, HttpStatus.ACCEPTED);
		}
	}

	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarHoroscopo(@RequestParam Long id,@RequestParam TipoHoroscopo tipoHoroscopo,  @RequestParam String contenido,@RequestParam TipoPublicacion tipoPublicacion,@RequestParam String usuarioEditor) {
		HoroscopoDTO actualizar = new HoroscopoDTO(tipoHoroscopo, contenido, tipoPublicacion, usuarioEditor);
		int status = hService.updateById(id, actualizar);

		switch (status) {

		case 0:
			return new ResponseEntity<String>("Actualizado satisfactoriamente", HttpStatus.ACCEPTED);

		case 1:
			return new ResponseEntity<String>("El contenido está vacío", HttpStatus.BAD_REQUEST);

		case 2:
			return new ResponseEntity<String>("El tipo de publicación es obligatorio", HttpStatus.BAD_REQUEST);

		case 3:
			return new ResponseEntity<String>("El usuario editor no existe en sesión", HttpStatus.UNAUTHORIZED);

		case 4:
			return new ResponseEntity<String>("El horóscopo no existe", HttpStatus.NOT_FOUND);

		case 5:
			return new ResponseEntity<String>("No tienes permisos para modificar este horóscopo", HttpStatus.FORBIDDEN);

		default:
			return new ResponseEntity<String>("Dato ingresado no válido", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarHoroscopo(@RequestParam Long id){
		int status = hService.deleteById(id);
		switch (status) {

        case 0:
            return new ResponseEntity<String>("Horóscopo eliminado correctamente", HttpStatus.OK);

        case 1:
            return new ResponseEntity<String>("El horóscopo no existe", HttpStatus.NOT_FOUND);

        case 2:
            return new ResponseEntity<String>("Usuario editor no encontrado en sesión", HttpStatus.UNAUTHORIZED);

        case 3:
            return new ResponseEntity<String>("No tienes permisos para eliminar este horóscopo", HttpStatus.FORBIDDEN);

        default:
            return new ResponseEntity<String>("Error al eliminar el horóscopo", HttpStatus.BAD_REQUEST);
		}
	}


}
