package co.edu.unbosque.paginanoticia.entity;


import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarionormal")
public class UsuarioNormal extends Usuario{

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private Double tarifa;
	

	public UsuarioNormal() {
		this.tarifa = 0.0;
	}



	public UsuarioNormal(String nombre, String contrasena, TipoUsuario tipoUsuario) {
		super(nombre, contrasena, tipoUsuario);
		this.tarifa = 0.0;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}

	public Double getTarifa() {
		return tarifa;
	}

	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, tarifa);
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
		UsuarioNormal other = (UsuarioNormal) obj;
		return id == other.id && Objects.equals(tarifa, other.tarifa);
	}



	@Override
	public String toString() {
		return "UsuarioNormal [id=" + id + ", tarifa=" + tarifa + "]";
	}
	
	
	
	
}
