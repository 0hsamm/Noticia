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

@Entity
@Table(name = "horoscopos")
public class Horoscopo {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	
	private String contenido;

	@Enumerated(EnumType.STRING)
	private TipoPublicacion tipoPublicacion;
	
    @ManyToOne
    @JoinColumn(name = "usuario_editor_id")
    private UsuarioEditor usuarioEditor; 
	
	public Horoscopo() {
		// TODO Auto-generated constructor stub
	}

	public Horoscopo(String contenido, TipoPublicacion tipoPublicacion, UsuarioEditor usuarioEditor) {
		super();
		this.contenido = contenido;
		this.tipoPublicacion = tipoPublicacion;
		this.usuarioEditor = usuarioEditor;
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
		return Objects.hash(contenido, id, tipoPublicacion, usuarioEditor);
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
		return Objects.equals(contenido, other.contenido) && id == other.id && tipoPublicacion == other.tipoPublicacion
				&& Objects.equals(usuarioEditor, other.usuarioEditor);
	}

	@Override
	public String toString() {
		return "Horoscopo [id=" + id + ", contenido=" + contenido + ", tipoPublicacion=" + tipoPublicacion
				+ ", usuarioEditor=" + usuarioEditor + "]";
	}
	
	
	
	
}
