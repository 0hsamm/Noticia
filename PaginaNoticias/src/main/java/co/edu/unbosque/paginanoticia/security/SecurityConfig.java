package co.edu.unbosque.paginanoticia.security;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de configuración de seguridad de la aplicación.
 * Se encarga de definir las reglas de acceso a los endpoints, la autenticación basada en JWT,
 * la configuración del proveedor de autenticación, el manejo de sesiones y la política de CORS.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final UserDetailsService userDetailsService;

	/**
	 * Constructor que inyecta el filtro JWT y el servicio de detalles del usuario.
	 */
	public SecurityConfig(
			JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Configura la cadena de filtros de seguridad HTTP.
	 * Define qué rutas son públicas, cuáles requieren autenticación y qué roles pueden acceder a cada recurso.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults())
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/auth/**").permitAll()
								.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/error").permitAll()
								.requestMatchers(
										HttpMethod.GET,
										"/noticia/mostrartodo",
										"/horoscopo/mostrartodo",
										"/comentario/mostrartodo",
										"/comentario/noticia/**",
										"/comentario/horoscopo/**")
								.hasAnyRole("ADMIN", "EDITOR", "COMENTARISTA", "USUARIO")
								.requestMatchers(
										HttpMethod.GET,
										"/usuarioadministrador/mostrartodo",
										"/usuarionormal/mostrartodo"
										)
								.hasRole("ADMIN")
								.requestMatchers(
										HttpMethod.POST,
										"/noticia/crear",
										"/horoscopo/crear")
								.hasRole("EDITOR")
								.requestMatchers(
										HttpMethod.PUT,
										"/noticia/actualizar",
										"/horoscopo/actualizar")
								.hasRole("EDITOR")
								.requestMatchers(
										HttpMethod.DELETE,
										"/noticia/eliminar",
										"/horoscopo/eliminar")
								.hasRole("EDITOR")
								.requestMatchers(HttpMethod.POST, "/comentario/create").hasRole("COMENTARISTA")
								.requestMatchers(HttpMethod.PUT, "/comentario/actualizar").hasRole("COMENTARISTA")
								.requestMatchers(HttpMethod.DELETE, "/comentario/delete/**")
								.hasRole("COMENTARISTA")
								.requestMatchers("/usuarioeditor/**", "/usuariocomentarista/**")
								.hasRole("ADMIN")
								.requestMatchers("/usuarioadministrador/**", "/usuarionormal/**")
								.denyAll()
								.anyRequest().authenticated())
				.sessionManagement(
						session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	/**
	 * Configura el proveedor de autenticación basado en base de datos.
	 * Utiliza un servicio de usuarios y un codificador de contraseñas BCrypt.
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	/**
	 * Retorna el administrador de autenticación del sistema.
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
			throws Exception {
		return config.getAuthenticationManager();
	}

	/**
	 * Define el codificador de contraseñas utilizado para el almacenamiento seguro de credenciales.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configura la política de CORS para permitir la comunicación con el frontend.
	 * Permite solicitudes desde la aplicación Angular en localhost:4200.
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:4200"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}