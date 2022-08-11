package com.gft.veterinario.dto.usuario;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MudarUsuarioDTO {

	private String usuario;
	private String senhaInicial;
	@NotNull(message = "Senha nova não pode ficar em branco!")
	@NotEmpty(message = "Senha nova deve possuir no mínimo um caractere!")
	private String senhaNova;
	
	public MudarUsuarioDTO() {
	}
	public MudarUsuarioDTO(String usuario, String senhaInicial, String senhaNova) {
		this.usuario = usuario;
		this.senhaInicial = senhaInicial;
		this.senhaNova = senhaNova;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenhaInicial() {
		return senhaInicial;
	}
	public void setSenhaInicial(String senhaInicial) {
		this.senhaInicial = senhaInicial;
	}
	public String getSenhaNova() {
		return senhaNova;
	}
	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}
	
	
}
