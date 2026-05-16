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
@Table(name = "noticias")
public class Noticia {

	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	
	private String titulo;
	private String contenido;

	@Enumerated(EnumType.STRING)
	private TipoPublicacion tipoPublicacion;
	
    @ManyToOne
    @JoinColumn(name = "usuario_comentarista_id")
    private UsuarioComentarista usuarioComentarista; 
	
	public Noticia() {
		// TODO Auto-generated constructor stub
	}

	

	public Noticia(String titulo, String contenido, TipoPublicacion tipoPublicacion,
			UsuarioComentarista usuarioComentarista) {
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

	public UsuarioComentarista getUsuarioComentarista() {
		return usuarioComentarista;
	}

	public void setUsuarioComentarista(UsuarioComentarista usuarioComentarista) {
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
		Noticia other = (Noticia) obj;
		return Objects.equals(contenido, other.contenido) && id == other.id && tipoPublicacion == other.tipoPublicacion
				&& Objects.equals(titulo, other.titulo)
				&& Objects.equals(usuarioComentarista, other.usuarioComentarista);
	}



	@Override
	public String toString() {
		return "Noticia [id=" + id + ", titulo=" + titulo + ", contenido=" + contenido + ", tipoPublicacion="
				+ tipoPublicacion + ", usuarioComentarista=" + usuarioComentarista + "]";
	}
	

	
	
	
	
	
	
}
