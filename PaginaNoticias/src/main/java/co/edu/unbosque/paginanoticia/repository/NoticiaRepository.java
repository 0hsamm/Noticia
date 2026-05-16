package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.Noticia;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

	List<Noticia> findByUsuarioComentarista_Id(Long usuarioComentaristaId);
	
	Optional<Noticia> findByTitulo(String titulo);
}
