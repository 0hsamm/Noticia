package co.edu.unbosque.paginanoticia.dto;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;

public class ComentarioDTO {

	private long id;
	private String contenido;
	private TipoPublicacion tipoPublicacion;
	private long usuarioComentaristaId;
	
	
	public ComentarioDTO() {
		// TODO Auto-generated constructor stub
	}


	public ComentarioDTO(long id, String contenido, TipoPublicacion tipoPublicacion, long usuarioComentaristaId) {
		super();
		this.id = id;
		this.contenido = contenido;
		this.tipoPublicacion = tipoPublicacion;
		this.usuarioComentaristaId = usuarioComentaristaId;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
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


	public long getUsuarioComentaristaId() {
		return usuarioComentaristaId;
	}


	public void setUsuarioComentaristaId(long usuarioComentaristaId) {
		this.usuarioComentaristaId = usuarioComentaristaId;
	}


	@Override
	public int hashCode() {
		return Objects.hash(contenido, id, tipoPublicacion, usuarioComentaristaId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComentarioDTO other = (ComentarioDTO) obj;
		return Objects.equals(contenido, other.contenido) && id == other.id && tipoPublicacion == other.tipoPublicacion
				&& usuarioComentaristaId == other.usuarioComentaristaId;
	}


	@Override
	public String toString() {
		return "ComentarioDTO [id=" + id + ", contenido=" + contenido + ", tipoPublicacion=" + tipoPublicacion
				+ ", usuarioComentaristaId=" + usuarioComentaristaId + "]";
	}
	
	
	
	
}
