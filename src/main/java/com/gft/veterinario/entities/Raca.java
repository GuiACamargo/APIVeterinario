package com.gft.veterinario.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Raca {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String expectativaDeVida;
	private String temperamento;
	@OneToMany(mappedBy = "raca", cascade = CascadeType.MERGE)
	@JsonManagedReference
	private List<Animal> animais;
		
	public Raca() {
	}
	public Raca(Long id, String nome, String expectativaDeVida, String temperamento, List<Animal> animais) {
		this.id = id;
		this.nome = nome;
		this.expectativaDeVida = expectativaDeVida;
		this.temperamento = temperamento;
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
	public List<Animal> getAnimais() {
		return animais;
	}
	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}	
}
