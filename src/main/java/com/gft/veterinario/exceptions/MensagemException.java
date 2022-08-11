package com.gft.veterinario.exceptions;

public class MensagemException extends RuntimeException {

	private static final long serialVersionUID = 5615169231351209679L;
	
	private String mensagem;

	public MensagemException(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
