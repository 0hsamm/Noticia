package co.edu.unbosque.paginanoticia.service;

import co.edu.unbosque.paginanoticia.dto.UsuarioEditorDTO;
import co.edu.unbosque.paginanoticia.entity.UsuarioEditor;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.repository.UsuarioAdministradorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioComentaristaRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioEditorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioNormalRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioEditorService implements CRUDOperation<UsuarioEditorDTO> {
	
	@Autowired
	private UsuarioEditorRepository usuarioEditorRepo;

	@Autowired
	private UsuarioAdministradorRepository usuarioAdministradorRepo;

	@Autowired
	private UsuarioComentaristaRepository usuarioComentaristaRepo;

	@Autowired
	private UsuarioNormalRepository usuarioNormalRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public int create(UsuarioEditorDTO data) {
		if (data.getNombre() == null || data.getNombre().trim().isEmpty()) {
			return 1;
		}
		if (data.getContrasena() == null || data.getContrasena().length() < 8) {
			return 2;
		}
		if (findNombreAlreadyTaken(data.getNombre())) {
			return 3;
		}

		UsuarioEditor usuarioEditor = mapper.map(data, UsuarioEditor.class);
		usuarioEditor.setContrasena(passwordEncoder.encode(data.getContrasena()));
		usuarioEditor.setTipoUsuario(TipoUsuario.EDITOR);
		usuarioEditorRepo.save(usuarioEditor);
		return 0;
	}

	@Override
	public List<UsuarioEditorDTO> getAll() {
		List<UsuarioEditor> entityList = usuarioEditorRepo.findAll();
		List<UsuarioEditorDTO> dtoList = new ArrayList<>();
		entityList.forEach(entidad -> dtoList.add(toDto(entidad)));
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<UsuarioEditor> encontrado = usuarioEditorRepo.findById(id);
		if (encontrado.isPresent()) {
			usuarioEditorRepo.delete(encontrado.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, UsuarioEditorDTO data) {
		Optional<UsuarioEditor> encontrado = usuarioEditorRepo.findById(id);
		if (encontrado.isEmpty()) {
			return 4;
		}
		if (data.getNombre() == null || data.getNombre().trim().isEmpty()) {
			return 1;
		}
		if (data.getContrasena() == null || data.getContrasena().length() < 8) {
			return 2;
		}
		if (!encontrado.get().getNombre().equals(data.getNombre()) && findNombreAlreadyTaken(data.getNombre())) {
			return 3;
		}

		UsuarioEditor temp = encontrado.get();
		temp.setNombre(data.getNombre());
		temp.setContrasena(passwordEncoder.encode(data.getContrasena()));
		temp.setTipoUsuario(TipoUsuario.EDITOR);
		usuarioEditorRepo.save(temp);
		return 0;
	}

	@Override
	public long count() {
		return usuarioEditorRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return usuarioEditorRepo.existsById(id);
	}

	private boolean findNombreAlreadyTaken(String nombre) {
		return usuarioEditorRepo.findByNombre(nombre).isPresent()
				|| usuarioAdministradorRepo.findByNombre(nombre).isPresent()
				|| usuarioComentaristaRepo.findByNombre(nombre).isPresent()
				|| usuarioNormalRepo.findByNombre(nombre).isPresent();
	}

	private UsuarioEditorDTO toDto(UsuarioEditor entity) {
		UsuarioEditorDTO dto = mapper.map(entity, UsuarioEditorDTO.class);
		dto.setContrasena(null);
		return dto;
	}
}
