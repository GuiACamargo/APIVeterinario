package com.gft.veterinario.dto.cliente;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RegistroClienteComSenhaDTO {

	@NotNull(message = "Senha não pode ficar em branco!")
	@NotEmpty(message = "Senha deve possuir no mínimo um caractere!")
	private String senhaUsadaParaLogin;
	private String nome;
	private String cpfUsadoParaLogin;
	private List<Long> atedimentosId;
	private List<Long> idDoAnimal;
	
	public RegistroClienteComSenhaDTO() {
	}
	public RegistroClienteComSenhaDTO(String senhaUsadaParaLogin, String nome, String cpfUsadoParaLogin,
			List<Long> atedimentosId, List<Long> idDoAnimal) {
		this.senhaUsadaParaLogin = senhaUsadaParaLogin;
		this.nome = nome;
		this.cpfUsadoParaLogin = cpfUsadoParaLogin;
		this.atedimentosId = atedimentosId;
		this.idDoAnimal = idDoAnimal;
	}
	
	public String getSenhaUsadaParaLogin() {
		return senhaUsadaParaLogin;
	}
	public void setSenhaUsadaParaLogin(String senhaUsadaParaLogin) {
		this.senhaUsadaParaLogin = senhaUsadaParaLogin;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpfUsadoParaLogin() {
		return cpfUsadoParaLogin;
	}
	public void setCpfUsadoParaLogin(String cpfUsadoParaLogin) {
		this.cpfUsadoParaLogin = cpfUsadoParaLogin;
	}
	public List<Long> getAtedimentosId() {
		return atedimentosId;
	}
	public void setAtedimentosId(List<Long> atedimentosId) {
		this.atedimentosId = atedimentosId;
	}
	public List<Long> getIdDoAnimal() {
		return idDoAnimal;
	}
	public void setIdDoAnimal(List<Long> idDoAnimal) {
		this.idDoAnimal = idDoAnimal;
	}	
}
