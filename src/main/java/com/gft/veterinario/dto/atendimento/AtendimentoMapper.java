package com.gft.veterinario.dto.atendimento;

import com.gft.veterinario.entities.Atendimento;

public class AtendimentoMapper {

	public static Atendimento fromDTO (RegistroAtendimentoDTO dto) {
		return new Atendimento(null, dto.getData(), dto.getHora(), dto.getDiagnostico(), 
				dto.getComentarios(), null, null, null, dto.getDadosDoAnimal());
	}
	
	public static ConsultaAtendimentoDTO fromEntity (Atendimento atendimento) {
		
		return new ConsultaAtendimentoDTO(atendimento.getId(), atendimento.getVeterinario(), atendimento.getCliente(), 
				atendimento.getAnimal(), atendimento.getData(), atendimento.getHora(), atendimento.getDiagnostico(), 
				atendimento.getComentarios(), atendimento.getDadosDoAnimal());
	}
	
}
