package co.edu.unbosque.paginanoticia.dto;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoHoroscopo;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;

public class HoroscopoDTO {
	
	
	private Long id;
	private TipoHoroscopo tipoHoroscopo;
	private String contenido;
	private TipoPublicacion tipoPublicacion;
	private String usuarioEditor;
	
	
	public HoroscopoDTO() {
		// TODO Auto-generated constructor stub
	}


	
	

	public HoroscopoDTO(TipoHoroscopo tipoHoroscopo, String contenido, TipoPublicacion tipoPublicacion,
			String usuarioEditor) {
		super();
		this.tipoHoroscopo = tipoHoroscopo;
		this.contenido = contenido;
		this.tipoPublicacion = tipoPublicacion;
		this.usuarioEditor = usuarioEditor;
	}





	public TipoHoroscopo getTipoHoroscopo() {
		return tipoHoroscopo;
	}





	public void setTipoHoroscopo(TipoHoroscopo tipoHoroscopo) {
		this.tipoHoroscopo = tipoHoroscopo;
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


	public String getUsuarioEditor() {
		return usuarioEditor;
	}


	public void setUsuarioEditor(String usuarioEditor) {
		this.usuarioEditor = usuarioEditor;
	}





	@Override
	public int hashCode() {
		return Objects.hash(contenido, id, tipoHoroscopo, tipoPublicacion, usuarioEditor);
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
		return Objects.equals(contenido, other.contenido) && Objects.equals(id, other.id)
				&& tipoHoroscopo == other.tipoHoroscopo && tipoPublicacion == other.tipoPublicacion
				&& usuarioEditor == other.usuarioEditor;
	}





	@Override
	public String toString() {
		return "HoroscopoDTO [id=" + id + ", tipoHoroscopo=" + tipoHoroscopo + ", contenido=" + contenido
				+ ", tipoPublicacion=" + tipoPublicacion + ", usuarioEditorId=" + usuarioEditor + "]";
	}


	
	
	
	

}
