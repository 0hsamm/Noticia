package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.Horoscopo;
import co.edu.unbosque.paginanoticia.enums.TipoHoroscopo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HoroscopoRepository extends JpaRepository<Horoscopo, Long> {

	List<Horoscopo> findByUsuarioEditor_Id(Long usuarioEditorId);
	
	Optional<Horoscopo> findFirstByTipoHoroscopoOrderByIdDesc(TipoHoroscopo tipoHoroscopo);
}
