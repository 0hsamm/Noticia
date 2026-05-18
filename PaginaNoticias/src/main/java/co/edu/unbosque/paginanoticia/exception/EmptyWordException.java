package co.edu.unbosque.paginanoticia.exception;

public class EmptyWordException extends Exception{

	public EmptyWordException() {
		super("Campo vacio por favor ingrese una palabra");
	}
	
}
