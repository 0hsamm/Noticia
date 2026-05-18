package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.UsuarioAdministrador;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioAdministradorRepository extends JpaRepository<UsuarioAdministrador, Long> {
	
	Optional<UsuarioAdministrador> findByNombre(String nombre);
}
