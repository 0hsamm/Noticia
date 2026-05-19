package co.edu.unbosque.paginanoticia.entity;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoHoroscopo;
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
 * Entidad que representa un horóscopo dentro del sistema.
 * Cada horóscopo está asociado a un usuario editor.
 */
@Entity
@Table(name = "horoscopos")
public class Horoscopo {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	private TipoHoroscopo tipoHoroscopo;

	private String contenido;

	@Enumerated(EnumType.STRING)
	private TipoPublicacion tipoPublicacion;

	/**
	 * Relación muchos a uno con UsuarioEditor.
	 * Un editor puede crear múltiples horóscopos.
	 */
	@ManyToOne
	@JoinColumn(name = "usuario_editor_id")
	private UsuarioEditor usuarioEditor;

	public Horoscopo() {
	}

	/**
	 * Constructor que inicializa los datos principales de un horóscopo.
	 *
	 * @param tipoHoroscopo tipo de horóscopo
	 * @param contenido contenido del horóscopo
	 * @param tipoPublicacion tipo de publicación del horóscopo
	 * @param usuarioEditor usuario editor responsable
	 */
	public Horoscopo(TipoHoroscopo tipoHoroscopo, String contenido, TipoPublicacion tipoPublicacion,
			UsuarioEditor usuarioEditor) {

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

	public UsuarioEditor getUsuarioEditor() {
		return usuarioEditor;
	}

	public void setUsuarioEditor(UsuarioEditor usuarioEditor) {
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
		Horoscopo other = (Horoscopo) obj;
		return Objects.equals(contenido, other.contenido)
				&& id == other.id
				&& tipoHoroscopo == other.tipoHoroscopo
				&& tipoPublicacion == other.tipoPublicacion
				&& Objects.equals(usuarioEditor, other.usuarioEditor);
	}

	@Override
	public String toString() {
		return "Horoscopo [id=" + id + ", tipoHoroscopo=" + tipoHoroscopo + ", contenido=" + contenido
				+ ", tipoPublicacion=" + tipoPublicacion + ", usuarioEditor=" + usuarioEditor + "]";
	}
}