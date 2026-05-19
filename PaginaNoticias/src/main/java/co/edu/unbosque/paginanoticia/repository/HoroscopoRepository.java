package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.Horoscopo;
import co.edu.unbosque.paginanoticia.enums.TipoHoroscopo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio encargado de la gestión de datos de la entidad Horoscopo.
 * Permite realizar consultas, búsquedas y eliminaciones relacionadas con horóscopos
 * y su relación con usuarios editores.
 */
public interface HoroscopoRepository extends JpaRepository<Horoscopo, Long> {

	/**
	 * Retorna la lista de horóscopos creados por un usuario editor específico.
	 *
	 * @param usuarioEditorId identificador del usuario editor
	 */
	List<Horoscopo> findByUsuarioEditor_Id(Long usuarioEditorId);

	/**
	 * Retorna el último horóscopo creado de un tipo específico.
	 *
	 * @param tipoHoroscopo tipo de horóscopo a buscar
	 */
	Optional<Horoscopo> findFirstByTipoHoroscopoOrderByIdDesc(TipoHoroscopo tipoHoroscopo);

	/**
	 * Elimina todos los horóscopos asociados a un usuario editor específico.
	 *
	 * @param id identificador del usuario editor
	 */
	@Modifying
	@Query("DELETE FROM Horoscopo h WHERE h.usuarioEditor.id = :id")
	void deleteByEditorId(@Param("id") Long id);
}