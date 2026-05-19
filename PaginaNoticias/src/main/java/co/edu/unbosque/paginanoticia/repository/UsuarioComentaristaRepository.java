package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.UsuarioComentarista;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio encargado de la gestión de datos de la entidad UsuarioComentarista.
 * Permite realizar operaciones de consulta y búsqueda de usuarios comentaristas.
 */
public interface UsuarioComentaristaRepository extends JpaRepository<UsuarioComentarista, Long> {

	/**
	 * Busca un usuario comentarista por su nombre de usuario.
	 *
	 * @param nombre nombre del comentarista
	 */
	Optional<UsuarioComentarista> findByNombre(String nombre);
}