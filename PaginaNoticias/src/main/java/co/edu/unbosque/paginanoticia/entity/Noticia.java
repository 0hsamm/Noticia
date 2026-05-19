package co.edu.unbosque.paginanoticia.entity;

import java.util.Objects;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Clase que representa una noticia dentro del sistema.
 * Contiene la información principal de una publicación como su título,
 * contenido, tipo de publicación y el usuario editor asociado.
 */
@Entity
@Table(name = "noticias")
public class Noticia {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	private String titulo;
	private String contenido;

	@Enumerated(EnumType.STRING)
	private TipoPublicacion tipoPublicacion;

	@ManyToOne
	@JoinColumn(name = "usuario_editor_id")
	private UsuarioEditor usuarioEditor;

	/**
	 * Constructor vacío de la clase Noticia.
	 */
	public Noticia() {
		// Constructor vacío requerido por JPA
	}

	/**
	 * Constructor que permite crear una noticia con sus atributos principales.
	 *
	 * @param titulo título de la noticia
	 * @param contenido contenido de la noticia
	 * @param tipoPublicacion tipo de publicación de la noticia
	 * @param usuarioEditor usuario editor asociado a la noticia
	 */
	public Noticia(String titulo, String contenido, TipoPublicacion tipoPublicacion,
			UsuarioEditor usuarioEditor) {
		super();
		this.titulo = titulo;
		this.contenido = contenido;
		this.tipoPublicacion = tipoPublicacion;
		this.usuarioEditor = usuarioEditor;
	}

	/**
	 * Retorna el título de la noticia.
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Establece el título de la noticia.
	 *
	 * @param titulo nuevo título de la noticia
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Retorna el identificador de la noticia.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador de la noticia.
	 *
	 * @param id identificador de la noticia
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Retorna el contenido de la noticia.
	 */
	public String getContenido() {
		return contenido;
	}

	/**
	 * Establece el contenido de la noticia.
	 *
	 * @param contenido nuevo contenido de la noticia
	 */
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	/**
	 * Retorna el tipo de publicación de la noticia.
	 */
	public TipoPublicacion getTipoPublicacion() {
		return tipoPublicacion;
	}

	/**
	 * Establece el tipo de publicación de la noticia.
	 *
	 * @param tipoPublicacion nuevo tipo de publicación
	 */
	public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
		this.tipoPublicacion = tipoPublicacion;
	}

	/**
	 * Retorna el usuario editor asociado a la noticia.
	 */
	public UsuarioEditor getUsuarioEditor() {
		return usuarioEditor;
	}

	/**
	 * Establece el usuario editor asociado a la noticia.
	 *
	 * @param usuarioEditor nuevo usuario editor
	 */
	public void setUsuarioEditor(UsuarioEditor usuarioEditor) {
		this.usuarioEditor = usuarioEditor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contenido, id, tipoPublicacion, titulo, usuarioEditor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Noticia other = (Noticia) obj;
		return Objects.equals(contenido, other.contenido) && id == other.id
				&& tipoPublicacion == other.tipoPublicacion
				&& Objects.equals(titulo, other.titulo)
				&& Objects.equals(usuarioEditor, other.usuarioEditor);
	}

	@Override
	public String toString() {
		return "Noticia [id=" + id + ", titulo=" + titulo + ", contenido=" + contenido
				+ ", tipoPublicacion=" + tipoPublicacion + ", usuarioComentarista=" + usuarioEditor + "]";
	}
}