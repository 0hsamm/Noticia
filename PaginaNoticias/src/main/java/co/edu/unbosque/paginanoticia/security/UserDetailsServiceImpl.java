package co.edu.unbosque.paginanoticia.security;

import co.edu.unbosque.paginanoticia.entity.Usuario;
import co.edu.unbosque.paginanoticia.repository.UsuarioAdministradorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioComentaristaRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioEditorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioNormalRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UsuarioAdministradorRepository usuarioAdministradorRepository;
	private final UsuarioEditorRepository usuarioEditorRepository;
	private final UsuarioComentaristaRepository usuarioComentaristaRepository;
	private final UsuarioNormalRepository usuarioNormalRepository;

	public UserDetailsServiceImpl(
			UsuarioAdministradorRepository usuarioAdministradorRepository,
			UsuarioEditorRepository usuarioEditorRepository,
			UsuarioComentaristaRepository usuarioComentaristaRepository,
			UsuarioNormalRepository usuarioNormalRepository) {
		this.usuarioAdministradorRepository = usuarioAdministradorRepository;
		this.usuarioEditorRepository = usuarioEditorRepository;
		this.usuarioComentaristaRepository = usuarioComentaristaRepository;
		this.usuarioNormalRepository = usuarioNormalRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<? extends Usuario> usuario = usuarioAdministradorRepository.findByNombre(username);
		if (usuario.isPresent()) {
			return usuario.get();
		}

		usuario = usuarioEditorRepository.findByNombre(username);
		if (usuario.isPresent()) {
			return usuario.get();
		}

		usuario = usuarioComentaristaRepository.findByNombre(username);
		if (usuario.isPresent()) {
			return usuario.get();
		}

		usuario = usuarioNormalRepository.findByNombre(username);
		if (usuario.isPresent()) {
			return usuario.get();
		}

		throw new UsernameNotFoundException("User not found with username: " + username);
	}
}
