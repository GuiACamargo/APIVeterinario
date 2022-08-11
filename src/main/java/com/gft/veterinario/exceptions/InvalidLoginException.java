package com.gft.veterinario.exceptions;

public class InvalidLoginException extends MensagemException {

	private static final long serialVersionUID = -872535100645109209L;

	public InvalidLoginException(String mensagem) {
		super(mensagem);
	}

}
