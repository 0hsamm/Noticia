package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.Horoscopo;
import co.edu.unbosque.paginanoticia.enums.TipoHoroscopo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HoroscopoRepository extends JpaRepository<Horoscopo, Long> {

	List<Horoscopo> findByUsuarioEditor_Id(Long usuarioEditorId);
	
	Optional<Horoscopo> findFirstByTipoHoroscopoOrderByIdDesc(TipoHoroscopo tipoHoroscopo);
	
	@Modifying
    @Query("DELETE FROM Horoscopo h WHERE h.usuarioEditor.id = :id")
    void deleteByEditorId(@Param("id") Long id);
	
}
