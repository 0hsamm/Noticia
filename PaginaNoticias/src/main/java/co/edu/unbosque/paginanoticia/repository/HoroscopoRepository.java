package co.edu.unbosque.paginanoticia.repository;

import co.edu.unbosque.paginanoticia.entity.Horoscopo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoroscopoRepository extends JpaRepository<Horoscopo, Long> {

	List<Horoscopo> findByUsuarioEditor_Id(Long usuarioEditorId);
}
