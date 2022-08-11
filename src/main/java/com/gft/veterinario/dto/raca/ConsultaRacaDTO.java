package com.gft.veterinario.dto.raca;

import java.util.List;

public class ConsultaRacaDTO {
	
	private Long id;
	private String nome;
	private String expectativaDeVida;
	private String temperamento;
	private List<String> nomeAnimais;
	
	public ConsultaRacaDTO() {
	}
	public ConsultaRacaDTO(Long id, String nome, String expectativaDeVida, String temperamento,
			List<String> nomeAnimais) {
		this.id = id;
		this.nome = nome;
		this.expectativaDeVida = expectativaDeVida;
		this.temperamento = temperamento;
		this.nomeAnimais = nomeAnimais;
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
	public String getExpectativaDeVida() {
		return expectativaDeVida;
	}
	public void setExpectativaDeVida(String expectativaDeVida) {
		this.expectativaDeVida = expectativaDeVida;
	}
	public String getTemperamento() {
		return temperamento;
	}
	public void setTemperamento(String temperamento) {
		this.temperamento = temperamento;
	}
	public List<String> getNomeAnimais() {
		return nomeAnimais;
	}
	public void setNomeAnimais(List<String> nomeAnimais) {
		this.nomeAnimais = nomeAnimais;
	}
}
