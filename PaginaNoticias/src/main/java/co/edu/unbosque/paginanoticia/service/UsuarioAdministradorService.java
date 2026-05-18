package co.edu.unbosque.paginanoticia.service;

import co.edu.unbosque.paginanoticia.dto.UsuarioAdministradorDTO;
import co.edu.unbosque.paginanoticia.entity.UsuarioAdministrador;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.exception.EmptyWordException;
import co.edu.unbosque.paginanoticia.exception.InvalidPasswordException;
import co.edu.unbosque.paginanoticia.exception.LanzadorDeExcepcion;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    Optional<UsuarioAdministrador> adminOpt = usuarioAdministradorRepo.findByNombre(username);

	    if (adminOpt.isEmpty()) {
	        return 4;
	    }

	    try {
			LanzadorDeExcepcion.verificarPalabraVacia(data.getNombre());
		} catch (EmptyWordException e) {
			return 1;
		}

	    try {
			LanzadorDeExcepcion.verificarTamanoContrasena(data.getContrasena());
		} catch (InvalidPasswordException e) {
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
	    if (encontrado.isEmpty()) {
	        return 1;
	    }

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    Optional<UsuarioAdministrador> adminOpt = usuarioAdministradorRepo.findByNombre(username);

	    if (adminOpt.isEmpty()) {
	        return 2;
	    }

	    UsuarioAdministrador administrador = encontrado.get();
	    if (administrador.getId() != adminOpt.get().getId()) {
	        return 3;
	    }

	    usuarioAdministradorRepo.delete(administrador);
	    return 0;
	}

	@Override
	public int updateById(Long id, UsuarioAdministradorDTO data) {

	    Optional<UsuarioAdministrador> encontrado = usuarioAdministradorRepo.findById(id);
	    if (encontrado.isEmpty()) {
	        return 4;
	    }

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    Optional<UsuarioAdministrador> adminOpt = usuarioAdministradorRepo.findByNombre(username);

	    if (adminOpt.isEmpty()) {
	        return 5;
	    }

	    UsuarioAdministrador administrador = encontrado.get();
	    if (administrador.getId() != adminOpt.get().getId()) {
	        return 6;
	    }

	    try {
			LanzadorDeExcepcion.verificarPalabraVacia(data.getNombre());
		} catch (EmptyWordException e) {
			return 1;
		}
	    try {
			LanzadorDeExcepcion.verificarTamanoContrasena(data.getContrasena());
		} catch (InvalidPasswordException e) {
			return 2;
		}

	    if (!administrador.getNombre().equals(data.getNombre()) && findNombreAlreadyTaken(data.getNombre())) {
	        return 3;
	    }

	    administrador.setNombre(data.getNombre());
	    administrador.setContrasena(passwordEncoder.encode(data.getContrasena()));
	    administrador.setTipoUsuario(TipoUsuario.ADMIN);
	    usuarioAdministradorRepo.save(administrador);
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
