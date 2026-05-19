package co.edu.unbosque.paginanoticia.exception;

/**
 * Clase utilitaria encargada de validar reglas básicas de negocio
 * y lanzar excepciones personalizadas cuando no se cumplen.
 */
public class LanzadorDeExcepcion {

	/**
	 * Verifica si una palabra es nula o está vacía.
	 * En caso de estar vacía, lanza una excepción EmptyWordException.
	 *
	 * @param s cadena de texto a validar
	 * @throws EmptyWordException si la cadena es null o está vacía
	 */
	public static void verificarPalabraVacia(String s) throws EmptyWordException {
		if (s == null || s.trim().isEmpty()) {
			throw new EmptyWordException();
		}
	}

	/**
	 * Verifica que la contraseña cumpla con la longitud mínima requerida.
	 * En caso de no cumplirla, lanza una excepción InvalidPasswordException.
	 *
	 * @param s contraseña a validar
	 * @throws InvalidPasswordException si la contraseña tiene menos de 8 caracteres
	 */
	public static void verificarTamanoContrasena(String s) throws InvalidPasswordException {

		if (s.length() < 8) {
			throw new InvalidPasswordException();
		}

	}
}