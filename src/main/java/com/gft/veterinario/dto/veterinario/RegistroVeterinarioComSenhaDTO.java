package com.gft.veterinario.dto.veterinario;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RegistroVeterinarioComSenhaDTO {

	@NotNull(message = "Senha não pode ficar em branco!")
	@NotEmpty(message = "Senha deve possuir no mínimo um caractere!")
	private String senhaUsadaParaLogin;
	private String nome;
	private String cpfUsadoParaLogin;
	private String especialidade;
	private List<Long> atendimentosId;
	
	public RegistroVeterinarioComSenhaDTO() {
	}
	public RegistroVeterinarioComSenhaDTO(String senhaUsadaParaLogin, String nome, String cpfUsadoParaLogin, 
			String especialidade, List<Long> atendimentosId) {
		this.senhaUsadaParaLogin = senhaUsadaParaLogin;
		this.nome = nome;
		this.cpfUsadoParaLogin = cpfUsadoParaLogin;
		this.especialidade = especialidade;
		this.atendimentosId = atendimentosId;
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
	public String getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
	public List<Long> getAtendimentosId() {
		return atendimentosId;
	}
	public void setAtendimentosId(List<Long> atendimentosId) {
		this.atendimentosId = atendimentosId;
	}
}
