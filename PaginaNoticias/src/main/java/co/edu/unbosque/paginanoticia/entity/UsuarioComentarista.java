package co.edu.unbosque.paginanoticia.entity;

import java.util.List;
import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuariocomentarista")
public class UsuarioComentarista extends Usuario{

	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	@OneToMany(mappedBy = "usuario")
	private List<Comentario> comentarios;
	
	
	public UsuarioComentarista() {
		// TODO Auto-generated constructor stub
	}


	public UsuarioComentarista(List<Comentario> comentarios) {
		super();
		this.comentarios = comentarios;
	}


	public UsuarioComentarista(String nombre, String contrasena, TipoUsuario tipoUsuario,
			List<Comentario> comentarios) {
		super(nombre, contrasena, tipoUsuario);
		this.comentarios = comentarios;
	}


	public UsuarioComentarista(String nombre, String contrasena, TipoUsuario tipoUsuario) {
		super(nombre, contrasena, tipoUsuario);
		// TODO Auto-generated constructor stub
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(comentarios, id);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioComentarista other = (UsuarioComentarista) obj;
		return Objects.equals(comentarios, other.comentarios) && id == other.id;
	}


	@Override
	public String toString() {
		return "UsuarioComentarista [id=" + id + ", comentarios=" + comentarios + "]";
	}
	
	
	
	
}
