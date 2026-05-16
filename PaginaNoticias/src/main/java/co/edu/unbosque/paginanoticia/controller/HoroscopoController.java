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
	public ResponseEntity<String> crearHoroscopo(@RequestParam TipoHoroscopo tipoHoroscopo, @RequestParam String contenido,@RequestParam TipoPublicacion tipoPublicacion,@RequestParam long usuarioEditorId) {
		HoroscopoDTO nuevo = new HoroscopoDTO(tipoHoroscopo, contenido, tipoPublicacion, usuarioEditorId);
		int status = hService.create(nuevo);

		if (status == 0) {
			return new ResponseEntity<String>("Creado satisfactoriamente", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<String> actualizarHoroscopo(@RequestParam Long id,@RequestParam TipoHoroscopo tipoHoroscopo,  @RequestParam String contenido,@RequestParam TipoPublicacion tipoPublicacion,@RequestParam long usuarioEditorId) {
		HoroscopoDTO actualizar = new HoroscopoDTO(tipoHoroscopo, contenido, tipoPublicacion, usuarioEditorId);
		int status = hService.updateById(id, actualizar);

		if (status == 0) {
			return new ResponseEntity<String>("Actualizado satisfactoriamente", HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarHoroscopo(@RequestParam Long id){
		int status = hService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<String>("Eliminado satisfactoriamente", HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
