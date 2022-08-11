package com.gft.veterinario.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Veterinario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Nome não pode ficar em branco!")
	@NotEmpty(message = "Nome deve possuir no mínimo um caractere!")
	private String nome;
	@Column(unique = true)
	@NotNull(message = "CPF não pode ficar em branco!")
	@NotEmpty(message = "CPF deve possuir no mínimo um caractere!")
	private String cpf;
	@NotNull(message = "Especialidade não pode ficar em branco!")
	@NotEmpty(message = "Especialidade deve possuir no mínimo um caractere!")
	private String especialidade;
	@OneToMany(mappedBy = "veterinario", cascade = CascadeType.MERGE)
	@JsonManagedReference
	private List<Atendimento> atendimentos;
	
	public Veterinario() {
	}
	
	public Veterinario(Long id, String nome, String cpf, String especialidade, List<Atendimento> atendimentos) {
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
