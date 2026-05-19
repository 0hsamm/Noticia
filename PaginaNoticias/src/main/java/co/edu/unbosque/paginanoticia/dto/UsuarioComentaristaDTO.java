package co.edu.unbosque.paginanoticia.dto;

import java.util.List;
import java.util.Objects;

import co.edu.unbosque.paginanoticia.entity.Comentario;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;

/**
 * Clase DTO que representa la información de un usuario comentarista.
 * Extiende de UsuarioDTO e incluye la lista de comentarios realizados por el usuario.
 */
public class UsuarioComentaristaDTO extends UsuarioDTO {

	private Long id;
	private List<Comentario> comentarios;

	public UsuarioComentaristaDTO() {
	}

	/**
	 * Constructor que inicializa la lista de comentarios del usuario comentarista.
	 *
	 * @param comentarios lista de comentarios asociados al usuario
	 */
	public UsuarioComentaristaDTO(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * Constructor que inicializa los datos del usuario comentarista junto con sus comentarios.
	 *
	 * @param nombre nombre del usuario
	 * @param contrasena contraseña del usuario
	 * @param tipoUsuario tipo de usuario comentarista
	 * @param comentarios lista de comentarios asociados al usuario
	 */
	public UsuarioComentaristaDTO(String nombre, String contrasena, TipoUsuario tipoUsuario,
			List<Comentario> comentarios) {

		super(nombre, contrasena, tipoUsuario);
		this.comentarios = comentarios;
	}

	/**
	 * Constructor que inicializa los datos básicos del usuario comentarista.
	 *
	 * @param nombre nombre del usuario
	 * @param contrasena contraseña del usuario
	 * @param tipoUsuario tipo de usuario comentarista
	 */
	public UsuarioComentaristaDTO(String nombre, String contrasena, TipoUsuario tipoUsuario) {
		super(nombre, contrasena, tipoUsuario);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), comentarios, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioComentaristaDTO other = (UsuarioComentaristaDTO) obj;
		return Objects.equals(comentarios, other.comentarios) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "UsuarioComentaristaDTO [id=" + id + ", comentarios=" + comentarios + "]";
	}
}