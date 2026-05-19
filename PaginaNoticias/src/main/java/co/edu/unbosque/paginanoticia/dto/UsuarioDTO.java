package co.edu.unbosque.paginanoticia.dto;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoUsuario;

/**
 * Clase DTO base que representa la información general de un usuario.
 * Sirve como clase padre para los diferentes tipos de usuarios del sistema.
 */
public class UsuarioDTO {

	private String nombre;
	private String contrasena;
	private TipoUsuario tipoUsuario;

	public UsuarioDTO() {
	}

	/**
	 * Constructor que inicializa los datos básicos de un usuario.
	 *
	 * @param nombre nombre del usuario
	 * @param contrasena contraseña del usuario
	 * @param tipoUsuario tipo de usuario
	 */
	public UsuarioDTO(String nombre, String contrasena, TipoUsuario tipoUsuario) {

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

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
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
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(contrasena, other.contrasena)
				&& Objects.equals(nombre, other.nombre)
				&& tipoUsuario == other.tipoUsuario;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [nombre=" + nombre + ", contrasena=" + contrasena + ", tipoUsuario=" + tipoUsuario + "]";
	}
}