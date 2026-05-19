package co.edu.unbosque.paginanoticia.service;

import co.edu.unbosque.paginanoticia.dto.UsuarioComentaristaDTO;
import co.edu.unbosque.paginanoticia.entity.UsuarioAdministrador;
import co.edu.unbosque.paginanoticia.entity.UsuarioComentarista;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.exception.EmptyWordException;
import co.edu.unbosque.paginanoticia.exception.InvalidPasswordException;
import co.edu.unbosque.paginanoticia.exception.LanzadorDeExcepcion;
import co.edu.unbosque.paginanoticia.repository.ComentarioRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioAdministradorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioComentaristaRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioEditorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioNormalRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Servicio encargado de la gestión de usuarios comentaristas.
 * Permite crear, actualizar, eliminar y consultar comentaristas,
 * validando autenticación, unicidad de nombre y reglas de seguridad de contraseña.
 */
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
	private ComentarioRepository comentarioRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Crea un nuevo usuario comentarista validando que el usuario autenticado
	 * sea un administrador y que los datos cumplan las reglas de negocio.
	 */
	@Override
	public int create(UsuarioComentaristaDTO data) {

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

	    UsuarioComentarista usuarioComentarista = mapper.map(data, UsuarioComentarista.class);
	    usuarioComentarista.setContrasena(passwordEncoder.encode(data.getContrasena()));
	    usuarioComentarista.setTipoUsuario(TipoUsuario.COMENTARISTA);
	    usuarioComentaristaRepo.save(usuarioComentarista);
	    return 0;
	}

	/**
	 * Obtiene todos los usuarios comentaristas registrados en el sistema.
	 */
	@Override
	public List<UsuarioComentaristaDTO> getAll() {
		List<UsuarioComentarista> entityList = usuarioComentaristaRepo.findAll();
		List<UsuarioComentaristaDTO> dtoList = new ArrayList<>();
		entityList.forEach(entidad -> dtoList.add(toDto(entidad)));
		return dtoList;
	}

	/**
	 * Elimina un usuario comentarista validando su existencia y el rol de administrador.
	 * También elimina los comentarios asociados al usuario.
	 */
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

	    comentarioRepo.deleteByComentaristaId(id);
	    usuarioComentaristaRepo.delete(encontrado.get());
	    return 0;
	}

	/**
	 * Actualiza un usuario comentarista validando autenticación de administrador,
	 * unicidad del nombre y reglas de contraseña.
	 */
	@Override
	@Transactional
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

	/**
	 * Verifica si un nombre de usuario ya está registrado en cualquier tipo de usuario del sistema.
	 */
	private boolean findNombreAlreadyTaken(String nombre) {
		return usuarioComentaristaRepo.findByNombre(nombre).isPresent()
				|| usuarioAdministradorRepo.findByNombre(nombre).isPresent()
				|| usuarioEditorRepo.findByNombre(nombre).isPresent()
				|| usuarioNormalRepo.findByNombre(nombre).isPresent();
	}

	/**
	 * Convierte una entidad UsuarioComentarista a su DTO correspondiente.
	 */
	private UsuarioComentaristaDTO toDto(UsuarioComentarista entity) {
		UsuarioComentaristaDTO dto = mapper.map(entity, UsuarioComentaristaDTO.class);
		dto.setContrasena(null);
		dto.setComentarios(null);
		return dto;
	}
}