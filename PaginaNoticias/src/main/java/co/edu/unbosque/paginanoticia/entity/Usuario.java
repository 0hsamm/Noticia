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

@MappedSuperclass
public class Usuario implements UserDetails {

	@Column(nullable = false, unique = true)
	private String nombre;

	@Column(nullable = false)
	private String contrasena;
	
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;
	
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}


	public Usuario(String nombre, String contrasena, TipoUsuario tipoUsuario) {
		super();
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.tipoUsuario = tipoUsuario;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getContrasena() {
		return contrasena;
	}


	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Override
	public String getPassword() {
		return contrasena;
	}

	@Override
	public String getUsername() {
		return nombre;
	}


	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}


	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

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
		return Objects.equals(contrasena, other.contrasena) && Objects.equals(nombre, other.nombre)
				&& tipoUsuario == other.tipoUsuario;
	}


	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", contrasena=" + contrasena + ", tipoUsuario=" + tipoUsuario + "]";
	}
	
	
	
}
