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

import com.gft.veterinario.dto.animal.AnimalMapper;
import com.gft.veterinario.dto.animal.ConsultaAnimalDTO;
import com.gft.veterinario.dto.animal.RegistroAnimalDTO;
import com.gft.veterinario.entities.Animal;
import com.gft.veterinario.entities.Atendimento;
import com.gft.veterinario.entities.Cliente;
import com.gft.veterinario.entities.DadosDoAnimal;
import com.gft.veterinario.services.AnimalService;
import com.gft.veterinario.services.ClienteService;

@SpringBootTest
class AnimalControllerTest {
	
	private static final String NOME_ANIMAL = "Bolt";
	private static final long ID2 = 2L;
	private static final int INDEX_ZERO = 0;
	private static final long ID = 1L;
	private static final String NOME_CLIENTE = "Guilherme";
	private static final String NOME_VETERINARIO = "José";
	private static final String CPF = "859.111.510-49";
	
	@InjectMocks
	private AnimalController animalController;
	
	@Mock
	private ClienteService clienteService;
	
	@Mock
	private AnimalService animalService;
	
	@Mock
	private AnimalMapper animalMapper;
	
	private Animal animal;
	private Animal animalNovo;
	private DadosDoAnimal d1;
	private DadosDoAnimal d2;
	private Atendimento atendimento;
	private Atendimento atendimentoNovo;
	private Cliente cliente;
	private Cliente clienteNovo;
	private Pageable pageable;
	private RegistroAnimalDTO dto;
	private RegistroAnimalDTO dtoNovo;
	private List<Atendimento> listAtendimento = new ArrayList<>();
	private List<Long> idsAtendimento = new ArrayList<>();
	private List<Atendimento> listAtendimentoNovo = new ArrayList<>();
	private List<Long> idsAtendimentoNovo = new ArrayList<>();
	private List<Animal> listAnimal = new ArrayList<>();
	private List<Animal> listAnimalNovo = new ArrayList<>();
	private Page<Animal> pageAnimais;


	@BeforeEach
	void setUp() throws Exception {
		startAll();
	}
	
	private void startAll() {
		d1 = new DadosDoAnimal(10, 11, 13);
		d2 = new DadosDoAnimal(15, 16, 17);
		
		atendimento = new Atendimento(ID, "26/03", "16:08", "Saúdavel", "Ótimo", null, null, null, d1, NOME_VETERINARIO, NOME_CLIENTE, NOME_ANIMAL);
		atendimentoNovo = new Atendimento(ID2, "25/03", "15:08", "Bom", "Bom", null, null, null, d1, NOME_VETERINARIO, NOME_CLIENTE, NOME_ANIMAL);
		listAtendimento.add(atendimento);
		listAtendimentoNovo.add(atendimentoNovo);
		idsAtendimento.add(atendimento.getId());
		idsAtendimentoNovo.add(atendimentoNovo.getId());
		
		cliente = new Cliente(ID, NOME_CLIENTE, CPF, null, null);
		clienteNovo = new Cliente(ID2, "Pablo", "479.988.140-04", listAtendimentoNovo, listAnimalNovo);

		animal = new Animal(ID, NOME_ANIMAL, 11, null, cliente, listAtendimento, d1);
		animalNovo = new Animal(ID2, "Dog", 15, null, clienteNovo, listAtendimentoNovo, d2);
		listAnimal.add(animal);
		listAnimalNovo.add(animalNovo);
		
		dto = new RegistroAnimalDTO(NOME_ANIMAL, 11, null, ID, idsAtendimento, d1);
		dtoNovo = new RegistroAnimalDTO("Dog", 15, null, ID2, idsAtendimentoNovo, d2);
		
		pageable = PageRequest.of(0, 10);
		pageAnimais = new PageImpl<>(List.of(animal), pageable, 10);
	}

	@Test
	void quandoBuscarTodosOsAnimiasRetornarPageDeConsultaAnimalDTO() {
		when(animalService.listarTodoOsAnimais(pageable)).thenReturn(pageAnimais);
		
		ResponseEntity<Page<ConsultaAnimalDTO>> response = animalController.buscarTodosOsAnimais(pageable);
	
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(PageImpl.class, response.getBody().getClass()),
				  () -> assertEquals(ConsultaAnimalDTO.class, response.getBody().getContent().get(INDEX_ZERO).getClass()),
				  () -> assertEquals(ID, response.getBody().getContent().get(INDEX_ZERO).getId()),
				  () -> assertEquals(NOME_ANIMAL, response.getBody().getContent().get(INDEX_ZERO).getNome()),
				  () -> assertEquals(listAtendimento, response.getBody().getContent().get(INDEX_ZERO).getAtendimentos()),
				  () -> assertEquals(cliente.getNome(), response.getBody().getContent().get(INDEX_ZERO).getNomeDoDono()));
	}

	@Test
	void quandoBuscarAnimalRetornarConsultaAnimalDTO() {
		when(animalService.buscarAnimal(anyLong())).thenReturn(animal);
		
		ResponseEntity<ConsultaAnimalDTO> response = animalController.buscarAnimal(ID);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaAnimalDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID, response.getBody().getId()),
				  () -> assertEquals(NOME_ANIMAL, response.getBody().getNome()),
				  () -> assertEquals(listAtendimento, response.getBody().getAtendimentos()),
				  () -> assertEquals(cliente.getNome(), response.getBody().getNomeDoDono()));
	}

	@Test
	void quandoBuscarAnimaisDoClientePorCpfRetornarListaDeAnimal() {
		when(clienteService.buscarAnimaisDoClientePorCpf(CPF)).thenReturn(listAnimal);
		
		ResponseEntity<List<Animal>> response = animalController.buscarAnimaisDoClientePorCpf(CPF);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ArrayList.class, response.getBody().getClass()),
				  () -> assertEquals(animal, response.getBody().get(INDEX_ZERO)));
	}

	@Test
	void quandoSalvarAnimalRetornarSucessoEConsultaAnimalDTO() {
		when(animalService.criaAnimalComDonoAtendimentoRaca(dto)).thenReturn(animal);
		when(animalService.salvarAnimal(animal)).thenReturn(animal);
		
		ResponseEntity<ConsultaAnimalDTO> response = animalController.salvarAnimal(dto);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaAnimalDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID, response.getBody().getId()),
				  () -> assertEquals(NOME_ANIMAL, response.getBody().getNome()),
				  () -> assertEquals(listAtendimento, response.getBody().getAtendimentos()),
				  () -> assertEquals(cliente.getNome(), response.getBody().getNomeDoDono()));
	}

	@Test
	void quandoAlterarAnimalRetornarSucessoEConsultaAnimalDTO() {
		when(animalService.alteraAnimal(dtoNovo, ID)).thenReturn(animalNovo);
		
		ResponseEntity<ConsultaAnimalDTO> response = animalController.alterarAnimal(dtoNovo, ID);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaAnimalDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID2, response.getBody().getId()),
				  () -> assertEquals("Dog", response.getBody().getNome()),
				  () -> assertEquals(listAtendimentoNovo, response.getBody().getAtendimentos()),
				  () -> assertEquals(clienteNovo.getNome(), response.getBody().getNomeDoDono()));
	}

	@Test
	void quandoExcluirAnimalRetornarSucesso() {
		doNothing().when(animalService).excluirAnimal(ID);
		
		ResponseEntity<ConsultaAnimalDTO> response = animalController.excluirAnimal(ID);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> verify(animalService, times(1)).excluirAnimal(anyLong()));
	}

}
