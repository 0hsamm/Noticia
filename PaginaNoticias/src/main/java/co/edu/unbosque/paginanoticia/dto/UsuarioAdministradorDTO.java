package co.edu.unbosque.paginanoticia.dto;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoUsuario;

public class UsuarioAdministradorDTO extends UsuarioDTO{

	private long id;
	
	public UsuarioAdministradorDTO() {
		// TODO Auto-generated constructor stub
	}

	public UsuarioAdministradorDTO(long id) {
		super();
		this.id = id;
	}

	public UsuarioAdministradorDTO(String nombre, String contrasena, TipoUsuario tipoUsuario, long id) {
		super(nombre, contrasena, tipoUsuario);
		this.id = id;
	}

	public UsuarioAdministradorDTO(String nombre, String contrasena, TipoUsuario tipoUsuario) {
		super(nombre, contrasena, tipoUsuario);
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
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
		UsuarioAdministradorDTO other = (UsuarioAdministradorDTO) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "UsuarioAdministradorDTO [id=" + id + "]";
	}
	
	
	
	
	
}
