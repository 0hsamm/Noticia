package co.edu.unbosque.paginanoticia.dto;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;

public class HoroscopoDTO {
	
	
	private long id;
	private String contenido;
	private TipoPublicacion tipoPublicacion;
	private long usuarioEditorId;
	
	
	public HoroscopoDTO() {
		// TODO Auto-generated constructor stub
	}


	public HoroscopoDTO(long id, String contenido, TipoPublicacion tipoPublicacion, long usuarioEditorId) {
		super();
		this.id = id;
		this.contenido = contenido;
		this.tipoPublicacion = tipoPublicacion;
		this.usuarioEditorId = usuarioEditorId;
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


	public long getUsuarioEditorId() {
		return usuarioEditorId;
	}


	public void setUsuarioEditorId(long usuarioEditorId) {
		this.usuarioEditorId = usuarioEditorId;
	}


	@Override
	public int hashCode() {
		return Objects.hash(contenido, id, tipoPublicacion, usuarioEditorId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoroscopoDTO other = (HoroscopoDTO) obj;
		return Objects.equals(contenido, other.contenido) && id == other.id && tipoPublicacion == other.tipoPublicacion
				&& usuarioEditorId == other.usuarioEditorId;
	}


	@Override
	public String toString() {
		return "HoroscopoDTO [id=" + id + ", contenido=" + contenido + ", tipoPublicacion=" + tipoPublicacion
				+ ", usuarioEditorId=" + usuarioEditorId + "]";
	}
	
	
	
	

}
