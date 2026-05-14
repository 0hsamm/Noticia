package co.edu.unbosque.paginanoticia.service;

import co.edu.unbosque.paginanoticia.dto.UsuarioNormalDTO;
import co.edu.unbosque.paginanoticia.entity.UsuarioNormal;
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
public class UsuarioNormalService implements CRUDOperation<UsuarioNormalDTO> {

	@Autowired
	private UsuarioNormalRepository usuarioNormalRepo;

	@Autowired
	private UsuarioAdministradorRepository usuarioAdministradorRepo;

	@Autowired
	private UsuarioEditorRepository usuarioEditorRepo;

	@Autowired
	private UsuarioComentaristaRepository usuarioComentaristaRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public int create(UsuarioNormalDTO data) {
		if (data.getNombre() == null || data.getNombre().trim().isEmpty()) {
			return 1;
		}
		if (data.getContrasena() == null || data.getContrasena().length() < 8) {
			return 2;
		}
		if (findNombreAlreadyTaken(data.getNombre())) {
			return 3;
		}

		UsuarioNormal usuarioNormal = mapper.map(data, UsuarioNormal.class);
		usuarioNormal.setContrasena(passwordEncoder.encode(data.getContrasena()));
		usuarioNormal.setTarifa(data.getTarifa() != null ? data.getTarifa() : 0.0);
		usuarioNormal.setTipoUsuario(TipoUsuario.USUARIO);
		usuarioNormalRepo.save(usuarioNormal);
		return 0;
	}

	@Override
	public List<UsuarioNormalDTO> getAll() {
		List<UsuarioNormal> entityList = usuarioNormalRepo.findAll();
		List<UsuarioNormalDTO> dtoList = new ArrayList<>();
		entityList.forEach(entidad -> dtoList.add(toDto(entidad)));
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<UsuarioNormal> encontrado = usuarioNormalRepo.findById(id);
		if (encontrado.isPresent()) {
			usuarioNormalRepo.delete(encontrado.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, UsuarioNormalDTO data) {
		Optional<UsuarioNormal> encontrado = usuarioNormalRepo.findById(id);
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

		UsuarioNormal temp = encontrado.get();
		temp.setNombre(data.getNombre());
		temp.setContrasena(passwordEncoder.encode(data.getContrasena()));
		temp.setTarifa(data.getTarifa() != null ? data.getTarifa() : 0.0);
		temp.setTipoUsuario(TipoUsuario.USUARIO);
		usuarioNormalRepo.save(temp);
		return 0;
	}

	@Override
	public long count() {
		return usuarioNormalRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return usuarioNormalRepo.existsById(id);
	}

	private boolean findNombreAlreadyTaken(String nombre) {
		return usuarioNormalRepo.findByNombre(nombre).isPresent()
				|| usuarioAdministradorRepo.findByNombre(nombre).isPresent()
				|| usuarioEditorRepo.findByNombre(nombre).isPresent()
				|| usuarioComentaristaRepo.findByNombre(nombre).isPresent();
	}

	private UsuarioNormalDTO toDto(UsuarioNormal entity) {
		UsuarioNormalDTO dto = mapper.map(entity, UsuarioNormalDTO.class);
		dto.setContrasena(null);
		return dto;
	}
}
