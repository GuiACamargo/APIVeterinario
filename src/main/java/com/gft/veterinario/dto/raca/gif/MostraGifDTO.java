package com.gft.veterinario.dto.raca.gif;

public class MostraGifDTO {

	private String id;
	private String url;
	
	public MostraGifDTO() {
	}
	public MostraGifDTO(String id, String url) {
		this.id = id;
		this.url = url;
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
