package com.gft.veterinario.exceptions;

public class AnimalAlreadyExistsException extends MensagemException {

	private static final long serialVersionUID = -5307770219318868515L;

	public AnimalAlreadyExistsException(String mensagem) {
		super(mensagem);
	}

}
