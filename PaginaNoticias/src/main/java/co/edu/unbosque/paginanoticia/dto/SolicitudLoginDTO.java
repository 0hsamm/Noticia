package co.edu.unbosque.paginanoticia.dto;

import java.util.Objects;

public class SolicitudLoginDTO {

	private String nombre;
	private String contrasena;
	
	
	
	public SolicitudLoginDTO() {
		// TODO Auto-generated constructor stub
	}



	public SolicitudLoginDTO(String nombre, String contrasena) {
		super();
		this.nombre = nombre;
		this.contrasena = contrasena;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getContrasena() {
		return contrasena;
	}



	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}



	@Override
	public int hashCode() {
		return Objects.hash(contrasena, nombre);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolicitudLoginDTO other = (SolicitudLoginDTO) obj;
		return Objects.equals(contrasena, other.contrasena) && Objects.equals(nombre, other.nombre);
	}



	@Override
	public String toString() {
		return "SolicitudLoginDTO [nombre=" + nombre + ", contrasena=" + contrasena + "]";
	}
	
	
	
	
	
}
