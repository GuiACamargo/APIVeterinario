package com.gft.veterinario.dto.raca.dados;

public class MostraRacaComImagemDTO {
	private Long id;
	private String name;
	private String life_span;
	private String temperament;
	private ImagemDaMostraRacaComImagemDTO image;
	
	public MostraRacaComImagemDTO(Long id, String name, String life_span, String temperament, ImagemDaMostraRacaComImagemDTO image) {
		this.id = id;
		this.name = name;
		this.life_span = life_span;
		this.temperament = temperament;
		this.image = image;
	}
	public MostraRacaComImagemDTO() {
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
	public ImagemDaMostraRacaComImagemDTO getImage() {
		return image;
	}
	public void setImage(ImagemDaMostraRacaComImagemDTO image) {
		this.image = image;
	}
}
