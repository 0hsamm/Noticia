package co.edu.unbosque.paginanoticia.controller;

import co.edu.unbosque.paginanoticia.dto.UsuarioDTO;
import co.edu.unbosque.paginanoticia.dto.UsuarioNormalDTO;
import co.edu.unbosque.paginanoticia.entity.Usuario;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import co.edu.unbosque.paginanoticia.security.JwtUtil;
import co.edu.unbosque.paginanoticia.service.UsuarioNormalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador encargado de manejar la autenticación y el registro de usuarios.
 * Permite iniciar sesión y registrar nuevos usuarios en el sistema.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UsuarioNormalService usuarioNormalService;

	public AuthController(
			AuthenticationManager authenticationManager,
			JwtUtil jwtUtil,
			UsuarioNormalService usuarioNormalService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.usuarioNormalService = usuarioNormalService;
	}

	/**
	 * Realiza el proceso de autenticación de un usuario.
	 * Si las credenciales son válidas, genera un token JWT y retorna el rol del usuario.
	 *
	 * @param loginRequest datos de inicio de sesión (nombre y contraseña)
	 * @return token JWT y rol del usuario si la autenticación es exitosa
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UsuarioDTO loginRequest) {
		try {
			Authentication authentication =
					authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(
									loginRequest.getNombre(), loginRequest.getContrasena()));

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String jwt = jwtUtil.generateToken(userDetails);

			String role = null;
			if (userDetails instanceof Usuario) {
				Usuario usuario = (Usuario) userDetails;
				role = usuario.getTipoUsuario().name();
			}

			return ResponseEntity.ok(new AuthResponse(jwt, role));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Nombre o contrasena invalidos o usuario no encontrado");
		}
	}

	/**
	 * Permite registrar un nuevo usuario en el sistema.
	 * Valida los datos y retorna un mensaje según el resultado del proceso.
	 *
	 * @param registerRequest datos del usuario a registrar
	 * @return respuesta indicando el estado del registro
	 */
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UsuarioDTO registerRequest) {
		UsuarioNormalDTO nuevoUsuario =
				new UsuarioNormalDTO(
						registerRequest.getNombre(),
						registerRequest.getContrasena(),
						TipoUsuario.USUARIO);

		int result = usuarioNormalService.create(nuevoUsuario);

		if (result == 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
		}
		if (result == 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre no puede estar vacio");
		}
		if (result == 2) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("La contrasena debe tener al menos 8 caracteres");
		}
		if (result == 3) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar el usuario");
	}

	/**
	 * Clase interna que representa la respuesta de autenticación.
	 * Contiene el token JWT y el rol del usuario autenticado.
	 */
	private static class AuthResponse {
		private final String token;
		private final String role;

		public AuthResponse(String token, String role) {
			this.token = token;
			this.role = role;
		}

		public String getToken() {
			return token;
		}

		public String getRole() {
			return role;
		}
	}
}