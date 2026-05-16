package co.edu.unbosque.paginanoticia.exception;

public class LanzadorDeExcepcion {

	
	public static void verificarPalabraVacia(String s) throws EmptyWordException{
		if(s == null || s.trim().isEmpty()) {
			throw new EmptyWordException();
		}
	}
	
	
	
	
	
}
