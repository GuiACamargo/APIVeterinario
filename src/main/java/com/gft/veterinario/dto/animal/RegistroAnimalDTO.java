package com.gft.veterinario.dto.animal;

import java.util.List;

import com.gft.veterinario.entities.DadosDoAnimal;

public class RegistroAnimalDTO {
	
	private String nome;
	private int idade;
	private Long idDaRaca;
	private Long idDoDono;
	private List<Long> idDoAtendimento;
	private DadosDoAnimal dadosIniciaisDoAnimal;
	
	public RegistroAnimalDTO() {
	}
	public RegistroAnimalDTO(String nome, int idade, Long idDaRaca, Long idDoDono, List<Long> idDoAtendimento,
			DadosDoAnimal dadosIniciaisDoAnimal) {
		this.nome = nome;
		this.idade = idade;
		this.idDaRaca = idDaRaca;
		this.idDoDono = idDoDono;
		this.idDoAtendimento = idDoAtendimento;
		this.dadosIniciaisDoAnimal = dadosIniciaisDoAnimal;
	}

	public List<Long> getIdDoAtendimento() {
		return idDoAtendimento;
	}
	public void setIdDoAtendimento(List<Long> idDoAtendimento) {
		this.idDoAtendimento = idDoAtendimento;
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
	public Long getIdDaRaca() {
		return idDaRaca;
	}
	public void setIdDaRaca(Long idDaRaca) {
		this.idDaRaca = idDaRaca;
	}
	public DadosDoAnimal getDadosIniciaisDoAnimal() {
		return dadosIniciaisDoAnimal;
	}
	public void setDadosIniciaisDoAnimal(DadosDoAnimal dadosIniciaisDoAnimal) {
		this.dadosIniciaisDoAnimal = dadosIniciaisDoAnimal;
	}
	public Long getIdDoDono() {
		return idDoDono;
	}
	public void setIdDoDono(Long idDoDono) {
		this.idDoDono = idDoDono;
	}
}
