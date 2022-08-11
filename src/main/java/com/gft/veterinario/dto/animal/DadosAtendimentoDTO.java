package com.gft.veterinario.dto.animal;

import com.gft.veterinario.entities.DadosDoAnimal;

public class DadosAtendimentoDTO {

	private String data;
	private String hora;
	private String diagnostico;
	private String comentarios;
	private DadosDoAnimal dadosDoAnimal;
	private String CpfDoVeterinario;
	private String nomeVeterinario;
	private String CpfDoCliente;
	private String nomeCliente;
	private String nomeAnimal;
	
	public DadosAtendimentoDTO() {
	}
	public DadosAtendimentoDTO(String data, String hora, String diagnostico, String comentarios,
			DadosDoAnimal dadosDoAnimal, String cpfDoVeterinario, String nomeVeterinario, String cpfDoCliente,
			String nomeCliente, String nomeAnimal) {
		this.data = data;
		this.hora = hora;
		this.diagnostico = diagnostico;
		this.comentarios = comentarios;
		this.dadosDoAnimal = dadosDoAnimal;
		CpfDoVeterinario = cpfDoVeterinario;
		this.nomeVeterinario = nomeVeterinario;
		CpfDoCliente = cpfDoCliente;
		this.nomeCliente = nomeCliente;
		this.nomeAnimal = nomeAnimal;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getDiagnostico() {
		return diagnostico;
	}
	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public DadosDoAnimal getDadosDoAnimal() {
		return dadosDoAnimal;
	}
	public void setDadosDoAnimal(DadosDoAnimal dadosDoAnimal) {
		this.dadosDoAnimal = dadosDoAnimal;
	}
	public String getCpfDoVeterinario() {
		return CpfDoVeterinario;
	}
	public void setCpfDoVeterinario(String cpfDoVeterinario) {
		CpfDoVeterinario = cpfDoVeterinario;
	}
	public String getNomeVeterinario() {
		return nomeVeterinario;
	}
	public void setNomeVeterinario(String nomeVeterinario) {
		this.nomeVeterinario = nomeVeterinario;
	}
	public String getCpfDoCliente() {
		return CpfDoCliente;
	}
	public void setCpfDoCliente(String cpfDoCliente) {
		CpfDoCliente = cpfDoCliente;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getNomeAnimal() {
		return nomeAnimal;
	}
	public void setNomeAnimal(String nomeAnimal) {
		this.nomeAnimal = nomeAnimal;
	}
}
