package com.gft.veterinario.dto.usuario;

public class ConsultaUsuarioDTO {

	private String cpf;
	private String nomePerfil;
	
	public ConsultaUsuarioDTO(String cpf, String nomePerfil) {
		this.cpf = cpf;
		this.nomePerfil = nomePerfil;
	}

	public ConsultaUsuarioDTO() {

	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNomePerfil() {
		return nomePerfil;
	}
	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}
	
}
