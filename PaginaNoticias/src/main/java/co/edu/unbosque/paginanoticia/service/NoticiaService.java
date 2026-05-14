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
import org.springframework.stereotype.Service;

@Service
public class NoticiaService implements CRUDOperation<NoticiaDTO> {

	private final NoticiaRepository comentarioRepo;
	private final UsuarioComentaristaRepository usuarioComentaristaRepo;
	private final ModelMapper mapper;

	public NoticiaService(
			NoticiaRepository comentarioRepo,
			UsuarioComentaristaRepository usuarioComentaristaRepo,
			ModelMapper mapper) {
		this.comentarioRepo = comentarioRepo;
		this.usuarioComentaristaRepo = usuarioComentaristaRepo;
		this.mapper = mapper;
	}

	@Override
	public int create(NoticiaDTO data) {
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

		Noticia comentario = mapper.map(data, Noticia.class);
		comentario.setUsuarioComentarista(usuarioOpt.get());
		comentarioRepo.save(comentario);
		return 0;
	}

	@Override
	public List<NoticiaDTO> getAll() {
		List<NoticiaDTO> dtoList = new ArrayList<>();
		comentarioRepo.findAll().forEach(comentario -> dtoList.add(toDto(comentario)));
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<Noticia> comentarioOpt = comentarioRepo.findById(id);
		if (comentarioOpt.isEmpty()) {
			return 1;
		}

		comentarioRepo.delete(comentarioOpt.get());
		return 0;
	}

	@Override
	public int updateById(Long id, NoticiaDTO data) {
		Optional<Noticia> comentarioOpt = comentarioRepo.findById(id);
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

		Noticia comentario = comentarioOpt.get();
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

	public List<NoticiaDTO> getByUsuarioComentarista(Long usuarioComentaristaId) {
		if (!usuarioComentaristaRepo.existsById(usuarioComentaristaId)) {
			return null;
		}

		List<NoticiaDTO> dtoList = new ArrayList<>();
		comentarioRepo.findByUsuarioComentarista_Id(usuarioComentaristaId)
				.forEach(comentario -> dtoList.add(toDto(comentario)));
		return dtoList;
	}

	private NoticiaDTO toDto(Noticia comentario) {
		NoticiaDTO dto = mapper.map(comentario, NoticiaDTO.class);
		if (comentario.getUsuarioComentarista() != null) {
			dto.setUsuarioComentaristaId(comentario.getUsuarioComentarista().getId());
		}
		return dto;
	}
}
