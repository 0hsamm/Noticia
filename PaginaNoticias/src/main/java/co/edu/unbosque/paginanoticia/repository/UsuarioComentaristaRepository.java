package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.UsuarioComentarista;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioComentaristaRepository extends JpaRepository<UsuarioComentarista, Long> {
	
	Optional<UsuarioComentarista> findByNombre(String nombre);
}
