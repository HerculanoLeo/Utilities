package br.com.herculano.utilities.exceptions;

public class DadosInvalidosException extends RuntimeException  {

	private static final long serialVersionUID = -5544026238389586612L;

	public DadosInvalidosException(String message) {
		super(message);
	}
}
