package co.edu.unbosque.paginanoticia.configuration;

import co.edu.unbosque.paginanoticia.entity.UsuarioAdministrador;
import co.edu.unbosque.paginanoticia.entity.UsuarioComentarista;
import co.edu.unbosque.paginanoticia.entity.UsuarioEditor;
import co.edu.unbosque.paginanoticia.entity.UsuarioNormal;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.repository.UsuarioAdministradorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioComentaristaRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioEditorRepository;
import co.edu.unbosque.paginanoticia.repository.UsuarioNormalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(
			UsuarioAdministradorRepository usuarioAdministradorRepository,
			UsuarioEditorRepository usuarioEditorRepository,
			UsuarioComentaristaRepository usuarioComentaristaRepository,
			UsuarioNormalRepository usuarioNormalRepository,
			PasswordEncoder passwordEncoder) {

		return args -> {
			if (!existsAnyUserWithName(
					"admin",
					usuarioAdministradorRepository,
					usuarioEditorRepository,
					usuarioComentaristaRepository,
					usuarioNormalRepository)) {
				UsuarioAdministrador admin =
						new UsuarioAdministrador(
								"admin",
								passwordEncoder.encode("1234567890"),
								TipoUsuario.ADMIN);
				usuarioAdministradorRepository.save(admin);
				log.info("Precargando usuario administrador");
			}

			if (!existsAnyUserWithName(
					"editor",
					usuarioAdministradorRepository,
					usuarioEditorRepository,
					usuarioComentaristaRepository,
					usuarioNormalRepository)) {
				UsuarioEditor editor =
						new UsuarioEditor(
								"editor",
								passwordEncoder.encode("1234567890"),
								TipoUsuario.EDITOR);
				usuarioEditorRepository.save(editor);
				log.info("Precargando usuario editor");
			}

			if (!existsAnyUserWithName(
					"comentarista",
					usuarioAdministradorRepository,
					usuarioEditorRepository,
					usuarioComentaristaRepository,
					usuarioNormalRepository)) {
				UsuarioComentarista comentarista =
						new UsuarioComentarista(
								"comentarista",
								passwordEncoder.encode("1234567890"),
								TipoUsuario.COMENTARISTA);
				usuarioComentaristaRepository.save(comentarista);
				log.info("Precargando usuario comentarista");
			}

			if (!existsAnyUserWithName(
					"normaluser",
					usuarioAdministradorRepository,
					usuarioEditorRepository,
					usuarioComentaristaRepository,
					usuarioNormalRepository)) {
				UsuarioNormal usuarioNormal =
						new UsuarioNormal(
								"normaluser",
								passwordEncoder.encode("1234567890"),
								TipoUsuario.USUARIO);
				usuarioNormal.setTarifa(0.0);
				usuarioNormalRepository.save(usuarioNormal);
				log.info("Precargando usuario normal");
			}
		};
	}

	private boolean existsAnyUserWithName(
			String nombre,
			UsuarioAdministradorRepository usuarioAdministradorRepository,
			UsuarioEditorRepository usuarioEditorRepository,
			UsuarioComentaristaRepository usuarioComentaristaRepository,
			UsuarioNormalRepository usuarioNormalRepository) {
		return usuarioAdministradorRepository.findByNombre(nombre).isPresent()
				|| usuarioEditorRepository.findByNombre(nombre).isPresent()
				|| usuarioComentaristaRepository.findByNombre(nombre).isPresent()
				|| usuarioNormalRepository.findByNombre(nombre).isPresent();
	}
}
