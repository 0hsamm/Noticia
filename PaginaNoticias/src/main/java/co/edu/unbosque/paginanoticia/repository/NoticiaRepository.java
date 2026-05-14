package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.Noticia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

	List<Noticia> findByUsuarioComentarista_Id(Long usuarioComentaristaId);
}
