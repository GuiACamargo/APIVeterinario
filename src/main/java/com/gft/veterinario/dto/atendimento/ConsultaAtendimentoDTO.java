package com.gft.veterinario.dto.atendimento;

import com.gft.veterinario.entities.Animal;
import com.gft.veterinario.entities.Cliente;
import com.gft.veterinario.entities.DadosDoAnimal;
import com.gft.veterinario.entities.Veterinario;

public class ConsultaAtendimentoDTO {
	
	private Long id;
	private Veterinario veterinario;
	private Cliente cliente;
	private Animal animal;
	private String data;
	private String hora;
	private String diagonistico;
	private String comentarios;
	private DadosDoAnimal dadosDoAnimal;
	
	public ConsultaAtendimentoDTO() {
	}
	
	
	public ConsultaAtendimentoDTO(Long id, Veterinario veterinario, Cliente cliente, Animal animal, String data, String hora,
			String diagonistico, String comentarios, DadosDoAnimal dadosDoAnimal) {
		this.id = id;
		this.veterinario = veterinario;
		this.cliente = cliente;
		this.animal = animal;
		this.data = data;
		this.hora = hora;
		this.diagonistico = diagonistico;
		this.comentarios = comentarios;
		this.dadosDoAnimal = dadosDoAnimal;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Veterinario getVeterinario() {
		return veterinario;
	}
	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Animal getAnimal() {
		return animal;
	}
	public void setAnimal(Animal animal) {
		this.animal = animal;
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
	public String getDiagonistico() {
		return diagonistico;
	}
	public void setDiagonistico(String diagonistico) {
		this.diagonistico = diagonistico;
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
