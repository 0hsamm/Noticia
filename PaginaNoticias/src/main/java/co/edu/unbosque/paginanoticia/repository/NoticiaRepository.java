package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.Noticia;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

	List<Noticia> findByUsuarioEditor_Id(Long usuarioEditorId);
	
	Optional<Noticia> findByTitulo(String titulo);
	
	@Modifying
    @Query("DELETE FROM Noticia n WHERE n.usuarioEditor.id = :id")
    void deleteByEditorId(@Param("id") Long id);
	
}
