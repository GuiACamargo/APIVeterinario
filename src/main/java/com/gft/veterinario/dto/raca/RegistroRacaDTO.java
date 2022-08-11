package com.gft.veterinario.dto.raca;

public class RegistroRacaDTO {
	
	private Long id;
	private String name;
	private String life_span;
	private String temperament;
	
	public RegistroRacaDTO(Long id, String name, String life_span, String temperament) {
		this.id = id;
		this.name = name;
		this.life_span = life_span;
		this.temperament = temperament;
	}
	public RegistroRacaDTO() {
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLife_span() {
		return life_span;
	}
	public void setLife_span(String life_span) {
		this.life_span = life_span;
	}
	public String getTemperament() {
		return temperament;
	}
	public void setTemperament(String temperament) {
		this.temperament = temperament;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
