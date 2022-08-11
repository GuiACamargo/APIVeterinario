package com.gft.veterinario.dto.veterinario;

import java.util.List;

public class RegistroVeterinarioDTO {

	private String nome;
	private String cpf;
	private String especialidade;
	private List<Long> atendimentosId; 
	
	public RegistroVeterinarioDTO() {
	}
	public RegistroVeterinarioDTO(String nome, String cpf, String especialidade, List<Long> atendimentosId) {
		this.nome = nome;
		this.cpf = cpf;
		this.especialidade = especialidade;
		this.atendimentosId = atendimentosId;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
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
