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

/**
 * Controlador encargado de gestionar las operaciones relacionadas con horóscopos.
 * Permite crear, listar, actualizar y eliminar registros de horóscopos.
 */
@RestController
@RequestMapping("/horoscopo")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class HoroscopoController {

	@Autowired
	private HoroscopoService hService;

	/**
	 * Crea un nuevo horóscopo en el sistema.
	 *
	 * @param tipoHoroscopo tipo de horóscopo
	 * @param contenido contenido del horóscopo
	 * @param tipoPublicacion tipo de publicación asociada
	 * @param usuarioEditor usuario editor responsable
	 * @return respuesta con el estado de la operación
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crearHoroscopo(@RequestParam TipoHoroscopo tipoHoroscopo,
			@RequestParam String contenido,
			@RequestParam TipoPublicacion tipoPublicacion,
			@RequestParam String usuarioEditor) {

		HoroscopoDTO nuevo = new HoroscopoDTO(tipoHoroscopo, contenido, tipoPublicacion, usuarioEditor);

		int status = hService.create(nuevo);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Horoscopo creado correctamente", HttpStatus.CREATED);
			case 1:
				return new ResponseEntity<String>("El contenido del horoscopo esta vacio", HttpStatus.BAD_REQUEST);
			case 2:
				return new ResponseEntity<String>("El tipo de publicacion es obligatorio", HttpStatus.BAD_REQUEST);
			case 3:
				return new ResponseEntity<String>("El usuario editor no existe en sesion", HttpStatus.UNAUTHORIZED);
			default:
				return new ResponseEntity<String>("Error al crear el horoscopo", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Obtiene todos los horóscopos registrados.
	 *
	 * @return lista de horóscopos o estado sin contenido si está vacía
	 */
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<HoroscopoDTO>> obtenerTodo() {

		List<HoroscopoDTO> horoscopos = hService.getAll();

		if (horoscopos.isEmpty()) {
			return new ResponseEntity<List<HoroscopoDTO>>(horoscopos, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<HoroscopoDTO>>(horoscopos, HttpStatus.ACCEPTED);
	}

	/**
	 * Actualiza un horóscopo existente.
	 *
	 * @param id identificador del horóscopo
	 * @param tipoHoroscopo tipo de horóscopo
	 * @param contenido contenido del horóscopo
	 * @param tipoPublicacion tipo de publicación asociada
	 * @param usuarioEditor usuario editor responsable
	 * @return respuesta con el estado de la actualización
	 */
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarHoroscopo(@RequestParam Long id,
			@RequestParam TipoHoroscopo tipoHoroscopo,
			@RequestParam String contenido,
			@RequestParam TipoPublicacion tipoPublicacion,
			@RequestParam String usuarioEditor) {

		HoroscopoDTO actualizar = new HoroscopoDTO(tipoHoroscopo, contenido, tipoPublicacion, usuarioEditor);

		int status = hService.updateById(id, actualizar);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Horoscopo actualizado correctamente", HttpStatus.ACCEPTED);
			case 1:
				return new ResponseEntity<String>("El contenido esta vacio", HttpStatus.BAD_REQUEST);
			case 2:
				return new ResponseEntity<String>("El tipo de publicacion es obligatorio", HttpStatus.BAD_REQUEST);
			case 3:
				return new ResponseEntity<String>("El usuario editor no existe en sesion", HttpStatus.UNAUTHORIZED);
			case 4:
				return new ResponseEntity<String>("El horoscopo no existe", HttpStatus.NOT_FOUND);
			case 5:
				return new ResponseEntity<String>("No tienes permisos para modificar este horoscopo", HttpStatus.FORBIDDEN);
			default:
				return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Elimina un horóscopo por su identificador.
	 *
	 * @param id identificador del horóscopo
	 * @return respuesta con el estado de la eliminación
	 */
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarHoroscopo(@RequestParam Long id) {

		int status = hService.deleteById(id);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Horoscopo eliminado correctamente", HttpStatus.OK);
			case 1:
				return new ResponseEntity<String>("El horoscopo no existe", HttpStatus.NOT_FOUND);
			case 2:
				return new ResponseEntity<String>("Usuario editor no encontrado en sesion", HttpStatus.UNAUTHORIZED);
			case 3:
				return new ResponseEntity<String>("No tienes permisos para eliminar este horoscopo", HttpStatus.FORBIDDEN);
			default:
				return new ResponseEntity<String>("Error al eliminar el horoscopo", HttpStatus.BAD_REQUEST);
		}
	}
}