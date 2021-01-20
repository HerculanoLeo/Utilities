package br.com.herculano.utilities.exceptions;

public class EmptyPerfilException extends RuntimeException {

	private static final long serialVersionUID = -1556512321131989010L;

	public EmptyPerfilException(String message) {
		super(message);
	}
}
