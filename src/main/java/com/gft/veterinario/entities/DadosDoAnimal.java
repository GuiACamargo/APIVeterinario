package com.gft.veterinario.entities;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Embeddable
public class DadosDoAnimal {
	
	@NotNull(message = "Peso não pode ficar em branco!")
	@PositiveOrZero(message = "Peso deve ser 0 ou Positivo!")
	private double pesoKg;
	@NotNull(message = "Altura não pode ficar em branco!")
	@PositiveOrZero(message = "Altura deve ser 0 ou Positivo!")
	private double alturaCm;
	@NotNull(message = "Largura não pode ficar em branco!")
	@PositiveOrZero(message = "Largura deve ser 0 ou Positivo!")
	private double larguraCm;
	
	public DadosDoAnimal(double pesoKg, double alturaCm, double larguraCm) {
		this.pesoKg = pesoKg;
		this.alturaCm = alturaCm;
		this.larguraCm = larguraCm;
	}
	public DadosDoAnimal() {
	}
	
	public double getPesoKg() {
		return pesoKg;
	}
	public void setPesoKg(double pesoKg) {
		this.pesoKg = pesoKg;
	}
	public double getAlturaCm() {
		return alturaCm;
	}
	public void setAlturaCm(double alturaCm) {
		this.alturaCm = alturaCm;
	}
	public double getLarguraCm() {
		return larguraCm;
	}
	public void setLarguraCm(double larguraCm) {
		this.larguraCm = larguraCm;
	}
}
