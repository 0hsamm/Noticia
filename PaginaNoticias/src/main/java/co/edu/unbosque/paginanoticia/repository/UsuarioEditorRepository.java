package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.UsuarioEditor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioEditorRepository extends JpaRepository<UsuarioEditor, Long> {
	
	Optional<UsuarioEditor> findByNombre(String nombre);
}
