package com.gft.veterinario.dto.cliente;

import java.util.List;

public class RegistroClienteDTO {
	
	private String nome;
	private String cpf;
	private List<Long> atedimentosId;
	private List<Long> idDoAnimal;
	
	public RegistroClienteDTO() {
	}
	public RegistroClienteDTO(String nome, String cpf, List<Long> atedimentosId,
			List<Long> idDoAnimal) {

		this.nome = nome;
		this.cpf = cpf;
		this.atedimentosId = atedimentosId;
		this.idDoAnimal = idDoAnimal;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public List<Long> getAtedimentosId() {
		return atedimentosId;
	}
	public void setAtedimentosId(List<Long> atedimentosId) {
		this.atedimentosId = atedimentosId;
	}
	public List<Long> getIdDoAnimal() {
		return idDoAnimal;
	}
	public void setIdDoAnimal(List<Long> idDoAnimal) {
		this.idDoAnimal = idDoAnimal;
	}
}
