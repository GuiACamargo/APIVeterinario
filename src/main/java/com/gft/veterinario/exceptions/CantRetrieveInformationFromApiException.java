package com.gft.veterinario.exceptions;

public class CantRetrieveInformationFromApiException extends MensagemException{

	private static final long serialVersionUID = 1279484434124403145L;

	public CantRetrieveInformationFromApiException(String mensagem) {
		super(mensagem);
	}

}
