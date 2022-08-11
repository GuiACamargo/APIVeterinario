package com.gft.veterinario.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Animal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Nome não pode ficar em branco!")
	@NotEmpty(message = "Nome deve possuir no mínimo um caractere!")
	private String nome;
	@NotNull(message = "Idade não pode ficar em branco!")
	@PositiveOrZero(message = "Idade deve ser 0 ou Positivo!")
	private int idade;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "raca_id")
	@JsonBackReference
	private Raca raca;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "dono_id")
	@JsonBackReference
	private Cliente dono;
	@OneToMany(mappedBy = "animal", cascade = CascadeType.MERGE)
	@JsonManagedReference
	private List<Atendimento> atendimentos;
	@Embedded
	@Valid
	private DadosDoAnimal dadosIniciais;
	
	public Animal(Long id, String nome, int idade, Raca raca, Cliente dono, List<Atendimento> atendimentos,
			DadosDoAnimal dados) {
		this.id = id;
		this.nome = nome;
		this.idade = idade;
		this.raca = raca;
		this.dono = dono;
		this.atendimentos = atendimentos;
		this.dadosIniciais = dados;
	}
	public Animal() {
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
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public Raca getRaca() {
		return raca;
	}
	public void setRaca(Raca raca) {
		this.raca = raca;
	}
	public Cliente getDono() {
		return dono;
	}
	public void setDono(Cliente dono) {
		this.dono = dono;
	}
	public List<Atendimento> getAtendimentos() {
		return atendimentos;
	}
	public void setAtendimentos(List<Atendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}
	public DadosDoAnimal getDados() {
		return dadosIniciais;
	}
	public void setDados(DadosDoAnimal dados) {
		this.dadosIniciais = dados;
	}
}
