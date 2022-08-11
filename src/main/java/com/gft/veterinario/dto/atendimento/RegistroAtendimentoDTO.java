package com.gft.veterinario.dto.atendimento;

import com.gft.veterinario.entities.DadosDoAnimal;

public class RegistroAtendimentoDTO {
	
	private Long idVeterinario;
	private Long idAnimal;
	private String data;
	private String hora;
	private String diagnostico;
	private String comentarios;
	private DadosDoAnimal dadosDoAnimal;
	
	public RegistroAtendimentoDTO() {
	}
	public RegistroAtendimentoDTO(Long idVeterinario, Long idAnimal, String data, String hora,
			String diagnostico, String comentarios, DadosDoAnimal dadosDoAnimal) {
		this.idVeterinario = idVeterinario;
		this.idAnimal = idAnimal;
		this.data = data;
		this.hora = hora;
		this.diagnostico = diagnostico;
		this.comentarios = comentarios;
		this.dadosDoAnimal = dadosDoAnimal;
	}
	
	public Long getIdVeterinario() {
		return idVeterinario;
	}
	public void setIdVeterinario(Long idVeterinario) {
		this.idVeterinario = idVeterinario;
	}
	public Long getIdAnimal() {
		return idAnimal;
	}
	public void setIdAnimal(Long idAnimal) {
		this.idAnimal = idAnimal;
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
}
