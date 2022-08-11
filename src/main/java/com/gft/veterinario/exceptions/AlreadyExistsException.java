package com.gft.veterinario.exceptions;

public class AlreadyExistsException extends MensagemException {

	private static final long serialVersionUID = -4732145767116739018L;

	public AlreadyExistsException(String mensagem) {
		super(mensagem);
	}

}
