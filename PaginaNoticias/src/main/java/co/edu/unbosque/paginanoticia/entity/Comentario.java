package co.edu.unbosque.paginanoticia.entity;

import java.time.LocalDateTime;
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
 * Entidad que representa un comentario dentro del sistema.
 * Un comentario puede estar asociado a una noticia, un horóscopo y un usuario comentarista.
 */
@Entity
@Table(name = "comentarios")
public class Comentario {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	private String contenido;

	private LocalDateTime fecha;

	/**
	 * Relación muchos a uno con UsuarioComentarista.
	 * Un comentarista puede tener muchos comentarios.
	 */
	@ManyToOne
	@JoinColumn(name = "comentarista_id")
	private UsuarioComentarista comentarista;

	/**
	 * Relación muchos a uno con Noticia.
	 * Una noticia puede tener muchos comentarios.
	 */
	@ManyToOne
	@JoinColumn(name = "noticia_id")
	private Noticia noticia;

	/**
	 * Relación muchos a uno con Horoscopo.
	 * Un horóscopo puede tener muchos comentarios.
	 */
	@ManyToOne
	@JoinColumn(name = "horoscopo_id")
	private Horoscopo horoscopo;

	@Enumerated(EnumType.STRING)
	private TipoPublicacion tipoPublicacion;

	public Comentario() {
	}

	/**
	 * Constructor que inicializa los datos principales de un comentario.
	 *
	 * @param contenido contenido del comentario
	 * @param fecha fecha de creación del comentario
	 * @param comentarista usuario comentarista asociado
	 * @param noticia noticia asociada
	 * @param horoscopo horóscopo asociado
	 * @param tipoPublicacion tipo de publicación del comentario
	 */
	public Comentario(String contenido, LocalDateTime fecha, UsuarioComentarista comentarista, Noticia noticia,
			Horoscopo horoscopo, TipoPublicacion tipoPublicacion) {

		this.contenido = contenido;
		this.fecha = fecha;
		this.comentarista = comentarista;
		this.noticia = noticia;
		this.horoscopo = horoscopo;
		this.tipoPublicacion = tipoPublicacion;
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

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public UsuarioComentarista getComentarista() {
		return comentarista;
	}

	public void setComentarista(UsuarioComentarista comentarista) {
		this.comentarista = comentarista;
	}

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public Horoscopo getHoroscopo() {
		return horoscopo;
	}

	public void setHoroscopo(Horoscopo horoscopo) {
		this.horoscopo = horoscopo;
	}

	public TipoPublicacion getTipoPublicacion() {
		return tipoPublicacion;
	}

	public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
		this.tipoPublicacion = tipoPublicacion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(comentarista, contenido, fecha, horoscopo, id, noticia, tipoPublicacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comentario other = (Comentario) obj;
		return Objects.equals(comentarista, other.comentarista)
				&& Objects.equals(contenido, other.contenido)
				&& Objects.equals(fecha, other.fecha)
				&& Objects.equals(horoscopo, other.horoscopo)
				&& id == other.id
				&& Objects.equals(noticia, other.noticia)
				&& tipoPublicacion == other.tipoPublicacion;
	}

	@Override
	public String toString() {
		return "Comentario [id=" + id + ", contenido=" + contenido + ", fecha=" + fecha + ", comentarista="
				+ comentarista + ", noticia=" + noticia + ", horoscopo=" + horoscopo + ", tipoPublicacion="
				+ tipoPublicacion + "]";
	}
}