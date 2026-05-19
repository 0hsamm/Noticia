package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.UsuarioAdministrador;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio encargado de la gestión de datos de la entidad UsuarioAdministrador.
 * Permite realizar operaciones de consulta y búsqueda de administradores.
 */
public interface UsuarioAdministradorRepository extends JpaRepository<UsuarioAdministrador, Long> {

	/**
	 * Busca un usuario administrador por su nombre de usuario.
	 *
	 * @param nombre nombre del administrador
	 */
	Optional<UsuarioAdministrador> findByNombre(String nombre);
}