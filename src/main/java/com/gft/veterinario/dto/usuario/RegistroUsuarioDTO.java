package com.gft.veterinario.dto.usuario;

public class RegistroUsuarioDTO {

	private String email;
	private String senha;
	
	public RegistroUsuarioDTO(String email, String senha, Long perfilId) {
		this.email = email;
		this.senha = senha;
	}
	public RegistroUsuarioDTO() {

	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
