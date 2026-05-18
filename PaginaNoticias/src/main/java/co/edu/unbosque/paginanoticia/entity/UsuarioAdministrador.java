package co.edu.unbosque.paginanoticia.entity;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarioadministrador")
public class UsuarioAdministrador extends Usuario{

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	
	public UsuarioAdministrador() {
		// TODO Auto-generated constructor stub
	}

	public UsuarioAdministrador(String nombre, String contrasena, TipoUsuario tipoUsuario) {
		super(nombre, contrasena, tipoUsuario);
	
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
		UsuarioAdministrador other = (UsuarioAdministrador) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "UsuarioAdministrador [id=" + id + "]";
	}
	
	

	
	
	
	
	
}
