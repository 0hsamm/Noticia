package co.edu.unbosque.paginanoticia.exception;

public class InvalidPasswordException extends Exception {

	public InvalidPasswordException() {
		super("La contrasena debe tener 8 caracteres como minimo");
	}
}
