package com.gft.veterinario.exceptions;

public class LackOfSpaceException extends MensagemException {

	private static final long serialVersionUID = 121155201338622407L;

	public LackOfSpaceException(String mensagem) {
		super(mensagem);
	}

}
