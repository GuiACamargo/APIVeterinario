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
public class Cliente {
	
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
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.MERGE)
	@JsonManagedReference
	private List<Atendimento> atendimentos;
	@OneToMany(mappedBy = "dono", cascade = CascadeType.MERGE)
	@JsonManagedReference
	private List<Animal> animais;
	
	public Cliente(Long id, String nome, String cpf, List<Atendimento> atendimentos, List<Animal> animais) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.atendimentos = atendimentos;
		this.animais = animais;
	}
	public Cliente() {
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
	public List<Animal> getAnimais() {
		return animais;
	}
	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}
}
