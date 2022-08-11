package com.gft.veterinario.dto.animal;

import java.util.List;

import com.gft.veterinario.entities.Atendimento;
import com.gft.veterinario.entities.DadosDoAnimal;
import com.gft.veterinario.entities.Raca;

public class ConsultaAnimalDTO {

	private Long id;
	private String nome;
	private int idade;
	private Raca raca;
	private String nomeDoDono;
	private List<Atendimento> atendimentos;
	private DadosDoAnimal dadosIniciaisDoAnimal;
	
	public ConsultaAnimalDTO() {
	}
	public ConsultaAnimalDTO(Long id, String nome, int idade, Raca raca, String nomeDoDono,
			List<Atendimento> atendimentos, DadosDoAnimal dadosDoAnimal) {
		this.id = id;
		this.nome = nome;
		this.idade = idade;
		this.raca = raca;
		this.nomeDoDono = nomeDoDono;
		this.atendimentos = atendimentos;
		this.dadosIniciaisDoAnimal = dadosDoAnimal;
	}

	public List<Atendimento> getAtendimentos() {
		return atendimentos;
	}
	public void setAtendimentos(List<Atendimento> atendimentos) {
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
	public DadosDoAnimal getDadosDoAnimal() {
		return dadosIniciaisDoAnimal;
	}
	public void setDadosDoAnimal(DadosDoAnimal dadosDoAnimal) {
		this.dadosIniciaisDoAnimal = dadosDoAnimal;
	}
	public String getNomeDoDono() {
		return nomeDoDono;
	}

	public void setNomeDoDono(String nomeDoDono) {
		this.nomeDoDono = nomeDoDono;
	}
}
