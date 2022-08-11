package com.gft.veterinario.dto.raca.imagem;

import java.util.List;

public class MostraImagemDTO {
	
	private List<RacaDaMostraImagemDTO> breeds;
	private String id;
	private String url;
	
	public MostraImagemDTO() {
	}
	public MostraImagemDTO(List<RacaDaMostraImagemDTO> breeds, String id, String url) {
		this.breeds = breeds;
		this.id = id;
		this.url = url;
	}

	public List<RacaDaMostraImagemDTO> getBreeds() {
		return breeds;
	}
	public void setBreeds(List<RacaDaMostraImagemDTO> breeds) {
		this.breeds = breeds;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
