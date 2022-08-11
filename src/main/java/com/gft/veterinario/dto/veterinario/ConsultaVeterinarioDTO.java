package com.gft.veterinario.dto.veterinario;

import java.util.List;

import com.gft.veterinario.entities.Atendimento;

public class ConsultaVeterinarioDTO {

	private Long id;
	private String nome;
	private String cpf;
	private String especialidade;
	private List<Atendimento> atendimentos;
	
	public ConsultaVeterinarioDTO() {
	}
	public ConsultaVeterinarioDTO(Long id, String nome, String cpf, String especialidade, List<Atendimento> atendimentos) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.especialidade = especialidade;
		this.atendimentos = atendimentos;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public List<Atendimento> getAtendimentos() {
		return atendimentos;
	}
	public void setAtendimentos(List<Atendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}
	
}
