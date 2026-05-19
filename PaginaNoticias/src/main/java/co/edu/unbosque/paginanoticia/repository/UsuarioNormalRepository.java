package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.UsuarioNormal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio encargado de la gestión de datos de la entidad UsuarioNormal.
 * Permite realizar operaciones de consulta y búsqueda de usuarios normales.
 */
public interface UsuarioNormalRepository extends JpaRepository<UsuarioNormal, Long> {

	/**
	 * Busca un usuario normal por su nombre de usuario.
	 *
	 * @param nombre nombre del usuario
	 */
	Optional<UsuarioNormal> findByNombre(String nombre);
}