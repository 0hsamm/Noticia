package co.edu.unbosque.paginanoticia.service;

import co.edu.unbosque.paginanoticia.dto.HoroscopoDTO;
import co.edu.unbosque.paginanoticia.entity.Horoscopo;
import co.edu.unbosque.paginanoticia.entity.UsuarioEditor;
import co.edu.unbosque.paginanoticia.repository.HoroscopoRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioEditorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class HoroscopoService implements CRUDOperation<HoroscopoDTO> {

	@Autowired
	private HoroscopoRepository horoscopoRepo;

	@Autowired
	private UsuarioEditorRepository usuarioEditorRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public int create(HoroscopoDTO data) {

	    if (data.getContenido() == null || data.getContenido().trim().isEmpty()) {
	        return 1;
	    }

	    if (data.getTipoPublicacion() == null) {
	        return 2;
	    }

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    Optional<UsuarioEditor> usuarioEditorOpt = usuarioEditorRepo.findByNombre(username);

	    if (usuarioEditorOpt.isEmpty()) {

	        return 3;
	    }

	    Horoscopo horoscopo = mapper.map(data, Horoscopo.class);
	    horoscopo.setUsuarioEditor(usuarioEditorOpt.get());
	    horoscopoRepo.save(horoscopo);

	    return 0;
	}

	@Override
	public List<HoroscopoDTO> getAll() {
		List<HoroscopoDTO> dtoList = new ArrayList<>();
		horoscopoRepo.findAll().forEach(horoscopo -> dtoList.add(toDto(horoscopo)));
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {

	    // BUSCAR HOROSCOPO
	    Optional<Horoscopo> horoscopoOpt = horoscopoRepo.findById(id);

	    if (horoscopoOpt.isEmpty()) {

	        return 1;
	    }

	    // OBTENER USUARIO AUTENTICADO
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    String username = auth.getName();

	    // BUSCAR EDITOR
	    Optional<UsuarioEditor> usuarioEditorOpt = usuarioEditorRepo.findByNombre(username);

	    if (usuarioEditorOpt.isEmpty()) {

	        return 2;
	    }

	    Horoscopo horoscopo = horoscopoOpt.get();

	    // VALIDAR QUE EL HOROSCOPO
	    // PERTENEZCA AL EDITOR
	    if (horoscopo.getUsuarioEditor().getId()
	            != usuarioEditorOpt.get().getId()) {

	        return 3;
	    }

	    // ELIMINAR
	    horoscopoRepo.delete(horoscopo);

	    return 0;
	}

	@Override
	public int updateById(Long id, HoroscopoDTO data) {

	    // BUSCAR HOROSCOPO
	    Optional<Horoscopo> horoscopoOpt = horoscopoRepo.findById(id);

	    if (horoscopoOpt.isEmpty()) {

	        return 4;
	    }

	    // VALIDAR CONTENIDO
	    if (data.getContenido() == null
	            || data.getContenido().trim().isEmpty()) {

	        return 1;
	    }

	    // VALIDAR TIPO PUBLICACION
	    if (data.getTipoPublicacion() == null) {

	        return 2;
	    }

	    // OBTENER USUARIO AUTENTICADO
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    String username = auth.getName();

	    // BUSCAR EDITOR
	    Optional<UsuarioEditor> usuarioEditorOpt = usuarioEditorRepo.findByNombre(username);

	    if (usuarioEditorOpt.isEmpty()) {

	        return 3;
	    }

	    Horoscopo horoscopo = horoscopoOpt.get();

	    // VALIDAR QUE EL HOROSCOPO
	    // PERTENEZCA AL EDITOR
	    if (horoscopo.getUsuarioEditor().getId()
	            != usuarioEditorOpt.get().getId()) {

	        return 5;
	    }

	    // ACTUALIZAR DATOS
	    horoscopo.setContenido(data.getContenido());

	    horoscopo.setTipoPublicacion(
	            data.getTipoPublicacion());

	    // SETEAR EDITOR AUTOMATICAMENTE
	    horoscopo.setUsuarioEditor(
	            usuarioEditorOpt.get());

	    // GUARDAR
	    horoscopoRepo.save(horoscopo);

	    return 0;
	}

	@Override
	public long count() {
		return horoscopoRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return horoscopoRepo.existsById(id);
	}

	public List<HoroscopoDTO> getByUsuarioEditor(Long usuarioEditorId) {
		if (!usuarioEditorRepo.existsById(usuarioEditorId)) {
			return null;
		}

		List<HoroscopoDTO> dtoList = new ArrayList<>();
		horoscopoRepo.findByUsuarioEditor_Id(usuarioEditorId)
				.forEach(horoscopo -> dtoList.add(toDto(horoscopo)));
		return dtoList;
	}

	private HoroscopoDTO toDto(Horoscopo horoscopo) {
		HoroscopoDTO dto = mapper.map(horoscopo, HoroscopoDTO.class);
		if (horoscopo.getUsuarioEditor() != null) {
			dto.setUsuarioEditorId(horoscopo.getUsuarioEditor().getId());
		}
		return dto;
	}
}
