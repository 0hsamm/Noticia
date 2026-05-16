package co.edu.unbosque.paginanoticia.dto;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;

public class NoticiaDTO {

	private Long id;
	private String titulo;
	private String contenido;
	private TipoPublicacion tipoPublicacion;
	private String usuarioComentarista;
	
	
	public NoticiaDTO() {
		// TODO Auto-generated constructor stub
	}


	

	public NoticiaDTO(String titulo, String contenido, TipoPublicacion tipoPublicacion, String usuarioComentarista) {
		super();
		this.titulo = titulo;
		this.contenido = contenido;
		this.tipoPublicacion = tipoPublicacion;
		this.usuarioComentarista = usuarioComentarista;
	}




	public String getTitulo() {
		return titulo;
	}




	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}




	public void setId(Long id) {
		this.id = id;
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


	public String getUsuarioComentarista() {
		return usuarioComentarista;
	}


	public void setUsuarioComentarista(String usuarioComentarista) {
		this.usuarioComentarista = usuarioComentarista;
	}




	@Override
	public int hashCode() {
		return Objects.hash(contenido, id, tipoPublicacion, titulo, usuarioComentarista);
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
				&& usuarioComentarista == other.usuarioComentarista;
	}




	@Override
	public String toString() {
		return "NoticiaDTO [id=" + id + ", titulo=" + titulo + ", contenido=" + contenido + ", tipoPublicacion="
				+ tipoPublicacion + ", usuarioComentarista=" + usuarioComentarista + "]";
	}


	
	
	
	
	
}
