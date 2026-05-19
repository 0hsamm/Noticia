package co.edu.unbosque.paginanoticia.dto;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoUsuario;

/**
 * Clase DTO que representa la información de un usuario normal.
 * Extiende de UsuarioDTO y añade el identificador único del usuario.
 */
public class UsuarioNormalDTO extends UsuarioDTO {

	private Long id;

	public UsuarioNormalDTO() {
	}

	/**
	 * Constructor que inicializa los datos básicos del usuario normal.
	 *
	 * @param nombre nombre del usuario
	 * @param contrasena contraseña del usuario
	 * @param tipoUsuario tipo de usuario normal
	 */
	public UsuarioNormalDTO(String nombre, String contrasena, TipoUsuario tipoUsuario) {
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
		UsuarioNormalDTO other = (UsuarioNormalDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "UsuarioNormalDTO [id=" + id + "]";
	}
}