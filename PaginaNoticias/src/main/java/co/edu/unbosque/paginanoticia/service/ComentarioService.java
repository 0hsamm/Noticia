package co.edu.unbosque.paginanoticia.service;

import co.edu.unbosque.paginanoticia.dto.ComentarioDTO;
import co.edu.unbosque.paginanoticia.entity.Comentario;
import co.edu.unbosque.paginanoticia.entity.UsuarioComentarista;
import co.edu.unbosque.paginanoticia.repository.ComentarioRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioComentaristaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService implements CRUDOperation<ComentarioDTO> {

	private final ComentarioRepository comentarioRepo;
	private final UsuarioComentaristaRepository usuarioComentaristaRepo;
	private final ModelMapper mapper;

	public ComentarioService(
			ComentarioRepository comentarioRepo,
			UsuarioComentaristaRepository usuarioComentaristaRepo,
			ModelMapper mapper) {
		this.comentarioRepo = comentarioRepo;
		this.usuarioComentaristaRepo = usuarioComentaristaRepo;
		this.mapper = mapper;
	}

	@Override
	public int create(ComentarioDTO data) {
		if (data.getContenido() == null || data.getContenido().trim().isEmpty()) {
			return 1;
		}
		if (data.getTipoPublicacion() == null) {
			return 2;
		}

		Optional<UsuarioComentarista> usuarioOpt =
				usuarioComentaristaRepo.findById(data.getUsuarioComentaristaId());
		if (usuarioOpt.isEmpty()) {
			return 3;
		}

		Comentario comentario = mapper.map(data, Comentario.class);
		comentario.setUsuarioComentarista(usuarioOpt.get());
		comentarioRepo.save(comentario);
		return 0;
	}

	@Override
	public List<ComentarioDTO> getAll() {
		List<ComentarioDTO> dtoList = new ArrayList<>();
		comentarioRepo.findAll().forEach(comentario -> dtoList.add(toDto(comentario)));
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<Comentario> comentarioOpt = comentarioRepo.findById(id);
		if (comentarioOpt.isEmpty()) {
			return 1;
		}

		comentarioRepo.delete(comentarioOpt.get());
		return 0;
	}

	@Override
	public int updateById(Long id, ComentarioDTO data) {
		Optional<Comentario> comentarioOpt = comentarioRepo.findById(id);
		if (comentarioOpt.isEmpty()) {
			return 4;
		}
		if (data.getContenido() == null || data.getContenido().trim().isEmpty()) {
			return 1;
		}
		if (data.getTipoPublicacion() == null) {
			return 2;
		}

		Optional<UsuarioComentarista> usuarioOpt =
				usuarioComentaristaRepo.findById(data.getUsuarioComentaristaId());
		if (usuarioOpt.isEmpty()) {
			return 3;
		}

		Comentario comentario = comentarioOpt.get();
		comentario.setContenido(data.getContenido());
		comentario.setTipoPublicacion(data.getTipoPublicacion());
		comentario.setUsuarioComentarista(usuarioOpt.get());
		comentarioRepo.save(comentario);
		return 0;
	}

	@Override
	public long count() {
		return comentarioRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return comentarioRepo.existsById(id);
	}

	public List<ComentarioDTO> getByUsuarioComentarista(Long usuarioComentaristaId) {
		if (!usuarioComentaristaRepo.existsById(usuarioComentaristaId)) {
			return null;
		}

		List<ComentarioDTO> dtoList = new ArrayList<>();
		comentarioRepo.findByUsuarioComentarista_Id(usuarioComentaristaId)
				.forEach(comentario -> dtoList.add(toDto(comentario)));
		return dtoList;
	}

	private ComentarioDTO toDto(Comentario comentario) {
		ComentarioDTO dto = mapper.map(comentario, ComentarioDTO.class);
		if (comentario.getUsuarioComentarista() != null) {
			dto.setUsuarioComentaristaId(comentario.getUsuarioComentarista().getId());
		}
		return dto;
	}
}
