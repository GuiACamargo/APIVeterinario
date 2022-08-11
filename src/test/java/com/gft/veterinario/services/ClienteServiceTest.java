package com.gft.veterinario.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.gft.veterinario.dto.cliente.ClienteMapper;
import com.gft.veterinario.dto.cliente.RegistroClienteComSenhaDTO;
import com.gft.veterinario.dto.cliente.RegistroClienteDTO;
import com.gft.veterinario.entities.Animal;
import com.gft.veterinario.entities.Atendimento;
import com.gft.veterinario.entities.Cliente;
import com.gft.veterinario.entities.DadosDoAnimal;
import com.gft.veterinario.entities.Perfil;
import com.gft.veterinario.entities.Usuario;
import com.gft.veterinario.exceptions.AlreadyExistsException;
import com.gft.veterinario.exceptions.EntityMissingException;
import com.gft.veterinario.exceptions.InvalidCpfException;
import com.gft.veterinario.exceptions.LackOfSpaceException;
import com.gft.veterinario.exceptions.MissingInformationsException;
import com.gft.veterinario.repositories.AnimalRepository;
import com.gft.veterinario.repositories.AtendimentoRepository;
import com.gft.veterinario.repositories.ClienteRepository;
import com.gft.veterinario.repositories.PerfilRepository;
import com.gft.veterinario.repositories.UsuarioRepository;

import br.com.caelum.stella.validation.CPFValidator;

@SpringBootTest
class ClienteServiceTest {
	
	private static final long ID2 = 2L;
	private static final int INDEX_ZERO = 0;
	private static final long ID = 1L;
	private static final String NOME_CLIENTE = "Guilherme";
	private static final String NOME_VETERINARIO = "José";
	private static final String CPF = "859.111.510-49";
	
	@InjectMocks
	private ClienteService clienteService;
	@Mock
	private ClienteRepository clienteRepository;
	@Mock
	private AnimalRepository animalRepository;
	@Mock
	private AtendimentoRepository atendimentoRepository;
	@Mock
	private PerfilRepository perfilRepository;
	@Mock
	private UsuarioRepository usuarioRepository;
	@Mock
	private CPFValidator cpfValidator;
	@Mock
	private ClienteMapper clienteMapper;
	
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
	private Optional<Cliente> optionalCliente;
	private Page<Cliente> pageCliente;
	private List<Animal> listAnimal = new ArrayList<>();
	private List<Atendimento> listAtendimento = new ArrayList<>();
	private List<Long> idsAtendimento = new ArrayList<>();
	private List<Long> idsAnimal = new ArrayList<>();
	private List<Animal> listAnimalNovo = new ArrayList<>();
	private List<Atendimento> listAtendimentoNovo = new ArrayList<>();
	private List<Long> idsAtendimentoNovo = new ArrayList<>();
	private List<Long> idsAnimalNovo = new ArrayList<>();
	
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
		optionalCliente = Optional.of(cliente);
		perfilCliente = new Perfil(ID2, "cliente");
		usuarioCliente = new Usuario(ID2, cliente.getCpf(), "123", perfilCliente);
		dtoComSenha = new RegistroClienteComSenhaDTO(usuarioCliente.getPassword(), NOME_CLIENTE, cliente.getCpf(), idsAtendimento, idsAnimal);
		dto = new RegistroClienteDTO("Pablo", "479.988.140-04", idsAtendimentoNovo, idsAnimalNovo);
		
		pageable = PageRequest.of(0, 10);
		pageCliente = new PageImpl<>(List.of(cliente), pageable, 10);
	}

	@Test
	void quandoCriaClienteComAnimalAtendimentoRetornarSucessoComPerfil() {
		when(perfilRepository.findByNome("cliente")).thenReturn(Optional.of(perfilCliente));
		when(usuarioRepository.save(usuarioCliente)).thenReturn(usuarioCliente);
		when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
		when(atendimentoRepository.findById(1L)).thenReturn(Optional.of(atendimento));
		when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.empty());
		when(clienteRepository.save(any())).thenReturn(clienteComAnimalEAtendimento);
		
		Cliente response = clienteService.criaClienteComAnimalAtendimento(dtoComSenha);
		
		assertAll(() -> assertEquals(NOME_CLIENTE, response.getNome()),
				  () -> assertNotNull(response),
				  () -> assertEquals(ID, response.getId()),
				  () -> assertEquals(CPF, response.getCpf()),
				  () -> assertEquals(listAtendimento, response.getAtendimentos()),
				  () -> assertEquals(listAnimal, response.getAnimais()));
	}
	
	@Test
	void quandoCriaClienteComAnimalAtendimentoRetornarSucessoSemPerfil() {
		when(perfilRepository.findByNome("cliente")).thenReturn(Optional.empty());
		when(usuarioRepository.save(usuarioCliente)).thenReturn(usuarioCliente);
		when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
		when(atendimentoRepository.findById(1L)).thenReturn(Optional.of(atendimento));
		when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.empty());
		when(clienteRepository.save(any())).thenReturn(clienteComAnimalEAtendimento);
		
		Cliente response = clienteService.criaClienteComAnimalAtendimento(dtoComSenha);
		
		assertAll(() -> assertEquals(NOME_CLIENTE, response.getNome()),
				() -> assertNotNull(response),
				() -> assertEquals(ID, response.getId()),
				() -> assertEquals(CPF, response.getCpf()),
				() -> assertEquals(listAtendimento, response.getAtendimentos()),
				() -> assertEquals(listAnimal, response.getAnimais()));
	}
	
	@Test
	void quandoCriaClienteComAnimalAtendimentoRetornarUmaInvalidCpfException() {
		dtoComSenha.setCpfUsadoParaLogin("999.999.999-99");
		when(perfilRepository.findByNome("cliente")).thenReturn(Optional.of(perfilCliente));
		
		try {
			clienteService.criaClienteComAnimalAtendimento(dtoComSenha);
		} catch (InvalidCpfException e) {
			assertAll(() -> assertEquals(InvalidCpfException.class, e.getClass()),
					  () -> assertEquals("Cpf inválido!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoCriaClienteComAnimalAtendimentoRetornarUmaMissingInformationsExceptionComPerfil() {
		dtoComSenha.setSenhaUsadaParaLogin(null);
		when(perfilRepository.findByNome("cliente")).thenReturn(Optional.of(perfilCliente));
		
		try {
			clienteService.criaClienteComAnimalAtendimento(dtoComSenha);
		} catch (MissingInformationsException e) {
			assertAll(() -> assertEquals(MissingInformationsException.class, e.getClass()),
					  () -> assertEquals("Diga um CPF e uma senha", e.getMensagem()));
		}
	}
	
	@Test
	void quandoCriaClienteComAnimalAtendimentoRetornarUmaMissingInformationsExceptionSemPerfil() {
		dtoComSenha.setSenhaUsadaParaLogin(null);
		when(perfilRepository.findByNome("cliente")).thenReturn(Optional.empty());
		
		try {
			clienteService.criaClienteComAnimalAtendimento(dtoComSenha);
		} catch (MissingInformationsException e) {
			assertAll(() -> assertEquals(MissingInformationsException.class, e.getClass()),
					() -> assertEquals("Diga um CPF e uma senha", e.getMensagem()));
		}
	}
	
	@Test
	void quandoCriaClienteComAnimalAtendimentoRetornarUmaLackOfSpaceExceptionParaAnimal() {
		animal.setDono(cliente);
		when(perfilRepository.findByNome("cliente")).thenReturn(Optional.of(perfilCliente));
		when(usuarioRepository.save(usuarioCliente)).thenReturn(usuarioCliente);
		when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
		
		try {
			clienteService.criaClienteComAnimalAtendimento(dtoComSenha);
		} catch (LackOfSpaceException e) {
			assertAll(() -> assertEquals(LackOfSpaceException.class, e.getClass()),
					  () -> assertEquals("Esse animal já possui um dono!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoCriaClienteComAnimalAtendimentoRetornarUmaEntityMissingExceptionParaAnimal() {
		when(perfilRepository.findByNome("cliente")).thenReturn(Optional.of(perfilCliente));
		when(usuarioRepository.save(usuarioCliente)).thenReturn(usuarioCliente);
		when(animalRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		try {
			clienteService.criaClienteComAnimalAtendimento(dtoComSenha);
		} catch (EntityMissingException e) {
			assertAll(() -> assertEquals(EntityMissingException.class, e.getClass()),
					  () -> assertEquals("Animal não encontrado!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoCriaClienteComAnimalAtendimentoRetornarUmaLackOfSpaceExceptionParaAtendimento() {
		atendimento.setCliente(cliente);
		when(perfilRepository.findByNome("cliente")).thenReturn(Optional.of(perfilCliente));
		when(usuarioRepository.save(usuarioCliente)).thenReturn(usuarioCliente);
		when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
		when(atendimentoRepository.findById(1L)).thenReturn(Optional.of(atendimento));
		
		try {
			clienteService.criaClienteComAnimalAtendimento(dtoComSenha);
		} catch (LackOfSpaceException e) {
			assertAll(() -> assertEquals(LackOfSpaceException.class, e.getClass()),
					  () -> assertEquals("Esse atendimento já possui um cliente cadastrado!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoCriaClienteComAnimalAtendimentoRetornarUmaEntityMissingExceptionParaAtendimento() {
		when(perfilRepository.findByNome("cliente")).thenReturn(Optional.of(perfilCliente));
		when(usuarioRepository.save(usuarioCliente)).thenReturn(usuarioCliente);
		when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
		when(atendimentoRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		try {
			clienteService.criaClienteComAnimalAtendimento(dtoComSenha);
		} catch (EntityMissingException e) {
			assertAll(() -> assertEquals(EntityMissingException.class, e.getClass()),
					  () -> assertEquals("Atendimento não encontrado!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoCriaClienteComAnimalAtendimentoRetornarUmaAlreadyExistsExceptionParaAtendimento() {
		when(perfilRepository.findByNome("cliente")).thenReturn(Optional.of(perfilCliente));
		when(usuarioRepository.save(usuarioCliente)).thenReturn(usuarioCliente);
		when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
		when(atendimentoRepository.findById(1L)).thenReturn(Optional.of(atendimento));
		when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));
		
		try {
			clienteService.criaClienteComAnimalAtendimento(dtoComSenha);
		} catch (AlreadyExistsException e) {
			assertAll(() -> assertEquals(AlreadyExistsException.class, e.getClass()),
					  () -> assertEquals("Já existe um cliente com esse CPF!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoAlteraClienteRetornarSucesso() {
		cliente.setAnimais(listAnimal);
		cliente.setAtendimentos(listAtendimento);
		optionalCliente = Optional.of(cliente);
		when(perfilRepository.findByNome("cliente")).thenReturn(Optional.of(perfilCliente));
		when(usuarioRepository.save(usuarioCliente)).thenReturn(usuarioCliente);
		when(animalRepository.findById(2L)).thenReturn(Optional.of(animalNovo));
		when(atendimentoRepository.findById(2L)).thenReturn(Optional.of(atendimentoNovo));
		when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.empty());
		when(clienteRepository.save(any())).thenReturn(clienteNovo);
		when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);
		
		Cliente response = clienteService.alteraCliente(dto, 1L);
		
		assertAll(() -> assertEquals("Pablo", response.getNome()),
				  () -> assertNotNull(response),
				  () -> assertEquals(ID2, response.getId()),
				  () -> assertEquals("479.988.140-04", response.getCpf()),
				  () -> assertEquals(listAtendimentoNovo, response.getAtendimentos()),
				  () -> assertEquals(listAnimalNovo, response.getAnimais()));
	}
	
	@Test
	void quandoAlteraClienteRetornarUmaInvalidCpfException() {
		dto.setCpf("999.999.999-99");
		when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);
		
		try {
			clienteService.alteraCliente(dto, 1L);
		} catch (InvalidCpfException e) {
			assertAll(() -> assertEquals(InvalidCpfException.class, e.getClass()),
					  () -> assertEquals("Cpf inválido!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoAlteraClienteRetornarUmaLackOfSpaceExceptionParaAnimal() {
		animal.setDono(cliente);
		when(perfilRepository.findByNome("cliente")).thenReturn(Optional.of(perfilCliente));
		when(animalRepository.findById(2L)).thenReturn(Optional.of(animal));
		when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);

		try {
			clienteService.alteraCliente(dto, 1L);
		} catch (LackOfSpaceException e) {
			assertAll(() -> assertEquals(LackOfSpaceException.class, e.getClass()),
					  () -> assertEquals("Esse animal já possui um dono!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoAlteraClienteRetornarUmaEntityMissingExceptionParaAnimal() {
		when(animalRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);

		try {
			clienteService.alteraCliente(dto, 1L);
		} catch (EntityMissingException e) {
			assertAll(() -> assertEquals(EntityMissingException.class, e.getClass()),
					  () -> assertEquals("Animal não encontrado!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoAlteraClienteRetornarUmaLackOfSpaceExceptionParaAtendimento() {
		atendimentoNovo.setCliente(cliente);
		when(animalRepository.findById(2L)).thenReturn(Optional.of(animalNovo));
		when(atendimentoRepository.findById(2L)).thenReturn(Optional.of(atendimentoNovo));
		when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);

		try {
			clienteService.alteraCliente(dto, 1L);
		} catch (LackOfSpaceException e) {
			assertAll(() -> assertEquals(LackOfSpaceException.class, e.getClass()),
					  () -> assertEquals("Esse atendimento já possui um cliente cadastrado!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoAlteraClienteRetornarUmaEntityMissingExceptionParaAtendimento() {
		when(animalRepository.findById(2L)).thenReturn(Optional.of(animalNovo));
		when(atendimentoRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);

		try {
			clienteService.alteraCliente(dto, 1L);
		} catch (EntityMissingException e) {
			assertAll(() -> assertEquals(EntityMissingException.class, e.getClass()),
					  () -> assertEquals("Atendimento não encontrado!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoAlteraClienteRetornarUmaAlreadyExistsExceptionParaAtendimento() {
		when(animalRepository.findById(2L)).thenReturn(Optional.of(animalNovo));
		when(atendimentoRepository.findById(2L)).thenReturn(Optional.of(atendimentoNovo));
		when(clienteRepository.findByCpf(clienteNovo.getCpf())).thenReturn(Optional.of(cliente));
		when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);

		try {
			clienteService.alteraCliente(dto, 1L);
		} catch (AlreadyExistsException e) {
			assertAll(() -> assertEquals(AlreadyExistsException.class, e.getClass()),
					  () -> assertEquals("Já existe um cliente com esse CPF!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoBuscarTodosOsClientesRetornarUmaPageDosClientes() {
		cliente.setAnimais(listAnimal);
		cliente.setAtendimentos(listAtendimento);
		when(clienteRepository.findAll(pageable)).thenReturn(pageCliente);
		
		Page<Cliente> response = clienteService.buscarTodosOsClientes(pageable);
		
		assertAll(() -> assertNotNull(response),
				  () -> assertEquals(List.of(cliente), response.getContent()),
				  () -> assertEquals(10, response.getTotalElements()),
				  () -> assertEquals(1, response.getTotalPages()),
				  () -> assertEquals(10, response.getSize()),
				  () -> assertEquals(Cliente.class, response.getContent().get(INDEX_ZERO).getClass()),
				  () -> assertEquals(ID, response.getContent().get(INDEX_ZERO).getId()),
				  () -> assertEquals(NOME_CLIENTE, response.getContent().get(INDEX_ZERO).getNome()),
				  () -> assertEquals(CPF, response.getContent().get(INDEX_ZERO).getCpf()),
				  () -> assertEquals(listAtendimento, response.getContent().get(INDEX_ZERO).getAtendimentos()),
				  () -> assertEquals(listAnimal, response.getContent().get(INDEX_ZERO).getAnimais()));
	}

	@Test
	void quandoBuscarClienteRetornarUmaInstanciaDeUsuario() {	
		when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);
		Cliente response = clienteService.buscarCliente(1L);
		
		assertAll(() -> assertEquals(Cliente.class, response.getClass()),
				  () -> assertEquals(ID, response.getId()),
				  () -> assertEquals(NOME_CLIENTE, response.getNome()),
				  () -> assertNotNull(response));
	}
	
	@Test
	void quandoBuscarClienteRetornarUmaEntityMissingException() {
		when(clienteRepository.findById(anyLong())).thenThrow(new EntityMissingException("Cliente não encontrado!"));
		
		try {
			clienteService.buscarCliente(ID);
		} catch (EntityMissingException e) {
			assertAll(() -> assertEquals(EntityMissingException.class, e.getClass()),
					  () -> assertEquals("Cliente não encontrado!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoSalvarClienteRetornarSucesso() {
		when(clienteRepository.save(cliente)).thenReturn(cliente);
		
		Cliente response = clienteService.salvarCliente(cliente);
		
		assertAll(() -> assertEquals(Cliente.class, response.getClass()),
				  () -> assertEquals(ID, response.getId()),
				  () -> assertEquals(NOME_CLIENTE, response.getNome()),
				  () -> assertNotNull(response));
	}
	
	@Test
	void quandoBuscarClientePeloCpfRetornarSucesso() {
		when(clienteRepository.findByCpf(CPF)).thenReturn(optionalCliente);
		
		Cliente response = clienteService.buscarClientePeloCpf(CPF);
		
		assertAll(() -> assertEquals(Cliente.class, response.getClass()),
				  () -> assertEquals(ID, response.getId()),
				  () -> assertEquals(NOME_CLIENTE, response.getNome()),
				  () -> assertNotNull(response));
	}
	
	@Test
	void quandoBuscarClientePeloCpfRetornarUmaEntityMissingException() {
		when(clienteRepository.findByCpf(CPF)).thenThrow(new EntityMissingException("Cliente não encontrado!"));
		
		try {
			clienteService.buscarClientePeloCpf(CPF);
		} catch (EntityMissingException e) {
			assertAll(() -> assertEquals(EntityMissingException.class, e.getClass()),
					  () -> assertEquals("Cliente não encontrado!", e.getMensagem()));
		}
	}
	
	@Test
	void quandoBuscarAnimaisDoClientePeloCpfRetornarSucesso() {
		cliente.setAnimais(listAnimal);
		optionalCliente = Optional.of(cliente);
		when(clienteRepository.findByCpf(CPF)).thenReturn(optionalCliente);

		List<Animal> response = clienteService.buscarAnimaisDoClientePorCpf(CPF);
		
		assertAll(() -> assertEquals(ArrayList.class, response.getClass()),
				  () -> assertEquals(listAnimal, response),
				  () -> assertNotNull(response));
	}
	
	@Test
	void quandoBuscarAtendimentosDoClientePeloCpfRetornarSucesso() {
		cliente.setAtendimentos(listAtendimento);
		optionalCliente = Optional.of(cliente);
		when(clienteRepository.findByCpf(CPF)).thenReturn(optionalCliente);
		
		List<Atendimento> response = clienteService.buscarAtendimentosDoClientePorCpf(CPF);
		
		assertAll(() -> assertEquals(ArrayList.class, response.getClass()),
				  () -> assertEquals(listAtendimento, response),
				  () -> assertNotNull(response));
	}
	
	@Test
	void quandoValidaCpfRetornarSucesso() {
		boolean response = clienteService.validaCpf(CPF);
		
		assertAll(() -> assertTrue(response));
	}
	
	@Test
	void quandoValidaCpfRetornarFalso() {
		boolean response = clienteService.validaCpf("999.999.999-99");
		
		assertAll(() -> assertFalse(response));
	}

	@Test
	void quandoDeletarClienteRetornarSucesso() {
		cliente.setAnimais(listAnimal);
		cliente.setAtendimentos(listAtendimento);
		animal.setDono(cliente);
		atendimento.setCliente(cliente);
		when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);
		doNothing().when(clienteRepository).deleteById(anyLong());
		clienteService.deletarCliente(ID);
		verify(clienteRepository, times(1)).deleteById(anyLong());
	}
}
