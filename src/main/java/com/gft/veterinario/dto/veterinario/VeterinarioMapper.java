package com.gft.veterinario.dto.veterinario;

import com.gft.veterinario.entities.Veterinario;

public class VeterinarioMapper {

	public static Veterinario fromDTO (RegistroVeterinarioDTO dto) {
		return new Veterinario(null, dto.getNome(), dto.getCpf(), dto.getEspecialidade(), null);
	}
	
	public static Veterinario fromDTOComSenha (RegistroVeterinarioComSenhaDTO dto) {
		return new Veterinario(null, dto.getNome(), dto.getCpfUsadoParaLogin(), dto.getEspecialidade(), null);
	}
	
	public static ConsultaVeterinarioDTO fromEntity (Veterinario veterinario) {
		return new ConsultaVeterinarioDTO(veterinario.getId(), veterinario.getNome(), veterinario.getCpf(), veterinario.getEspecialidade(), veterinario.getAtendimentos());
	}
	
}
