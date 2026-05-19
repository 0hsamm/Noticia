package co.edu.unbosque.paginanoticia.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoHoroscopo;
import co.edu.unbosque.paginanoticia.enums.TipoPublicacion;

/**
 * Clase DTO que representa la información de un comentario.
 * Contiene los datos necesarios para la transferencia entre capas del sistema.
 */
public class ComentarioDTO {

	private Long id;

	private String contenido;

	private LocalDateTime fecha;

	private String nombreComentarista;

	private String tituloNoticia;

	private TipoHoroscopo signoHoroscopo;

	private Long horoscopoId;

	private TipoPublicacion tipoPublicacion;

	public ComentarioDTO() {
	}

	/**
	 * Constructor que inicializa los datos principales de un comentario.
	 *
	 * @param contenido contenido del comentario
	 * @param fecha fecha del comentario
	 * @param nombreComentarista nombre del usuario que realiza el comentario
	 * @param tituloNoticia título de la noticia asociada
	 * @param signoHoroscopo signo del horóscopo asociado
	 * @param tipoPublicacion tipo de publicación asociada
	 */
	public ComentarioDTO(String contenido, LocalDateTime fecha, String nombreComentarista, String tituloNoticia,
			TipoHoroscopo signoHoroscopo, TipoPublicacion tipoPublicacion) {

		this.contenido = contenido;
		this.fecha = fecha;
		this.nombreComentarista = nombreComentarista;
		this.tituloNoticia = tituloNoticia;
		this.signoHoroscopo = signoHoroscopo;
		this.tipoPublicacion = tipoPublicacion;
	}

	public Long getId() {
		return id;
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

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getNombreComentarista() {
		return nombreComentarista;
	}

	public void setNombreComentarista(String nombreComentarista) {
		this.nombreComentarista = nombreComentarista;
	}

	public String getTituloNoticia() {
		return tituloNoticia;
	}

	public void setTituloNoticia(String tituloNoticia) {
		this.tituloNoticia = tituloNoticia;
	}

	public TipoHoroscopo getSignoHoroscopo() {
		return signoHoroscopo;
	}

	public void setSignoHoroscopo(TipoHoroscopo signoHoroscopo) {
		this.signoHoroscopo = signoHoroscopo;
	}

	public Long getHoroscopoId() {
		return horoscopoId;
	}

	public void setHoroscopoId(Long horoscopoId) {
		this.horoscopoId = horoscopoId;
	}

	public TipoPublicacion getTipoPublicacion() {
		return tipoPublicacion;
	}

	public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
		this.tipoPublicacion = tipoPublicacion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contenido, fecha, horoscopoId, id, nombreComentarista, signoHoroscopo, tipoPublicacion,
				tituloNoticia);
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
		return Objects.equals(contenido, other.contenido) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(horoscopoId, other.horoscopoId) && Objects.equals(id, other.id)
				&& Objects.equals(nombreComentarista, other.nombreComentarista)
				&& signoHoroscopo == other.signoHoroscopo && tipoPublicacion == other.tipoPublicacion
				&& Objects.equals(tituloNoticia, other.tituloNoticia);
	}

	@Override
	public String toString() {
		return "ComentarioDTO [id=" + id + ", contenido=" + contenido + ", fecha=" + fecha + ", nombreComentarista="
				+ nombreComentarista + ", tituloNoticia=" + tituloNoticia + ", signoHoroscopo=" + signoHoroscopo
				+ ", horoscopoId=" + horoscopoId + ", tipoPublicacion=" + tipoPublicacion + "]";
	}
}