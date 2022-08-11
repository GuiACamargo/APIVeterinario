package com.gft.veterinario.dto.raca;

import com.gft.veterinario.entities.Raca;

public class RacaMapper {
	
	public static Raca fromDTO (RegistroRacaDTO dto) {
		return new Raca (null, dto.getName(), dto.getLife_span(), dto.getTemperament(), null);
	}
	
	public static ConsultaRacaDTO fromEntity (Raca raca) {
		return new ConsultaRacaDTO(raca.getId(), raca.getNome(), raca.getExpectativaDeVida(), raca.getTemperamento(), null);
	}

}
