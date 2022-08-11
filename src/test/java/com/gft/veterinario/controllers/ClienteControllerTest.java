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

import com.gft.veterinario.dto.cliente.ClienteMapper;
import com.gft.veterinario.dto.cliente.ConsultaClienteDTO;
import com.gft.veterinario.dto.cliente.RegistroClienteComSenhaDTO;
import com.gft.veterinario.dto.cliente.RegistroClienteDTO;
import com.gft.veterinario.entities.Animal;
import com.gft.veterinario.entities.Atendimento;
import com.gft.veterinario.entities.Cliente;
import com.gft.veterinario.entities.DadosDoAnimal;
import com.gft.veterinario.entities.Perfil;
import com.gft.veterinario.entities.Usuario;
import com.gft.veterinario.services.ClienteService;

@SpringBootTest
class ClienteControllerTest {
	
	private static final long ID2 = 2L;
	private static final int INDEX_ZERO = 0;
	private static final long ID = 1L;
	private static final String NOME_CLIENTE = "Guilherme";
	private static final String NOME_VETERINARIO = "José";
	private static final String CPF = "859.111.510-49";
	
	@InjectMocks
	private ClienteController clienteController;
	
	@Mock
	private ClienteMapper clienteMapper;
	
	@Mock
	private ClienteService clienteService;
	
	private Cliente cliente;
	private Cliente clienteComAnimalEAtendimento;
	private Cliente clienteNovo;
	private DadosDoAnimal d1;
	private DadosDoAnimal d2;
	private Atendimento atendimento;
	private Animal animal;
	private Atendimento atendimentoNovo;
	private Animal animalNovo;
	private Usuario usuarioCliente;
	private Perfil perfilCliente;
	private RegistroClienteComSenhaDTO dtoComSenha;
	private RegistroClienteDTO dto;
	private Pageable pageable;
	private List<Animal> listAnimal = new ArrayList<>();
	private List<Atendimento> listAtendimento = new ArrayList<>();
	private List<Long> idsAtendimento = new ArrayList<>();
	private List<Long> idsAnimal = new ArrayList<>();
	private List<Animal> listAnimalNovo = new ArrayList<>();
	private List<Atendimento> listAtendimentoNovo = new ArrayList<>();
	private List<Long> idsAtendimentoNovo = new ArrayList<>();
	private List<Long> idsAnimalNovo = new ArrayList<>();
	private Page<Cliente> pageCliente;
	private List<String> nomeAnimais = new ArrayList<>();
	private List<String> nomeAnimaisNovo = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		startAll();
	}
	
	private void startAll() {
		d1 = new DadosDoAnimal(10, 11, 13);
		d2 = new DadosDoAnimal(15, 16, 17);
				
		atendimento = new Atendimento(ID, "26/03", "16:08", "Saúdavel", "Ótimo", null, null, null, d1, NOME_VETERINARIO, NOME_CLIENTE, "Bolt");
		atendimentoNovo = new Atendimento(ID2, "25/03", "15:08", "Bom", "Bom", null, null, null, d1, NOME_VETERINARIO, NOME_CLIENTE, "Bolt");
		listAtendimento.add(atendimento);
		listAtendimentoNovo.add(atendimentoNovo);
		idsAtendimento.add(atendimento.getId());
		idsAtendimentoNovo.add(atendimentoNovo.getId());

		animal = new Animal(ID, "Bolt", 11, null, null, listAtendimento, d2);
		animalNovo = new Animal(ID2, "Bolt", 15, null, null, listAtendimento, d2);
		listAnimal.add(animal);
		listAnimalNovo.add(animalNovo);
		idsAnimal.add(animal.getId());
		idsAnimalNovo.add(animalNovo.getId());
		
		cliente = new Cliente(ID, NOME_CLIENTE, CPF, null, null);
		clienteComAnimalEAtendimento = new Cliente(ID, NOME_CLIENTE, CPF, listAtendimento, listAnimal);
		clienteNovo = new Cliente(ID2, "Pablo", "479.988.140-04", listAtendimentoNovo, listAnimalNovo);
		perfilCliente = new Perfil(ID2, "cliente");
		usuarioCliente = new Usuario(ID2, cliente.getCpf(), "123", perfilCliente);
		dtoComSenha = new RegistroClienteComSenhaDTO(usuarioCliente.getPassword(), NOME_CLIENTE, cliente.getCpf(), idsAtendimento, idsAnimal);
		dto = new RegistroClienteDTO("Pablo", "479.988.140-04", idsAtendimentoNovo, idsAnimalNovo);
		
		pageable = PageRequest.of(0, 10);
		pageCliente = new PageImpl<>(List.of(cliente), pageable, 10);
		
		listAnimal.forEach(a -> {
			nomeAnimais.add(a.getNome());
		});
		listAnimalNovo.forEach(a -> {
			nomeAnimaisNovo.add(a.getNome());
		});
		
	}

	@Test
	void quandoBuscarClienteRetornarSucesso() {
		cliente.setAtendimentos(listAtendimento);
		cliente.setAnimais(listAnimal);
		when(clienteService.buscarCliente(anyLong())).thenReturn(cliente);
		
		ResponseEntity<ConsultaClienteDTO> response = clienteController.buscarCliente(ID);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaClienteDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID, response.getBody().getId()),
				  () -> assertEquals(NOME_CLIENTE, response.getBody().getNome()),
				  () -> assertEquals(CPF, response.getBody().getCpf()),
				  () -> assertEquals(listAtendimento, response.getBody().getAtendimentos()),
				  () -> assertEquals(nomeAnimais, response.getBody().getAnimais()));
	}

	@Test
	void quandoBuscarTodosOsClienteRetornarPageDeConsultaClienteDTO() {
		cliente.setAtendimentos(listAtendimento);
		cliente.setAnimais(listAnimal);
		when(clienteService.buscarTodosOsClientes(pageable)).thenReturn(pageCliente);
		
		ResponseEntity<Page<ConsultaClienteDTO>> response = clienteController.buscarTodosOsClientes(pageable);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(PageImpl.class, response.getBody().getClass()),
				  () -> assertEquals(ConsultaClienteDTO.class, response.getBody().getContent().get(INDEX_ZERO).getClass()),
				  () -> assertEquals(ID, response.getBody().getContent().get(INDEX_ZERO).getId()),
				  () -> assertEquals(NOME_CLIENTE, response.getBody().getContent().get(INDEX_ZERO).getNome()),
				  () -> assertEquals(CPF, response.getBody().getContent().get(INDEX_ZERO).getCpf()),
				  () -> assertEquals(listAtendimento, response.getBody().getContent().get(INDEX_ZERO).getAtendimentos()),
				  () -> assertEquals(nomeAnimais, response.getBody().getContent().get(INDEX_ZERO).getAnimais()));
	}

	@Test
	void quandoSalvarClienteRetornarSucesso() {
		when(clienteService.criaClienteComAnimalAtendimento(dtoComSenha)).thenReturn(clienteComAnimalEAtendimento);
		
		ResponseEntity<ConsultaClienteDTO> response = clienteController.salvarCliente(dtoComSenha);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaClienteDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID, response.getBody().getId()),
				  () -> assertEquals(NOME_CLIENTE, response.getBody().getNome()),
				  () -> assertEquals(CPF, response.getBody().getCpf()),
				  () -> assertEquals(listAtendimento, response.getBody().getAtendimentos()),
				  () -> assertEquals(nomeAnimais, response.getBody().getAnimais()));
	}
	
	@Test
	void quandoAlterarClienteRetornarClienteNovo() {
		when(clienteService.alteraCliente(dto, 1L)).thenReturn(clienteNovo);
		
		ResponseEntity<ConsultaClienteDTO> response = clienteController.alterarCliente(dto, 1L);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaClienteDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID2, response.getBody().getId()),
				  () -> assertEquals("Pablo", response.getBody().getNome()),
				  () -> assertEquals("479.988.140-04", response.getBody().getCpf()),
				  () -> assertEquals(listAtendimentoNovo, response.getBody().getAtendimentos()),
				  () -> assertEquals(nomeAnimaisNovo, response.getBody().getAnimais()));
	}

	@Test
	void quandoBuscarClientePorCpfRetornarConsultaClienteDTO() {
		cliente.setAtendimentos(listAtendimento);
		cliente.setAnimais(listAnimal);
		when(clienteService.buscarClientePeloCpf(CPF)).thenReturn(cliente);
		
		ResponseEntity<ConsultaClienteDTO> response = clienteController.buscarClientePorCpf(CPF);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaClienteDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID, response.getBody().getId()),
				  () -> assertEquals(NOME_CLIENTE, response.getBody().getNome()),
				  () -> assertEquals(CPF, response.getBody().getCpf()),
				  () -> assertEquals(listAtendimento, response.getBody().getAtendimentos()),
				  () -> assertEquals(nomeAnimais, response.getBody().getAnimais()));
	}

	@Test
	void quandoExcluirClientePorIdRetornarSucesso() {
		doNothing().when(clienteService).deletarCliente(ID);
		
		ResponseEntity<ConsultaClienteDTO> response = clienteController.excluirClientePorId(ID);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> verify(clienteService, times(1)).deletarCliente(anyLong()));
	}

}
