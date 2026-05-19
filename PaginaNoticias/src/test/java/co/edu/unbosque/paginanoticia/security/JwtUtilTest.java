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

/**
 * Clase de prueba para la utilidad JWT.
 * Permite validar la generación de tokens, extracción de datos del token
 * y verificación de su validez.
 */
class JwtUtilTest {

	private JwtUtil jwtUtil;

	/**
	 * Configuración inicial de la utilidad JWT antes de cada prueba,
	 * incluyendo la asignación de una clave secreta de prueba.
	 */
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

	/**
	 * Prueba que valida la generación de un token JWT.
	 */
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

	/**
	 * Prueba que valida la extracción del nombre de usuario desde el token.
	 */
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

	/**
	 * Prueba que valida la extracción del rol desde el token.
	 */
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

	/**
	 * Prueba que valida la extracción de la fecha de expiración del token.
	 */
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

	/**
	 * Prueba que valida la verificación de un token JWT válido.
	 */
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