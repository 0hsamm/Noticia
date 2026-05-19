package co.edu.unbosque.paginanoticia.dto;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoUsuario;

/**
 * Clase DTO que representa la información de un usuario editor.
 * Extiende de UsuarioDTO y añade el identificador único del editor.
 */
public class UsuarioEditorDTO extends UsuarioDTO {

	private Long id;

	public UsuarioEditorDTO() {
	}

	/**
	 * Constructor que inicializa los datos básicos del usuario editor.
	 *
	 * @param nombre nombre del usuario
	 * @param contrasena contraseña del usuario
	 * @param tipoUsuario tipo de usuario editor
	 */
	public UsuarioEditorDTO(String nombre, String contrasena, TipoUsuario tipoUsuario) {
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
		UsuarioEditorDTO other = (UsuarioEditorDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "UsuarioEditorDTO [id=" + id + "]";
	}
}