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

/**
 * Controlador encargado de gestionar las operaciones relacionadas con noticias.
 * Permite crear, listar, actualizar y eliminar noticias dentro del sistema.
 */
@RestController
@RequestMapping("/noticia")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class NoticiaController {

	@Autowired
	private NoticiaService nService;

	/**
	 * Crea una nueva noticia en el sistema.
	 *
	 * @param titulo título de la noticia
	 * @param contenido contenido de la noticia
	 * @param tipoPublicacion tipo de publicación asociada
	 * @param usuarioComentarista usuario responsable de la noticia
	 * @return respuesta con el estado de la operación
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crearNoticia(@RequestParam String titulo,
			@RequestParam String contenido,
			@RequestParam TipoPublicacion tipoPublicacion,
			@RequestParam String usuarioComentarista) {

		NoticiaDTO nuevo = new NoticiaDTO(titulo, contenido, tipoPublicacion, usuarioComentarista);

		int status = nService.create(nuevo);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Noticia creada correctamente", HttpStatus.CREATED);
			case 1:
				return new ResponseEntity<String>("El contenido de la noticia esta vacio", HttpStatus.BAD_REQUEST);
			case 2:
				return new ResponseEntity<String>("El tipo de publicacion es obligatorio", HttpStatus.BAD_REQUEST);
			case 3:
				return new ResponseEntity<String>("El usuario editor no existe en sesion", HttpStatus.UNAUTHORIZED);
			default:
				return new ResponseEntity<String>("Error al crear la noticia", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Obtiene todas las noticias registradas.
	 *
	 * @return lista de noticias o estado sin contenido si está vacía
	 */
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<NoticiaDTO>> obtenerTodo() {

		List<NoticiaDTO> noticias = nService.getAll();

		if (noticias.isEmpty()) {
			return new ResponseEntity<List<NoticiaDTO>>(noticias, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<NoticiaDTO>>(noticias, HttpStatus.ACCEPTED);
	}

	/**
	 * Actualiza una noticia existente.
	 *
	 * @param id identificador de la noticia
	 * @param titulo nuevo título
	 * @param contenido nuevo contenido
	 * @param tipoPublicacion tipo de publicación asociada
	 * @param usuarioComentarista usuario responsable de la actualización
	 * @return respuesta con el estado de la actualización
	 */
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarNoticia(@RequestParam Long id,
			@RequestParam String titulo,
			@RequestParam String contenido,
			@RequestParam TipoPublicacion tipoPublicacion,
			@RequestParam String usuarioComentarista) {

		NoticiaDTO actualizar = new NoticiaDTO(titulo, contenido, tipoPublicacion, usuarioComentarista);

		int status = nService.updateById(id, actualizar);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Noticia actualizada correctamente", HttpStatus.ACCEPTED);
			case 1:
				return new ResponseEntity<String>("El contenido de la noticia esta vacio", HttpStatus.BAD_REQUEST);
			case 2:
				return new ResponseEntity<String>("El tipo de publicacion es obligatorio", HttpStatus.BAD_REQUEST);
			case 3:
				return new ResponseEntity<String>("Usuario editor no encontrado en sesion", HttpStatus.UNAUTHORIZED);
			case 4:
				return new ResponseEntity<String>("La noticia no existe", HttpStatus.NOT_FOUND);
			case 5:
				return new ResponseEntity<String>("No tienes permisos para modificar esta noticia", HttpStatus.FORBIDDEN);
			default:
				return new ResponseEntity<String>("Error al actualizar la noticia", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Elimina una noticia por su identificador.
	 *
	 * @param id identificador de la noticia
	 * @return respuesta con el estado de la eliminación
	 */
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarNoticia(@RequestParam Long id) {

		int status = nService.deleteById(id);

		switch (status) {
			case 0:
				return new ResponseEntity<String>("Noticia eliminada correctamente", HttpStatus.OK);
			case 1:
				return new ResponseEntity<String>("La noticia no existe", HttpStatus.NOT_FOUND);
			case 2:
				return new ResponseEntity<String>("Usuario editor no encontrado en sesion", HttpStatus.UNAUTHORIZED);
			case 3:
				return new ResponseEntity<String>("No tienes permisos para eliminar esta noticia", HttpStatus.FORBIDDEN);
			default:
				return new ResponseEntity<String>("Error al eliminar la noticia", HttpStatus.BAD_REQUEST);
		}
	}
}