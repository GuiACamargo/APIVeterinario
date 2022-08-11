package com.gft.veterinario.dto.cliente;

import java.util.List;

import com.gft.veterinario.entities.Atendimento;

public class ConsultaClienteDTO {

	private Long id;
	private String nome;
	private String cpf;
	private List<Atendimento> atendimentos;
	private List<String> animais;
	
	public ConsultaClienteDTO() {
	}
	public ConsultaClienteDTO(Long id, String nome, String cpf, List<Atendimento> atendimentos, List<String> animais) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.atendimentos = atendimentos;
		this.animais = animais;
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
	public List<Atendimento> getAtendimentos() {
		return atendimentos;
	}
	public void setAtendimentos(List<Atendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}
	public List<String> getAnimais() {
		return animais;
	}
	public void setAnimais(List<String> animais) {
		this.animais = animais;
	}
}
