package co.edu.unbosque.paginanoticia.dto;

import java.util.Objects;

public class RespuestaLoginDTO {

	private String token;
	private String nombre;
	private String rol;
	
	
	public RespuestaLoginDTO() {
		// TODO Auto-generated constructor stub
	}


	public RespuestaLoginDTO(String token, String nombre, String rol) {
		super();
		this.token = token;
		this.nombre = nombre;
		this.rol = rol;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getRol() {
		return rol;
	}


	public void setRol(String rol) {
		this.rol = rol;
	}


	@Override
	public int hashCode() {
		return Objects.hash(nombre, rol, token);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RespuestaLoginDTO other = (RespuestaLoginDTO) obj;
		return Objects.equals(nombre, other.nombre) && Objects.equals(rol, other.rol)
				&& Objects.equals(token, other.token);
	}


	@Override
	public String toString() {
		return "RespuestaSolicitudDTO [token=" + token + ", nombre=" + nombre + ", rol=" + rol + "]";
	}
	
	
	
}
