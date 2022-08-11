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

import com.gft.veterinario.dto.veterinario.ConsultaVeterinarioDTO;
import com.gft.veterinario.dto.veterinario.RegistroVeterinarioComSenhaDTO;
import com.gft.veterinario.dto.veterinario.RegistroVeterinarioDTO;
import com.gft.veterinario.dto.veterinario.VeterinarioMapper;
import com.gft.veterinario.entities.Animal;
import com.gft.veterinario.entities.Atendimento;
import com.gft.veterinario.entities.Cliente;
import com.gft.veterinario.entities.DadosDoAnimal;
import com.gft.veterinario.entities.Veterinario;
import com.gft.veterinario.services.VeterinarioService;

@SpringBootTest
class VeterinarioControllerTest {
	
	private static final String NOME_ANIMAL = "Bolt";
	private static final long ID2 = 2L;
	private static final int INDEX_ZERO = 0;
	private static final long ID = 1L;
	private static final String NOME_CLIENTE = "Guilherme";
	private static final String NOME_VETERINARIO = "José";
	private static final String CPF = "859.111.510-49";
	
	@InjectMocks
	private VeterinarioController veterinarioController;
	
	@Mock
	private VeterinarioService veterinarioService;
	
	@Mock
	private VeterinarioMapper veterinarioMapper;
	
	private Veterinario veterinario;
	private Veterinario veterinarioNovo;
	private Animal animal;
	private Animal animalNovo;
	private DadosDoAnimal d1;
	private DadosDoAnimal d2;
	private Atendimento atendimento;
	private Atendimento atendimentoNovo;
	private Cliente cliente;
	private Cliente clienteNovo;
	private Pageable pageable;
	private RegistroVeterinarioComSenhaDTO dtoComSenha;
	private RegistroVeterinarioDTO dto;
	private List<Atendimento> listAtendimento = new ArrayList<>();
	private List<Long> idsAtendimento = new ArrayList<>();
	private List<Atendimento> listAtendimentoNovo = new ArrayList<>();
	private List<Long> idsAtendimentoNovo = new ArrayList<>();
	private List<Animal> listAnimal = new ArrayList<>();
	private List<Animal> listAnimalNovo = new ArrayList<>();
	private Page<Veterinario> pageVeterinarios;

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
		
		veterinario = new Veterinario(ID, NOME_VETERINARIO, CPF, "Cachorro", listAtendimento);
		veterinarioNovo = new Veterinario(ID2, "Pablo", "479.988.140-04", "Qualquer", listAtendimentoNovo);
		
		dtoComSenha = new RegistroVeterinarioComSenhaDTO("123", NOME_VETERINARIO, CPF, "Cachorro", idsAtendimento);
		dto = new RegistroVeterinarioDTO("Pablo", "479.988.140-04", "Cachorro", idsAtendimentoNovo);

		animal = new Animal(ID, NOME_ANIMAL, 11, null, cliente, listAtendimento, d1);
		animalNovo = new Animal(ID2, "Dog", 15, null, clienteNovo, listAtendimentoNovo, d2);
		listAnimal.add(animal);
		listAnimalNovo.add(animalNovo);
		
		pageable = PageRequest.of(0, 10);
		pageVeterinarios = new PageImpl<>(List.of(veterinario), pageable, 10);
	}

	@Test
	void quandoBuscarTodosOsVeterinariosRetornarPageDeConsultaVeterinarioDTO() {
		when(veterinarioService.listarTodosOsVeterinarios(pageable)).thenReturn(pageVeterinarios);
	
		ResponseEntity<Page<ConsultaVeterinarioDTO>> response = veterinarioController.buscarTodosOsVeterinarios(pageable);
	
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(PageImpl.class, response.getBody().getClass()),
				  () -> assertEquals(ConsultaVeterinarioDTO.class, response.getBody().getContent().get(INDEX_ZERO).getClass()),
				  () -> assertEquals(ID, response.getBody().getContent().get(INDEX_ZERO).getId()),
				  () -> assertEquals(NOME_VETERINARIO, response.getBody().getContent().get(INDEX_ZERO).getNome()),
				  () -> assertEquals(CPF, response.getBody().getContent().get(INDEX_ZERO).getCpf()),
				  () -> assertEquals(listAtendimento, response.getBody().getContent().get(INDEX_ZERO).getAtendimentos()));	
		}

	@Test
	void quandoBuscarVeterinarioRetornarConsultaVeterinarioDTO() {
		when(veterinarioService.buscarVeterinario(ID)).thenReturn(veterinario);
		
		ResponseEntity<ConsultaVeterinarioDTO> response = veterinarioController.buscarVeterinario(ID);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaVeterinarioDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID, response.getBody().getId()),
				  () -> assertEquals(NOME_VETERINARIO, response.getBody().getNome()),
				  () -> assertEquals(CPF, response.getBody().getCpf()),
				  () -> assertEquals(listAtendimento, response.getBody().getAtendimentos()));
	}

	@Test
	void quandoBuscarVeterinarioPorCpfRetornarConsultaVeterinarioDTO() {
		when(veterinarioService.buscarVeterinarioPeloCpf(CPF)).thenReturn(veterinario);
		
		ResponseEntity<ConsultaVeterinarioDTO> response = veterinarioController.buscarVeterinarioPorCpf(CPF);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaVeterinarioDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID, response.getBody().getId()),
				  () -> assertEquals(NOME_VETERINARIO, response.getBody().getNome()),
				  () -> assertEquals(CPF, response.getBody().getCpf()),
				  () -> assertEquals(listAtendimento, response.getBody().getAtendimentos()));
	}

	@Test
	void quandoSalvarVeterinarioRetornarConsultaVeterinarioDTO() {
		when(veterinarioService.criaVeterinarioComAtendimento(dtoComSenha)).thenReturn(veterinario);
		
		ResponseEntity<ConsultaVeterinarioDTO> response = veterinarioController.salvarVeterinario(dtoComSenha);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaVeterinarioDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID, response.getBody().getId()),
				  () -> assertEquals(NOME_VETERINARIO, response.getBody().getNome()),
				  () -> assertEquals(CPF, response.getBody().getCpf()),
				  () -> assertEquals(listAtendimento, response.getBody().getAtendimentos()));
	}

	@Test
	void quandoAlterarVeterinarioRetornarConsultaVeterinarioDTO() {
		when(veterinarioService.alteraVeterinario(dto, ID)).thenReturn(veterinarioNovo);
		
		ResponseEntity<ConsultaVeterinarioDTO> response = veterinarioController.alterarVeterinario(dto, ID);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ConsultaVeterinarioDTO.class, response.getBody().getClass()),
				  () -> assertEquals(ID2, response.getBody().getId()),
				  () -> assertEquals("Pablo", response.getBody().getNome()),
				  () -> assertEquals("479.988.140-04", response.getBody().getCpf()),
				  () -> assertEquals(listAtendimentoNovo, response.getBody().getAtendimentos()),
				  () -> assertEquals("Qualquer", response.getBody().getEspecialidade()));
	}

	@Test
	void testExcluirVeterinario() {
		doNothing().when(veterinarioService).excluirVeterinario(ID);
		
		ResponseEntity<ConsultaVeterinarioDTO> response = veterinarioController.excluirVeterinario(ID);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> verify(veterinarioService, times(1)).excluirVeterinario(anyLong()));
	}

}
