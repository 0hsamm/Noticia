package co.edu.unbosque.paginanoticia.dto;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;

/**
 * Clase DTO que representa la información de una noticia.
 * Contiene los datos necesarios para la transferencia entre capas del sistema.
 */
public class NoticiaDTO {

	private Long id;
	private String titulo;
	private String contenido;
	private TipoPublicacion tipoPublicacion;
	private String usuarioEditor;

	public NoticiaDTO() {
	}

	/**
	 * Constructor que inicializa los datos principales de una noticia.
	 *
	 * @param titulo título de la noticia
	 * @param contenido contenido de la noticia
	 * @param tipoPublicacion tipo de publicación asociada
	 * @param usuarioEditor usuario editor responsable de la noticia
	 */
	public NoticiaDTO(String titulo, String contenido, TipoPublicacion tipoPublicacion, String usuarioEditor) {

		this.titulo = titulo;
		this.contenido = contenido;
		this.tipoPublicacion = tipoPublicacion;
		this.usuarioEditor = usuarioEditor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public TipoPublicacion getTipoPublicacion() {
		return tipoPublicacion;
	}

	public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
		this.tipoPublicacion = tipoPublicacion;
	}

	public String getUsuarioEditor() {
		return usuarioEditor;
	}

	public void setUsuarioEditor(String usuarioEditor) {
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
		NoticiaDTO other = (NoticiaDTO) obj;
		return Objects.equals(contenido, other.contenido) && Objects.equals(id, other.id)
				&& tipoPublicacion == other.tipoPublicacion && Objects.equals(titulo, other.titulo)
				&& Objects.equals(usuarioEditor, other.usuarioEditor);
	}

	@Override
	public String toString() {
		return "NoticiaDTO [id=" + id + ", titulo=" + titulo + ", contenido=" + contenido + ", tipoPublicacion="
				+ tipoPublicacion + ", usuarioEditor=" + usuarioEditor + "]";
	}
}