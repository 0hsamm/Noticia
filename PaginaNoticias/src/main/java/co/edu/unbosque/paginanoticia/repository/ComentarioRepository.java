package co.edu.unbosque.paginanoticia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unbosque.paginanoticia.entity.Comentario;

/**
 * Repositorio encargado de la gestión de datos de la entidad Comentario.
 * Permite realizar operaciones de consulta, eliminación y búsqueda
 * de comentarios relacionados con noticias, horóscopos y comentaristas.
 */
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

	/**
	 * Retorna la lista de comentarios asociados a una noticia específica.
	 *
	 * @param id identificador de la noticia
	 */
	List<Comentario> findByNoticia_Id(Long id);

	/**
	 * Retorna la lista de comentarios asociados a un horóscopo específico.
	 *
	 * @param id identificador del horóscopo
	 */
	List<Comentario> findByHoroscopo_Id(Long id);

	/**
	 * Retorna la lista de comentarios realizados por un comentarista específico.
	 *
	 * @param id identificador del comentarista
	 */
	List<Comentario> findByComentarista_Id(Long id);

	/**
	 * Elimina todos los comentarios asociados a una noticia específica.
	 *
	 * @param noticiaId identificador de la noticia
	 */
	void deleteByNoticiaId(Long noticiaId);

	/**
	 * Elimina todos los comentarios asociados a un horóscopo específico.
	 *
	 * @param horoscopoId identificador del horóscopo
	 */
	void deleteByHoroscopoId(Long horoscopoId);

	/**
	 * Elimina todos los comentarios realizados por un comentarista específico.
	 *
	 * @param id identificador del comentarista
	 */
	@Modifying
	@Query("DELETE FROM Comentario c WHERE c.comentarista.id = :id")
	void deleteByComentaristaId(@Param("id") Long id);
}