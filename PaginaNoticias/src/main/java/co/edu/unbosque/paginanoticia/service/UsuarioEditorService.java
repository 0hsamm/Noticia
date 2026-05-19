package co.edu.unbosque.paginanoticia.service;

import co.edu.unbosque.paginanoticia.dto.UsuarioEditorDTO;
import co.edu.unbosque.paginanoticia.entity.UsuarioAdministrador;
import co.edu.unbosque.paginanoticia.entity.UsuarioEditor;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.exception.EmptyWordException;
import co.edu.unbosque.paginanoticia.exception.InvalidPasswordException;
import co.edu.unbosque.paginanoticia.exception.LanzadorDeExcepcion;
import co.edu.unbosque.paginanoticia.repository.HoroscopoRepository;
import co.edu.unbosque.paginanoticia.repository.NoticiaRepository;
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
 * Servicio encargado de la gestión de usuarios editores.
 * Permite crear, actualizar, eliminar y consultar editores,
 * validando autenticación de administrador, unicidad de nombre
 * y reglas de seguridad de contraseña.
 */
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
	
	@Autowired
	private NoticiaRepository noticiaRepo;

	@Autowired
	private HoroscopoRepository horoscopoRepo;

	/**
	 * Crea un nuevo usuario editor validando autenticación de administrador,
	 * nombre único y requisitos de contraseña.
	 */
	@Override
	public int create(UsuarioEditorDTO data) {

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

	    UsuarioEditor usuarioEditor = mapper.map(data, UsuarioEditor.class);
	    usuarioEditor.setContrasena(passwordEncoder.encode(data.getContrasena()));
	    usuarioEditor.setTipoUsuario(TipoUsuario.EDITOR);
	    usuarioEditorRepo.save(usuarioEditor);
	    return 0;
	}

	/**
	 * Obtiene todos los usuarios editores registrados en el sistema.
	 */
	@Override
	public List<UsuarioEditorDTO> getAll() {
		List<UsuarioEditor> entityList = usuarioEditorRepo.findAll();
		List<UsuarioEditorDTO> dtoList = new ArrayList<>();
		entityList.forEach(entidad -> dtoList.add(toDto(entidad)));
		return dtoList;
	}

	/**
	 * Elimina un usuario editor validando su existencia y rol de administrador.
	 * También elimina noticias y horóscopos asociados al editor.
	 */
	@Override
	@Transactional
	public int deleteById(Long id) {

	    Optional<UsuarioEditor> encontrado = usuarioEditorRepo.findById(id);

	    if (encontrado.isEmpty()) {
	        return 1;
	    }

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    Optional<UsuarioAdministrador> adminOpt = usuarioAdministradorRepo.findByNombre(username);

	    if (adminOpt.isEmpty()) {
	        return 2;
	    }

	    noticiaRepo.deleteByEditorId(id);
	    horoscopoRepo.deleteByEditorId(id);
	    usuarioEditorRepo.delete(encontrado.get());
	    return 0;
	}

	/**
	 * Actualiza un usuario editor validando autenticación,
	 * unicidad del nombre y seguridad de la contraseña.
	 */
	@Override
	public int updateById(Long id, UsuarioEditorDTO data) {

	    Optional<UsuarioEditor> encontrado = usuarioEditorRepo.findById(id);

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

	/**
	 * Verifica si un nombre de usuario ya existe en cualquier tipo de usuario del sistema.
	 */
	private boolean findNombreAlreadyTaken(String nombre) {
		return usuarioEditorRepo.findByNombre(nombre).isPresent()
				|| usuarioAdministradorRepo.findByNombre(nombre).isPresent()
				|| usuarioComentaristaRepo.findByNombre(nombre).isPresent()
				|| usuarioNormalRepo.findByNombre(nombre).isPresent();
	}

	/**
	 * Convierte una entidad UsuarioEditor a su DTO correspondiente.
	 */
	private UsuarioEditorDTO toDto(UsuarioEditor entity) {
		UsuarioEditorDTO dto = mapper.map(entity, UsuarioEditorDTO.class);
		dto.setContrasena(null);
		return dto;
	}
}