package co.edu.unbosque.paginanoticia.service;

import co.edu.unbosque.paginanoticia.dto.UsuarioNormalDTO;
import co.edu.unbosque.paginanoticia.entity.UsuarioNormal;
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

	    UsuarioNormal usuarioNormal = mapper.map(data, UsuarioNormal.class);
	    usuarioNormal.setContrasena(passwordEncoder.encode(data.getContrasena()));
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

	    if (encontrado.isEmpty()) {
	        return 1;
	    }

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    String username = auth.getName();
	    Optional<UsuarioNormal> usuarioOpt = usuarioNormalRepo.findByNombre(username);

	    if (usuarioOpt.isEmpty()) {
	        return 2;
	    }

	    UsuarioNormal usuario = encontrado.get();
	    if (usuario.getId() != usuarioOpt.get().getId()) {
	        return 3;
	    }

	    usuarioNormalRepo.delete(usuario);
	    return 0;
	}

	@Override
	public int updateById(Long id, UsuarioNormalDTO data) {

	    Optional<UsuarioNormal> encontrado = usuarioNormalRepo.findById(id);

	    if (encontrado.isEmpty()) {
	        return 4;
	    }

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    Optional<UsuarioNormal> usuarioOpt = usuarioNormalRepo.findByNombre(username);

	    if (usuarioOpt.isEmpty()) {
	        return 5;
	    }

	    UsuarioNormal usuario = encontrado.get();
	    if (usuario.getId() != usuarioOpt.get().getId()) {
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

	    if (!usuario.getNombre().equals(data.getNombre()) && findNombreAlreadyTaken(data.getNombre())) {
	        return 3;
	    }

	    usuario.setNombre(data.getNombre());
	    usuario.setContrasena(passwordEncoder.encode(data.getContrasena()));
	    usuario.setTipoUsuario(TipoUsuario.USUARIO);
	    usuarioNormalRepo.save(usuario);
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
