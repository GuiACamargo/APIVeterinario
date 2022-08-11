package com.gft.veterinario.entities;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Atendimento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Data não pode ficar em branco!")
	@NotEmpty(message = "Data deve possuir no mínimo um caractere!")
	@Pattern(regexp = "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|"
			+ "(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)", message = "A data deve seguir o padrão dd/mm/aaaa, com ano entre 1900 e 9999")
	private String data;
	@NotNull(message = "Hora não pode ficar em branco!")
	@NotEmpty(message = "Hora deve possuir no mínimo um caractere!")
	@Pattern(regexp = "^([0-1][0-9]|[2][0-3]):([0-5][0-9])$", message = "A hora deve seguir o padrão hh:mm (não é aceito 24:00, apenas 00:00)")
	private String hora;
	@NotNull(message = "Diagnóstico não pode ficar em branco!")
	@NotEmpty(message = "Diagnóstico deve possuir no mínimo um caractere!")
	private String diagnostico;
	@NotNull(message = "Comentário não pode ficar em branco!")
	@NotEmpty(message = "Comentário deve possuir no mínimo um caractere!")
	private String comentarios;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "veterinario_id")
	@JsonBackReference
	private Veterinario veterinario;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "cliente_id")
	@JsonBackReference
	private Cliente cliente;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "animal_id")
	@JsonBackReference
	private Animal animal;
	@Embedded
	@Valid
	private DadosDoAnimal dadosDoAnimal;
	private String nomeVeterinario;
	private String nomeCliente;
	private String nomeAnimal;

	public Atendimento() {
	}
	public Atendimento(Long id, String data, String hora, String diagnostico, String comentarios,
			Veterinario veterinario, Cliente cliente, Animal animal, DadosDoAnimal dadosDoAnimal) {
		this.id = id;
		this.data = data;
		this.hora = hora;
		this.diagnostico = diagnostico;
		this.comentarios = comentarios;
		this.veterinario = veterinario;
		this.cliente = cliente;
		this.animal = animal;
		this.dadosDoAnimal = dadosDoAnimal;

	}

	public Atendimento(Long id, String data, String hora, String diagnostico, String comentarios,
			Veterinario veterinario, Cliente cliente, Animal animal, DadosDoAnimal dadosDoAnimal,
			String nomeVeterinario, String nomeCliente, String nomeAnimal) {
		this.id = id;
		this.data = data;
		this.hora = hora;
		this.diagnostico = diagnostico;
		this.comentarios = comentarios;
		this.veterinario = veterinario;
		this.cliente = cliente;
		this.animal = animal;
		this.dadosDoAnimal = dadosDoAnimal;
		this.nomeVeterinario = nomeVeterinario;
		this.nomeCliente = nomeCliente;
		this.nomeAnimal = nomeAnimal;
	}
	public String getNomeVeterinario() {
		return nomeVeterinario;
	}
	public void setNomeVeterinario(String nomeVeterinario) {
		this.nomeVeterinario = nomeVeterinario;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public DadosDoAnimal getDadosDoAnimal() {
		return dadosDoAnimal;
	}
	public void setDadosDoAnimal(DadosDoAnimal dadosDoAnimal) {
		this.dadosDoAnimal = dadosDoAnimal;
	}
}
