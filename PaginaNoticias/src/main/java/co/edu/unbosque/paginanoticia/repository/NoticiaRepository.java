package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.Noticia;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio encargado de la gestión de datos de la entidad Noticia.
 * Permite realizar consultas, búsquedas y eliminaciones de noticias
 * relacionadas con usuarios editores.
 */
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

	/**
	 * Retorna la lista de noticias creadas por un usuario editor específico.
	 *
	 * @param usuarioEditorId identificador del usuario editor
	 */
	List<Noticia> findByUsuarioEditor_Id(Long usuarioEditorId);

	/**
	 * Busca una noticia por su título.
	 *
	 * @param titulo título de la noticia
	 */
	Optional<Noticia> findByTitulo(String titulo);

	/**
	 * Elimina todas las noticias asociadas a un usuario editor específico.
	 *
	 * @param id identificador del usuario editor
	 */
	@Modifying
	@Query("DELETE FROM Noticia n WHERE n.usuarioEditor.id = :id")
	void deleteByEditorId(@Param("id") Long id);
}