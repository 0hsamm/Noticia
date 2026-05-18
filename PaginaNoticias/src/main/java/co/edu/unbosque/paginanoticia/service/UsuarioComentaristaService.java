package co.edu.unbosque.paginanoticia.service;

import co.edu.unbosque.paginanoticia.dto.UsuarioComentaristaDTO;
import co.edu.unbosque.paginanoticia.entity.UsuarioAdministrador;
import co.edu.unbosque.paginanoticia.entity.UsuarioComentarista;
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
public class UsuarioComentaristaService implements CRUDOperation<UsuarioComentaristaDTO> {

	@Autowired
	private UsuarioComentaristaRepository usuarioComentaristaRepo;

	@Autowired
	private UsuarioAdministradorRepository usuarioAdministradorRepo;

	@Autowired
	private UsuarioEditorRepository usuarioEditorRepo;

	@Autowired
	private UsuarioNormalRepository usuarioNormalRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public int create(UsuarioComentaristaDTO data) {

	    Authentication auth =SecurityContextHolder.getContext().getAuthentication();
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

	    UsuarioComentarista usuarioComentarista = mapper.map(data, UsuarioComentarista.class);
	    usuarioComentarista.setContrasena(passwordEncoder.encode(data.getContrasena()));
	    usuarioComentarista.setTipoUsuario(TipoUsuario.COMENTARISTA);
	    usuarioComentaristaRepo.save(usuarioComentarista);
	    return 0;
	}
	    

	@Override
	public List<UsuarioComentaristaDTO> getAll() {
		List<UsuarioComentarista> entityList = usuarioComentaristaRepo.findAll();
		List<UsuarioComentaristaDTO> dtoList = new ArrayList<>();
		entityList.forEach(entidad -> dtoList.add(toDto(entidad)));
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {

	    Optional<UsuarioComentarista> encontrado = usuarioComentaristaRepo.findById(id);
	    
	    if (encontrado.isEmpty()) {
	        return 1;
	    }

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    Optional<UsuarioAdministrador> adminOpt = usuarioAdministradorRepo.findByNombre(username);

	    if (adminOpt.isEmpty()) {
	        return 2;
	    }

	    usuarioComentaristaRepo.delete(encontrado.get());
	    return 0;
	}

	@Override
	public int updateById(Long id, UsuarioComentaristaDTO data) {

	    Optional<UsuarioComentarista> encontrado = usuarioComentaristaRepo.findById(id);

	    if (encontrado.isEmpty()) {
	        return 4;
	    }

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    Optional<UsuarioAdministrador> adminOpt = usuarioAdministradorRepo.findByNombre(username);

	    if (adminOpt.isEmpty()) {
	        return 5;
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
	    

	    if (!encontrado.get().getNombre().equals(data.getNombre()) && findNombreAlreadyTaken(data.getNombre())) {
	        return 3;
	    }

	    UsuarioComentarista temp = encontrado.get();
	    temp.setNombre(data.getNombre());
	    temp.setContrasena(passwordEncoder.encode(data.getContrasena()));
	    temp.setTipoUsuario(TipoUsuario.COMENTARISTA);
	    usuarioComentaristaRepo.save(temp);
	    return 0;
	}

	@Override
	public long count() {
		return usuarioComentaristaRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return usuarioComentaristaRepo.existsById(id);
	}

	private boolean findNombreAlreadyTaken(String nombre) {
		return usuarioComentaristaRepo.findByNombre(nombre).isPresent()
				|| usuarioAdministradorRepo.findByNombre(nombre).isPresent()
				|| usuarioEditorRepo.findByNombre(nombre).isPresent()
				|| usuarioNormalRepo.findByNombre(nombre).isPresent();
	}

	private UsuarioComentaristaDTO toDto(UsuarioComentarista entity) {
		UsuarioComentaristaDTO dto = mapper.map(entity, UsuarioComentaristaDTO.class);
		dto.setContrasena(null);
		dto.setComentarios(null);
		return dto;
	}
}
