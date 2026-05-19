package co.edu.unbosque.paginanoticia.entity;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Clase base que representa un usuario del sistema.
 * Implementa UserDetails para integrarse con el sistema de seguridad.
 * Contiene la información común para los distintos tipos de usuarios.
 */
@MappedSuperclass
public class Usuario implements UserDetails {

	@Column(nullable = false, unique = true)
	private String nombre;

	@Column(nullable = false)
	private String contrasena;

	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;

	/**
	 * Constructor vacío de la clase Usuario.
	 */
	public Usuario() {
		// Constructor vacío requerido por JPA
	}

	/**
	 * Constructor que permite crear un usuario con sus atributos principales.
	 *
	 * @param nombre nombre de usuario
	 * @param contrasena contraseña del usuario
	 * @param tipoUsuario tipo de usuario dentro del sistema
	 */
	public Usuario(String nombre, String contrasena, TipoUsuario tipoUsuario) {
		super();
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.tipoUsuario = tipoUsuario;
	}

	/**
	 * Retorna el nombre del usuario.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del usuario.
	 *
	 * @param nombre nuevo nombre de usuario
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Retorna la contraseña del usuario.
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Establece la contraseña del usuario.
	 *
	 * @param contrasena nueva contraseña
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * Retorna el username utilizado por el sistema de seguridad.
	 */
	@Override
	public String getUsername() {
		return nombre;
	}

	/**
	 * Retorna el password utilizado por el sistema de seguridad.
	 */
	@Override
	public String getPassword() {
		return contrasena;
	}

	/**
	 * Retorna el tipo de usuario del sistema.
	 */
	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	/**
	 * Establece el tipo de usuario.
	 *
	 * @param tipoUsuario nuevo tipo de usuario
	 */
	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	/**
	 * Retorna los permisos o roles del usuario dentro del sistema de seguridad.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + tipoUsuario.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasena, nombre, tipoUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(contrasena, other.contrasena)
				&& Objects.equals(nombre, other.nombre)
				&& tipoUsuario == other.tipoUsuario;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", contrasena=" + contrasena + ", tipoUsuario=" + tipoUsuario + "]";
	}
}