package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.UsuarioNormal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioNormalRepository extends JpaRepository<UsuarioNormal, Long> {
	
	Optional<UsuarioNormal> findByNombre(String nombre);
}
