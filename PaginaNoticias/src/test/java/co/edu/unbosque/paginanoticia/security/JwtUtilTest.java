package co.edu.unbosque.paginanoticia.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.unbosque.paginanoticia.entity.Usuario;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;

class JwtUtilTest {

	private JwtUtil jwtUtil;

	@BeforeEach
	void setUp() {

		jwtUtil = new JwtUtil();

		try {

			Field secretField = JwtUtil.class.getDeclaredField("secret");

			secretField.setAccessible(true);

			secretField.set(
					jwtUtil,
					"defaultSecretKeyWhichShouldBeAtLeast32CharactersLong"
			);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Test
	void testGenerateToken() {

		Usuario usuario = new Usuario();

		usuario.setNombre("samuel");
		usuario.setContrasena("12345678");
		usuario.setTipoUsuario(TipoUsuario.ADMIN);

		String token = jwtUtil.generateToken(usuario);

		assertNotNull(token);
		assertTrue(token.length() > 0);
	}

	@Test
	void testExtractUsername() {

		Usuario usuario = new Usuario();

		usuario.setNombre("samuel");
		usuario.setContrasena("12345678");
		usuario.setTipoUsuario(TipoUsuario.ADMIN);

		String token = jwtUtil.generateToken(usuario);

		String username = jwtUtil.extractUsername(token);

		assertEquals("samuel", username);
	}

	@Test
	void testExtractRole() {

		Usuario usuario = new Usuario();

		usuario.setNombre("samuel");
		usuario.setContrasena("12345678");
		usuario.setTipoUsuario(TipoUsuario.ADMIN);

		String token = jwtUtil.generateToken(usuario);

		String role = jwtUtil.extractRole(token);

		assertEquals("ADMIN", role);
	}

	@Test
	void testExtractExpiration() {

		Usuario usuario = new Usuario();

		usuario.setNombre("samuel");
		usuario.setContrasena("12345678");
		usuario.setTipoUsuario(TipoUsuario.ADMIN);

		String token = jwtUtil.generateToken(usuario);

		Date expiration = jwtUtil.extractExpiration(token);

		assertNotNull(expiration);
		assertTrue(expiration.after(new Date()));
	}

	@Test
	void testValidateToken() {

		Usuario usuario = new Usuario();

		usuario.setNombre("samuel");
		usuario.setContrasena("12345678");
		usuario.setTipoUsuario(TipoUsuario.ADMIN);

		String token = jwtUtil.generateToken(usuario);

		Boolean valido = jwtUtil.validateToken(token, usuario);

		assertTrue(valido);
	}
}