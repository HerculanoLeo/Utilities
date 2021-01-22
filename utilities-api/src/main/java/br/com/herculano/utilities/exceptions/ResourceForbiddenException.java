package br.com.herculano.utilities.exceptions;

public class ResourceForbiddenException extends RuntimeException {

	private static final long serialVersionUID = -1585951574343781860L;

	public ResourceForbiddenException(String message) {
		super(message);
	}
}
