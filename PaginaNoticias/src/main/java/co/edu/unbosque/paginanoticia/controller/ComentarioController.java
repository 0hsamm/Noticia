package co.edu.unbosque.paginanoticia.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.paginanoticia.dto.ComentarioDTO;
import co.edu.unbosque.paginanoticia.enums.TipoHoroscopo;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import co.edu.unbosque.paginanoticia.service.ComentarioService;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con comentarios.
 * Permite crear, listar, actualizar y eliminar comentarios, además de consultar
 * comentarios por noticia o por horóscopo.
 */
@RestController
@RequestMapping("/comentario")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ComentarioController {

	@Autowired
	private ComentarioService comentarioService;

	/**
	 * Crea un nuevo comentario asociado a una noticia o un horóscopo.
	 *
	 * @param contenido contenido del comentario
	 * @param fecha fecha de creación del comentario
	 * @param nombreComentarista nombre del usuario que comenta
	 * @param tituloNoticia título de la noticia (opcional)
	 * @param signoHoroscopo signo del horóscopo (opcional)
	 * @param horoscopoId id del horóscopo (opcional)
	 * @param tipoPublicacion tipo de publicación asociada
	 * @return respuesta con el estado de la operación
	 */
	@PostMapping("/create")
	public ResponseEntity<String> crearComentario(@RequestParam String contenido, @RequestParam LocalDateTime fecha,
			@RequestParam String nombreComentarista, @RequestParam(required = false) String tituloNoticia,
			@RequestParam(required = false) TipoHoroscopo signoHoroscopo,
			@RequestParam(required = false) Long horoscopoId,
			@RequestParam TipoPublicacion tipoPublicacion) {

		ComentarioDTO nuevo = new ComentarioDTO(contenido, fecha, nombreComentarista, tituloNoticia,
				signoHoroscopo, tipoPublicacion);

		nuevo.setHoroscopoId(horoscopoId);

		int status = comentarioService.create(nuevo);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Comentario creado correctamente", HttpStatus.CREATED);
			case 1:
				return new ResponseEntity<String>("El contenido del comentario esta vacio", HttpStatus.BAD_REQUEST);
			case 2:
				return new ResponseEntity<String>("El comentarista no existe", HttpStatus.NOT_FOUND);
			case 3:
				return new ResponseEntity<String>("La noticia no existe", HttpStatus.NOT_FOUND);
			case 4:
				return new ResponseEntity<String>("El horoscopo no existe", HttpStatus.NOT_FOUND);
			default:
				return new ResponseEntity<String>("Dato ingresado no valido", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Obtiene todos los comentarios registrados.
	 *
	 * @return lista de comentarios o estado sin contenido si está vacía
	 */
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<ComentarioDTO>> obtenerTodo() {

		List<ComentarioDTO> comentarios = comentarioService.getAll();

		if (comentarios.isEmpty()) {
			return new ResponseEntity<List<ComentarioDTO>>(comentarios, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<ComentarioDTO>>(comentarios, HttpStatus.ACCEPTED);
	}

	/**
	 * Elimina un comentario por su identificador.
	 *
	 * @param id identificador del comentario
	 * @return respuesta con el estado de la operación
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {

		int status = comentarioService.deleteById(id);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Comentario eliminado correctamente", HttpStatus.OK);
			case 1:
				return new ResponseEntity<String>("El comentario no existe", HttpStatus.NOT_FOUND);
			case 2:
				return new ResponseEntity<String>("Usuario no encontrado en sesion", HttpStatus.UNAUTHORIZED);
			case 3:
				return new ResponseEntity<String>("No tienes permisos para eliminar este comentario", HttpStatus.FORBIDDEN);
			default:
				return new ResponseEntity<String>("Error al eliminar el comentario", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Actualiza un comentario existente.
	 *
	 * @param id identificador del comentario
	 * @param contenido nuevo contenido del comentario
	 * @param fecha nueva fecha del comentario
	 * @param nombreComentarista nombre del comentarista
	 * @param tituloNoticia título de la noticia (opcional)
	 * @param signoHoroscopo signo del horóscopo (opcional)
	 * @param horoscopoId id del horóscopo (opcional)
	 * @param tipoPublicacion tipo de publicación asociada
	 * @return respuesta con el estado de la actualización
	 */
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarComentario(@RequestParam Long id, @RequestParam String contenido,
			@RequestParam LocalDateTime fecha, @RequestParam String nombreComentarista,
			@RequestParam(required = false) String tituloNoticia,
			@RequestParam(required = false) TipoHoroscopo signoHoroscopo,
			@RequestParam(required = false) Long horoscopoId,
			@RequestParam TipoPublicacion tipoPublicacion) {

		ComentarioDTO actualizar = new ComentarioDTO(contenido, fecha, nombreComentarista, tituloNoticia,
				signoHoroscopo, tipoPublicacion);

		actualizar.setHoroscopoId(horoscopoId);

		int status = comentarioService.updateById(id, actualizar);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Comentario actualizado correctamente", HttpStatus.OK);
			case 1:
				return new ResponseEntity<String>("El contenido del comentario esta vacio", HttpStatus.BAD_REQUEST);
			case 2:
				return new ResponseEntity<String>("Usuario no encontrado en sesion", HttpStatus.UNAUTHORIZED);
			case 3:
				return new ResponseEntity<String>("La noticia no existe", HttpStatus.NOT_FOUND);
			case 4:
				return new ResponseEntity<String>("El horoscopo no existe", HttpStatus.NOT_FOUND);
			case 5:
				return new ResponseEntity<String>("El comentario no existe", HttpStatus.NOT_FOUND);
			case 6:
				return new ResponseEntity<String>("No tienes permisos para modificar este comentario", HttpStatus.FORBIDDEN);
			default:
				return new ResponseEntity<String>("Error al actualizar el comentario", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Obtiene los comentarios asociados a una noticia específica.
	 *
	 * @param id identificador de la noticia
	 * @return lista de comentarios relacionados
	 */
	@GetMapping("/noticia/{id}")
	public ResponseEntity<List<ComentarioDTO>> getByNoticia(@PathVariable Long id) {

		List<ComentarioDTO> lista = comentarioService.getByNoticia(id);

		if (lista.isEmpty()) {
			return new ResponseEntity<List<ComentarioDTO>>(lista, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<ComentarioDTO>>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene los comentarios asociados a un horóscopo específico.
	 *
	 * @param id identificador del horóscopo
	 * @return lista de comentarios relacionados
	 */
	@GetMapping("/horoscopo/{id}")
	public ResponseEntity<List<ComentarioDTO>> getByHoroscopo(@PathVariable Long id) {

		List<ComentarioDTO> lista = comentarioService.getByHoroscopo(id);

		if (lista.isEmpty()) {
			return new ResponseEntity<List<ComentarioDTO>>(lista, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<ComentarioDTO>>(lista, HttpStatus.OK);
	}
}