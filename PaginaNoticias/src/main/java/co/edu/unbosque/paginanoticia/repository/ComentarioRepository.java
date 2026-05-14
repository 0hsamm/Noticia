package co.edu.unbosque.paginanoticia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.paginanoticia.entity.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

	
	List<Comentario> findByNoticia_Id(Long id);

    List<Comentario> findByHoroscopo_Id(Long id);

    List<Comentario> findByComentarista_Id(Long id);
	
    void deleteByNoticiaId(Long noticiaId);

    void deleteByHoroscopoId(Long horoscopoId);
    
}
