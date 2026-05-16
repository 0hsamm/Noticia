package co.edu.unbosque.paginanoticia.service;

import co.edu.unbosque.paginanoticia.dto.NoticiaDTO;
import co.edu.unbosque.paginanoticia.entity.Noticia;
import co.edu.unbosque.paginanoticia.entity.UsuarioComentarista;
import co.edu.unbosque.paginanoticia.repository.NoticiaRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioComentaristaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class NoticiaService implements CRUDOperation<NoticiaDTO> {

	@Autowired
	private NoticiaRepository noticiaRepo;
	
	@Autowired
	private UsuarioComentaristaRepository usuarioComentaristaRepo;
	
	@Autowired
	private ModelMapper mapper;



	@Override
	public int create(NoticiaDTO data) {

	    if (data.getContenido() == null || data.getContenido().trim().isEmpty()) {

	        return 1;
	    }

	    if (data.getTipoPublicacion() == null) {

	        return 2;
	    }

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    String username = auth.getName();

	    Optional<UsuarioComentarista> usuarioOpt = usuarioComentaristaRepo.findByNombre(username);

	    if (usuarioOpt.isEmpty()) {

	        return 3;
	    }

	    Noticia noticia = mapper.map(data, Noticia.class);
	    noticia.setUsuarioComentarista(usuarioOpt.get());

	    noticiaRepo.save(noticia);

	    return 0;
	}

	@Override
	public List<NoticiaDTO> getAll() {
		List<NoticiaDTO> dtoList = new ArrayList<>();
		noticiaRepo.findAll().forEach(comentario -> dtoList.add(toDto(comentario)));
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {

	    Optional<Noticia> noticiaOpt = noticiaRepo.findById(id);

	    if (noticiaOpt.isEmpty()) {

	        return 1;
	    }

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    String username = auth.getName();

	    Optional<UsuarioComentarista> usuarioOpt = usuarioComentaristaRepo.findByNombre(username);

	    if (usuarioOpt.isEmpty()) {

	        return 2;
	    }

	    Noticia noticia = noticiaOpt.get();

	    if (noticia.getUsuarioComentarista().getId()
	            != usuarioOpt.get().getId()) {

	        return 3;
	    }

	    noticiaRepo.delete(noticia);

	    return 0;
	}

	@Override
	public int updateById(Long id, NoticiaDTO data) {

	    Optional<Noticia> noticiaOpt = noticiaRepo.findById(id);

	    if (noticiaOpt.isEmpty()) {

	        return 4;
	    }

	    if (data.getContenido() == null
	            || data.getContenido().trim().isEmpty()) {

	        return 1;
	    }

	    if (data.getTipoPublicacion() == null) {

	        return 2;
	    }

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    String username = auth.getName();

	    Optional<UsuarioComentarista> usuarioOpt =
	            usuarioComentaristaRepo.findByNombre(username);

	    if (usuarioOpt.isEmpty()) {

	        return 3;
	    }

	    Noticia noticia = noticiaOpt.get();

	    if (noticia.getUsuarioComentarista().getId()
	            != usuarioOpt.get().getId()) {

	        return 5;
	    }

	    noticia.setContenido(data.getContenido());

	    noticia.setTipoPublicacion(
	            data.getTipoPublicacion());

	    noticia.setUsuarioComentarista(
	            usuarioOpt.get());

	    noticiaRepo.save(noticia);

	    return 0;
	}

	@Override
	public long count() {
		return noticiaRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return noticiaRepo.existsById(id);
	}

	public List<NoticiaDTO> getByUsuarioComentarista(Long usuarioComentaristaId) {
		if (!usuarioComentaristaRepo.existsById(usuarioComentaristaId)) {
			return null;
		}

		List<NoticiaDTO> dtoList = new ArrayList<>();
		noticiaRepo.findByUsuarioComentarista_Id(usuarioComentaristaId)
				.forEach(comentario -> dtoList.add(toDto(comentario)));
		return dtoList;
	}

	private NoticiaDTO toDto(Noticia comentario) {
		NoticiaDTO dto = mapper.map(comentario, NoticiaDTO.class);
		if (comentario.getUsuarioComentarista() != null) {
			dto.setUsuarioComentarista(comentario.getUsuarioComentarista().getNombre());
		}
		return dto;
	}
}
