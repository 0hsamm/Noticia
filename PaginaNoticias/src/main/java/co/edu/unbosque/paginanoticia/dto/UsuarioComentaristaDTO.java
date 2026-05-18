package co.edu.unbosque.paginanoticia.dto;

import java.util.List;
import java.util.Objects;

import co.edu.unbosque.paginanoticia.entity.Comentario;
import co.edu.unbosque.paginanoticia.enums.TipoUsuario;

public class UsuarioComentaristaDTO extends UsuarioDTO{
	private Long id;
	private List<Comentario> comentarios;
	
	public UsuarioComentaristaDTO() {
		// TODO Auto-generated constructor stub
	}

	public UsuarioComentaristaDTO(List<Comentario> comentarios) {
		super();
		this.comentarios = comentarios;
	}

	public UsuarioComentaristaDTO(String nombre, String contrasena, TipoUsuario tipoUsuario,
			List<Comentario> comentarios) {
		super(nombre, contrasena, tipoUsuario);
		this.comentarios = comentarios;
	}

	public UsuarioComentaristaDTO(String nombre, String contrasena, TipoUsuario tipoUsuario) {
		super(nombre, contrasena, tipoUsuario);
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
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
		UsuarioComentaristaDTO other = (UsuarioComentaristaDTO) obj;
		return Objects.equals(comentarios, other.comentarios) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "UsuarioComentaristaDTO [id=" + id + ", comentarios=" + comentarios + "]";
	}
	
	
	
	
}
