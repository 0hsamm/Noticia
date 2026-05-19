package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.UsuarioEditor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio encargado de la gestión de datos de la entidad UsuarioEditor.
 * Permite realizar operaciones de consulta y búsqueda de usuarios editores.
 */
public interface UsuarioEditorRepository extends JpaRepository<UsuarioEditor, Long> {

	/**
	 * Busca un usuario editor por su nombre de usuario.
	 *
	 * @param nombre nombre del editor
	 */
	Optional<UsuarioEditor> findByNombre(String nombre);
}