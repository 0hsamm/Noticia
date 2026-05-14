package co.edu.unbosque.paginanoticia.service;

import co.edu.unbosque.paginanoticia.dto.UsuarioAdministradorDTO;
import co.edu.unbosque.paginanoticia.entity.UsuarioAdministrador;
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
public class UsuarioAdministradorService implements CRUDOperation<UsuarioAdministradorDTO> {

	@Autowired
	private UsuarioAdministradorRepository usuarioAdministradorRepo;

	@Autowired
	private UsuarioEditorRepository usuarioEditorRepo;

	@Autowired
	private UsuarioComentaristaRepository usuarioComentaristaRepo;

	@Autowired
	private UsuarioNormalRepository usuarioNormalRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public int create(UsuarioAdministradorDTO data) {
		if (data.getNombre() == null || data.getNombre().trim().isEmpty()) {
			return 1;
		}
		if (data.getContrasena() == null || data.getContrasena().length() < 8) {
			return 2;
		}
		if (findNombreAlreadyTaken(data.getNombre())) {
			return 3;
		}

		UsuarioAdministrador usuarioAdministrador = mapper.map(data, UsuarioAdministrador.class);
		usuarioAdministrador.setContrasena(passwordEncoder.encode(data.getContrasena()));
		usuarioAdministrador.setTipoUsuario(TipoUsuario.ADMIN);
		usuarioAdministradorRepo.save(usuarioAdministrador);
		return 0;
	}

	@Override
	public List<UsuarioAdministradorDTO> getAll() {
		List<UsuarioAdministrador> entityList = usuarioAdministradorRepo.findAll();
		List<UsuarioAdministradorDTO> dtoList = new ArrayList<>();
		entityList.forEach(entidad -> dtoList.add(toDto(entidad)));
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<UsuarioAdministrador> encontrado = usuarioAdministradorRepo.findById(id);
		if (encontrado.isPresent()) {
			usuarioAdministradorRepo.delete(encontrado.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, UsuarioAdministradorDTO data) {
		Optional<UsuarioAdministrador> encontrado = usuarioAdministradorRepo.findById(id);
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

		UsuarioAdministrador temp = encontrado.get();
		temp.setNombre(data.getNombre());
		temp.setContrasena(passwordEncoder.encode(data.getContrasena()));
		temp.setTipoUsuario(TipoUsuario.ADMIN);
		usuarioAdministradorRepo.save(temp);
		return 0;
	}

	@Override
	public long count() {
		return usuarioAdministradorRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return usuarioAdministradorRepo.existsById(id);
	}

	private boolean findNombreAlreadyTaken(String nombre) {
		return usuarioAdministradorRepo.findByNombre(nombre).isPresent()
				|| usuarioEditorRepo.findByNombre(nombre).isPresent()
				|| usuarioComentaristaRepo.findByNombre(nombre).isPresent()
				|| usuarioNormalRepo.findByNombre(nombre).isPresent();
	}

	private UsuarioAdministradorDTO toDto(UsuarioAdministrador entity) {
		UsuarioAdministradorDTO dto = mapper.map(entity, UsuarioAdministradorDTO.class);
		dto.setContrasena(null);
		return dto;
	}
}
