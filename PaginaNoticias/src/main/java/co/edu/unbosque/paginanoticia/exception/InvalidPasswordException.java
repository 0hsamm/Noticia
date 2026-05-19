package co.edu.unbosque.paginanoticia.exception;

/**
 * Excepción que se lanza cuando la contraseña ingresada no cumple
 * con los requisitos mínimos de seguridad establecidos.
 */
public class InvalidPasswordException extends Exception {

	/**
	 * Constructor de la excepción InvalidPasswordException.
	 * Indica que la contraseña no cumple con la longitud mínima requerida.
	 */
	public InvalidPasswordException() {
		super("La contrasena debe tener 8 caracteres como minimo");
	}
}