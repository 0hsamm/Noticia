package co.edu.unbosque.paginanoticia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unbosque.paginanoticia.entity.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

	
	List<Comentario> findByNoticia_Id(Long id);

    List<Comentario> findByHoroscopo_Id(Long id);

    List<Comentario> findByComentarista_Id(Long id);
	
    void deleteByNoticiaId(Long noticiaId);

    void deleteByHoroscopoId(Long horoscopoId);
    
    @Modifying
    @Query("DELETE FROM Comentario c WHERE c.comentarista.id = :id")
    void deleteByComentaristaId(@Param("id") Long id);

    
}
