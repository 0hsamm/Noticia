package co.edu.unbosque.paginanoticia.entity;

import java.util.List;
import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase que representa un usuario comentarista dentro del sistema.
 * Hereda de Usuario y contiene la relación con los comentarios realizados.
 */
@Entity
@Table(name = "usuariocomentarista")
public class UsuarioComentarista extends Usuario {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	@OneToMany(mappedBy = "comentarista")
	private List<Comentario> comentarios;

	/**
	 * Constructor vacío de la clase UsuarioComentarista.
	 */
	public UsuarioComentarista() {
		// Constructor vacío requerido por JPA
	}

	/**
	 * Constructor que permite crear un usuario comentarista con su lista de comentarios.
	 *
	 * @param comentarios lista de comentarios asociados al usuario
	 */
	public UsuarioComentarista(List<Comentario> comentarios) {
		super();
		this.comentarios = comentarios;
	}

	/**
	 * Constructor que permite crear un usuario comentarista con sus datos básicos y comentarios.
	 *
	 * @param nombre nombre del usuario comentarista
	 * @param contrasena contraseña del usuario comentarista
	 * @param tipoUsuario tipo de usuario dentro del sistema
	 * @param comentarios lista de comentarios asociados al usuario
	 */
	public UsuarioComentarista(String nombre, String contrasena, TipoUsuario tipoUsuario,
			List<Comentario> comentarios) {
		super(nombre, contrasena, tipoUsuario);
		this.comentarios = comentarios;
	}

	/**
	 * Constructor que permite crear un usuario comentarista con sus datos básicos.
	 *
	 * @param nombre nombre del usuario comentarista
	 * @param contrasena contraseña del usuario comentarista
	 * @param tipoUsuario tipo de usuario dentro del sistema
	 */
	public UsuarioComentarista(String nombre, String contrasena, TipoUsuario tipoUsuario) {
		super(nombre, contrasena, tipoUsuario);
	}

	/**
	 * Retorna el identificador del usuario comentarista.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador del usuario comentarista.
	 *
	 * @param id nuevo identificador
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Retorna la lista de comentarios realizados por el usuario.
	 */
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	/**
	 * Establece la lista de comentarios del usuario.
	 *
	 * @param comentarios nueva lista de comentarios
	 */
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(comentarios, id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioComentarista other = (UsuarioComentarista) obj;
		return Objects.equals(comentarios, other.comentarios) && id == other.id;
	}

	@Override
	public String toString() {
		return "UsuarioComentarista [id=" + id + ", comentarios=" + comentarios + "]";
	}
}