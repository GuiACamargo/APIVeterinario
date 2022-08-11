package com.gft.veterinario.dto.cliente;

import java.util.ArrayList;
import java.util.List;

import com.gft.veterinario.entities.Cliente;

public class ClienteMapper {
	
	public static Cliente fromDTO(RegistroClienteDTO dto) {
		return new Cliente(null, dto.getNome(), dto.getCpf(), null, null);
	}
	
	public static Cliente fromDTOComSenha(RegistroClienteComSenhaDTO dto) {
		return new Cliente(null, dto.getNome(), dto.getCpfUsadoParaLogin(), null, null);
	}
	
	public static ConsultaClienteDTO fromEntity(Cliente cliente) {
		List<String> animais = new ArrayList<>();
		if(cliente.getAnimais() != null) {
			cliente.getAnimais().forEach(animalVar -> {
				String animalNome = animalVar.getNome();
				animais.add(animalNome);
			});
		}
		return new ConsultaClienteDTO(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getAtendimentos(), animais);
	}

}
