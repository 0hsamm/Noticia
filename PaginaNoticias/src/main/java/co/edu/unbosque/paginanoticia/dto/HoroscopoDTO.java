package co.edu.unbosque.paginanoticia.dto;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoHoroscopo;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;

/**
 * Clase DTO que representa la información de un horóscopo.
 * Contiene los datos necesarios para la transferencia entre capas del sistema.
 */
public class HoroscopoDTO {

	private Long id;
	private TipoHoroscopo tipoHoroscopo;
	private String contenido;
	private TipoPublicacion tipoPublicacion;
	private String usuarioEditor;

	public HoroscopoDTO() {
	}

	/**
	 * Constructor que inicializa los datos principales de un horóscopo.
	 *
	 * @param tipoHoroscopo tipo de horóscopo
	 * @param contenido contenido del horóscopo
	 * @param tipoPublicacion tipo de publicación asociada
	 * @param usuarioEditor usuario editor responsable del horóscopo
	 */
	public HoroscopoDTO(TipoHoroscopo tipoHoroscopo, String contenido, TipoPublicacion tipoPublicacion,
			String usuarioEditor) {

		this.tipoHoroscopo = tipoHoroscopo;
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

	public TipoHoroscopo getTipoHoroscopo() {
		return tipoHoroscopo;
	}

	public void setTipoHoroscopo(TipoHoroscopo tipoHoroscopo) {
		this.tipoHoroscopo = tipoHoroscopo;
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
				&& Objects.equals(usuarioEditor, other.usuarioEditor);
	}

	@Override
	public String toString() {
		return "HoroscopoDTO [id=" + id + ", tipoHoroscopo=" + tipoHoroscopo + ", contenido=" + contenido
				+ ", tipoPublicacion=" + tipoPublicacion + ", usuarioEditor=" + usuarioEditor + "]";
	}
}