package co.edu.unbosque.paginanoticia.exception;

/**
 * Excepción que se lanza cuando un campo de texto se encuentra vacío
 * y se requiere que el usuario ingrese una palabra.
 */
public class EmptyWordException extends Exception {

	/**
	 * Constructor de la excepción EmptyWordException.
	 * Inicializa el mensaje de error indicando que el campo está vacío.
	 */
	public EmptyWordException() {
		super("Campo vacio por favor ingrese una palabra");
	}
}