package co.edu.unbosque.paginanoticia.dto;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoUsuario;

/**
 * Clase DTO que representa la información de un usuario administrador.
 * Extiende de UsuarioDTO y agrega el identificador único del administrador.
 */
public class UsuarioAdministradorDTO extends UsuarioDTO {

	private Long id;

	public UsuarioAdministradorDTO() {
	}

	/**
	 * Constructor que inicializa los datos principales del usuario administrador.
	 *
	 * @param nombre nombre del usuario
	 * @param contrasena contraseña del usuario
	 * @param tipoUsuario tipo de usuario administrador
	 */
	public UsuarioAdministradorDTO(String nombre, String contrasena, TipoUsuario tipoUsuario) {
		super(nombre, contrasena, tipoUsuario);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioAdministradorDTO other = (UsuarioAdministradorDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "UsuarioAdministradorDTO [id=" + id + "]";
	}
}