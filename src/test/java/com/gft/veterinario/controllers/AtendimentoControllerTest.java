package com.gft.veterinario.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gft.veterinario.dto.atendimento.AtendimentoMapper;
import com.gft.veterinario.dto.atendimento.ConsultaAtendimentoDTO;
import com.gft.veterinario.dto.atendimento.RegistroAtendimentoDTO;
import com.gft.veterinario.entities.Animal;
import com.gft.veterinario.entities.Atendimento;
import com.gft.veterinario.entities.Cliente;
import com.gft.veterinario.entities.DadosDoAnimal;
import com.gft.veterinario.entities.Veterinario;
import com.gft.veterinario.services.AtendimentoService;
import com.gft.veterinario.services.ClienteService;

@SpringBootTest
class AtendimentoControllerTest {
	
	private static final String NOME_ANIMAL = "Bolt";
	private static final long ID2 = 2L;
	private static final int INDEX_ZERO = 0;
	private static final long ID = 1L;
	private static final String NOME_CLIENTE = "Guilherme";
	private static final String NOME_VETERINARIO = "José";
	private static final String CPF = "859.111.510-49";
	
	@InjectMocks
	private AtendimentoController atendimentoController;
	
	@Mock
	private AtendimentoService atendimentoService;
	
	@Mock
	private ClienteService clienteService;
	
	@Mock
	private AtendimentoMapper atendimentoMapper;
	
	private Veterinario veterinario;
	private Veterinario veterinarioNovo;
	private Cliente cliente;
	private Cliente clienteNovo;
	private DadosDoAnimal d1;
	private DadosDoAnimal d2;
	private Atendimento atendimento;
	private Atendimento atendimentoNovo;
	private Animal animal;
	private Animal animalNovo;
	private Pageable pageable;
	private RegistroAtendimentoDTO dto;
	private RegistroAtendimentoDTO dtoNovo;
	private List<Animal> listAnimal = new ArrayList<>();
	private List<Animal> listAnimalNovo = new ArrayList<>();
	private List<Atendimento> listAtendimento = new ArrayList<>();
	private List<Atendimento> listAtendimentoNovo = new ArrayList<>();
	private List<Long> idsAtendimento = new ArrayList<>();
	private List<Long> idsAtendimentoNovo = new ArrayList<>();
	private Page<Atendimento> pageAtendimentos;

	@BeforeEach
	void setUp() throws Exception {
		startAll();
	}

	private void startAll() {
		d1 = new DadosDoAnimal(10, 11, 13);
		d2 = new DadosDoAnimal(15, 16, 17);

		
		cliente = new Cliente(ID, NOME_CLIENTE, CPF, null, null);
		clienteNovo = new Cliente(ID2, "Pablo", "479.988.140-04", listAtendimentoNovo, listAnimalNovo);
		
		veterinario = new Veterinario(ID, NOME_VETERINARIO, CPF, "Cachorro", listAtendimento);
		veterinarioNovo = new Veterinario(ID2, "Pablo", "479.988.140-04", "Qualquer", listAtendimentoNovo);
		
		animal = new Animal(ID, NOME_ANIMAL, 11, null, cliente, listAtendimento, d1);
		animalNovo = new Animal(ID2, "Dog", 15, null, clienteNovo, listAtendimentoNovo, d2);
		listAnimal.add(animal);
		listAnimalNovo.add(animalNovo);
		
		atendimento = new Atendimento(ID, "26/03", "16:08", "Saúdavel", "Ótimo", veterinario, cliente, animal, d1, NOME_VETERINARIO, NOME_CLIENTE, NOME_ANIMAL);
		atendimentoNovo = new Atendimento(ID2, "25/03", "15:08", "Bom", "Bom", veterinarioNovo, clienteNovo, animalNovo, d1, NOME_VETERINARIO, NOME_CLIENTE, NOME_ANIMAL);
		listAtendimento.add(atendimento);
		listAtendimentoNovo.add(atendimentoNovo);
		idsAtendimento.add(atendimento.getId());
		idsAtendimentoNovo.add(atendimentoNovo.getId());
		
		dto = new RegistroAtendimentoDTO(ID, ID, "26/03", "16:08", "Saúdavel", "Ótimo", d1);
		dtoNovo = new RegistroAtendimentoDTO(ID2, ID2, "25/03", "15:08", "Bom", "Bom", d2);
		
		pageable = PageRequest.of(0, 10);
		pageAtendimentos = new PageImpl<>(List.of(atendimento), pageable, 10);
	}
	
	@Test
	void quandoBuscarTodosOsAtendimentosRetornarPageDeConsultaAtendimentoDTO() {
		when(atendimentoService.listarTodosOsAtendimentos(pageable)).thenReturn(pageAtendimentos);
		
		ResponseEntity<Page<ConsultaAtendimentoDTO>> response = atendimentoController.buscarTodosOsAtendimentos(pageable);
	
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(PageImpl.class, response.getBody().getClass()),
				  () -> assertEquals(ConsultaAtendimentoDTO.class, response.getBody().getContent().get(INDEX_ZERO).getClass()),
				  () -> assertEquals(animal, response.getBody().getContent().get(INDEX_ZERO).getAnimal()),
				  () -> assertEquals(veterinario, response.getBody().getContent().get(INDEX_ZERO).getVeterinario()),
				  () -> assertEquals(cliente, response.getBody().getContent().get(INDEX_ZERO).getCliente()),
				  () -> assertEquals(ID, response.getBody().getContent().get(INDEX_ZERO).getId()));	
	}

	@Test
	void quandoBuscarAtendimentoRetornarConsultaAtendimentoDTO() {
		when(atendimentoService.buscarAtendimento(ID)).thenReturn(atendimento);
		
		ResponseEntity<ConsultaAtendimentoDTO> response = atendimentoController.buscarAtendimento(ID);
	
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaAtendimentoDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID, response.getBody().getId()),
				  () -> assertEquals(veterinario, response.getBody().getVeterinario()),
				  () -> assertEquals(cliente, response.getBody().getCliente()),
				  () -> assertEquals(animal, response.getBody().getAnimal()));
	}

	@Test
	void quandoBuscarAtendimentosDoClientePorCpfRetornarListDeAtendimento() {
		when(clienteService.buscarAtendimentosDoClientePorCpf(CPF)).thenReturn(listAtendimento);
		
		ResponseEntity<List<Atendimento>> response = atendimentoController.buscarAtendimentosDoClientePorCpf(CPF);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ArrayList.class, response.getBody().getClass()),
				  () -> assertEquals(atendimento, response.getBody().get(INDEX_ZERO)));
	}

	@Test
	void quandoSalvarAtendimentoRetornarSucessoEConsultaAtendimentoDTO() {
		when(atendimentoService.criaAtendimentoComAnimalClienteVeterinario(dto)).thenReturn(atendimento);
		when(atendimentoService.salvarAtendimento(atendimento)).thenReturn(atendimento);
		
		ResponseEntity<ConsultaAtendimentoDTO> response = atendimentoController.salvarAtendimento(dto);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaAtendimentoDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID, response.getBody().getId()),
				  () -> assertEquals(veterinario, response.getBody().getVeterinario()),
				  () -> assertEquals(cliente, response.getBody().getCliente()),
				  () -> assertEquals(animal, response.getBody().getAnimal()));
	}

	@Test
	void quandoAlterarAtendimentoRetornarSucessoEConsultaAtendimentoDTO() {
		when(atendimentoService.alteraAtendimento(dtoNovo, ID)).thenReturn(atendimentoNovo);
		
		ResponseEntity<ConsultaAtendimentoDTO> response = atendimentoController.alterarAtendimento(dtoNovo, ID);

		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaAtendimentoDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID2, response.getBody().getId()),
				  () -> assertEquals(veterinarioNovo, response.getBody().getVeterinario()),
				  () -> assertEquals(clienteNovo, response.getBody().getCliente()),
				  () -> assertEquals(animalNovo, response.getBody().getAnimal()));
	}

	@Test
	void quandoExcluirAtendimentoRetornarSucesso() {
		doNothing().when(atendimentoService).excluirAtendimento(ID);
		
		ResponseEntity<ConsultaAtendimentoDTO> response = atendimentoController.excluirAtendimento(ID);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> verify(atendimentoService, times(1)).excluirAtendimento(anyLong()));
	}

}
