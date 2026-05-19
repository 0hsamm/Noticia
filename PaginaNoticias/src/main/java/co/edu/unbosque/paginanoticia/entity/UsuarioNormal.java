package co.edu.unbosque.paginanoticia.entity;

import java.util.Objects;

import co.edu.unbosque.paginanoticia.enums.TipoUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase que representa un usuario normal dentro del sistema.
 * Hereda de Usuario y contiene la información básica de un usuario estándar.
 */
@Entity
@Table(name = "usuarionormal_taller")
public class UsuarioNormal extends Usuario {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	/**
	 * Constructor vacío de la clase UsuarioNormal.
	 */
	public UsuarioNormal() {
	}

	/**
	 * Constructor que permite crear un usuario normal con sus datos básicos.
	 *
	 * @param nombre nombre del usuario
	 * @param contrasena contraseña del usuario
	 * @param tipoUsuario tipo de usuario dentro del sistema
	 */
	public UsuarioNormal(String nombre, String contrasena, TipoUsuario tipoUsuario) {
		super(nombre, contrasena, tipoUsuario);
	}

	/**
	 * Retorna el identificador del usuario normal.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador del usuario normal.
	 *
	 * @param id nuevo identificador
	 */
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
		UsuarioNormal other = (UsuarioNormal) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "UsuarioNormal [id=" + id + "]";
	}
}