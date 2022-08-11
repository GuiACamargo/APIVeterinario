package com.gft.veterinario.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gft.veterinario.dto.cliente.ClienteMapper;
import com.gft.veterinario.dto.cliente.RegistroClienteComSenhaDTO;
import com.gft.veterinario.dto.cliente.RegistroClienteDTO;
import com.gft.veterinario.entities.Animal;
import com.gft.veterinario.entities.Atendimento;
import com.gft.veterinario.entities.Cliente;
import com.gft.veterinario.entities.Perfil;
import com.gft.veterinario.entities.Usuario;
import com.gft.veterinario.exceptions.AlreadyExistsException;
import com.gft.veterinario.exceptions.DeleteException;
import com.gft.veterinario.exceptions.EntityMissingException;
import com.gft.veterinario.exceptions.InvalidCpfException;
import com.gft.veterinario.exceptions.LackOfSpaceException;
import com.gft.veterinario.exceptions.MissingInformationsException;
import com.gft.veterinario.repositories.AnimalRepository;
import com.gft.veterinario.repositories.AtendimentoRepository;
import com.gft.veterinario.repositories.ClienteRepository;
import com.gft.veterinario.repositories.PerfilRepository;
import com.gft.veterinario.repositories.UsuarioRepository;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

@Service
public class ClienteService {
	
	@Autowired
	@Lazy
	private AuthenticationManager authManager;

	private final ClienteRepository clienteRepository;
	
	private final AnimalRepository animalRepository;
	
	private final AtendimentoRepository atendimentoRepository;
	
	private final PerfilRepository perfilRepository;
	
	private final UsuarioRepository usuarioRepository;

	public ClienteService(ClienteRepository clienteRepository, AnimalRepository animalRepository,
			AtendimentoRepository atendimentoRepository, PerfilRepository perfilRepository,
			UsuarioRepository usuarioRepository) {
		this.clienteRepository = clienteRepository;
		this.animalRepository = animalRepository;
		this.atendimentoRepository = atendimentoRepository;
		this.perfilRepository = perfilRepository;
		this.usuarioRepository = usuarioRepository;
	}

	public Cliente salvarCliente (Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public Page<Cliente> buscarTodosOsClientes (Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}
	
	public Cliente buscarCliente (Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		return cliente.orElseThrow(() -> new EntityMissingException("Cliente não encontrado!"));
	}
	
	public Cliente buscarClientePeloCpf (String cpf) {
		Optional<Cliente> cliente = clienteRepository.findByCpf(cpf);
		
		return cliente.orElseThrow(() -> new EntityMissingException("Cliente não encontrado!"));
	}
	
	public List<Animal> buscarAnimaisDoClientePorCpf (String cpf) {
		Cliente cliente = buscarClientePeloCpf(cpf);
		
		return cliente.getAnimais();
	}
	
	public List<Atendimento> buscarAtendimentosDoClientePorCpf(String cpf) {
		Cliente cliente = buscarClientePeloCpf(cpf);

		return cliente.getAtendimentos();
	}
	
	public void deletarCliente (Long id) {
		Cliente cliente = buscarCliente(id);
		if (cliente != null) {
			if(cliente.getAnimais() != null) {
				cliente.getAnimais().forEach(a -> {
					a.setDono(null);
				});	
			}
			if(cliente.getAtendimentos() != null) {
				cliente.getAtendimentos().forEach(a -> {
					a.setCliente(null);
					a.setNomeCliente(null);
				});
			}
			clienteRepository.deleteById(id);
		} else {
			throw new DeleteException("Erro ao deletar o cliente!");
		}
	}
	
	public boolean validaCpf(String cpf){ 
	    CPFValidator cpfValidator = new CPFValidator();
	    List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf); 
	    if(erros.size() > 0) { 
	    	return false; 
	    } else { 
	    	return true; 
	    }
	}
	
	public Cliente criaClienteComAnimalAtendimento (RegistroClienteComSenhaDTO dto) {
		Cliente cliente = ClienteMapper.fromDTOComSenha(dto);
		Optional<Perfil> perfil = perfilRepository.findByNome("cliente");
		if(validaCpf(cliente.getCpf())) {
			if(perfil.isPresent()) {
				if(dto.getSenhaUsadaParaLogin() != null) {
					Usuario usuario = new Usuario(null, dto.getCpfUsadoParaLogin(), new BCryptPasswordEncoder().encode(dto.getSenhaUsadaParaLogin()) , perfil.get());
					usuarioRepository.save(usuario);
				} else {
					throw new MissingInformationsException("Diga um CPF e uma senha");
				}
			} else {
				if(dto.getSenhaUsadaParaLogin() != null) {
					Perfil perfilCliente = new Perfil(null, "cliente");
					Usuario usuario = new Usuario(null, dto.getCpfUsadoParaLogin(), new BCryptPasswordEncoder().encode(dto.getSenhaUsadaParaLogin()) , perfilCliente);
					usuarioRepository.save(usuario);
				} else {
					throw new MissingInformationsException("Diga um CPF e uma senha");
				}
			}
			if(dto.getIdDoAnimal() != null) {
				dto.getIdDoAnimal().forEach(a -> {
					if(animalRepository.findById(a).isPresent()) {
						Animal animal = animalRepository.findById(a).get();
						List<Animal> animais = cliente.getAnimais();
						if(animal.getDono() == null) {
							if(animais == null) {
								animais = new ArrayList<>(Arrays.asList(animal));
							}
							animal.setDono(cliente);
							cliente.setAnimais(animais);
						} else {
							throw new LackOfSpaceException("Esse animal já possui um dono!");
						}
					} else {
						throw new EntityMissingException("Animal não encontrado!");
					}
				});
			}
			if(dto.getAtedimentosId() != null) {
				dto.getAtedimentosId().forEach(a -> {
					if(atendimentoRepository.findById(a).isPresent()) {
						Atendimento atendimento = atendimentoRepository.findById(a).get();
						List<Atendimento> atendimentos = cliente.getAtendimentos();
						if(atendimento.getCliente() == null) {
							if(atendimentos == null) {							
								atendimentos = new ArrayList<>(Arrays.asList(atendimento));
							}
							atendimento.setCliente(cliente);
							cliente.setAtendimentos(atendimentos);
						} else {
							throw new LackOfSpaceException("Esse atendimento já possui um cliente cadastrado!");
						}
					} else {
						throw new EntityMissingException("Atendimento não encontrado!");
					}
				});
			}
			if (clienteRepository.findByCpf(cliente.getCpf()).isEmpty()) {
				return salvarCliente(cliente);
			} else {
				throw new AlreadyExistsException("Já existe um cliente com esse CPF!");
			}
		} else {
			throw new InvalidCpfException("Cpf inválido!");
		}
	}
	
	public Cliente alteraCliente (RegistroClienteDTO dto, Long id) {
		Cliente cliente = ClienteMapper.fromDTO(dto);
		Cliente clienteOriginal = buscarCliente(id);
		if(validaCpf(cliente.getCpf())) {
			if(clienteOriginal.getAnimais() != null) {
				clienteOriginal.getAnimais().forEach(a -> {
					a.setDono(null);
				});
			}
			if(clienteOriginal.getAtendimentos() != null) {
				clienteOriginal.getAtendimentos().forEach(a -> {
					a.setCliente(null);
					a.setNomeCliente(null);
				});
			}
			
			if(dto.getIdDoAnimal() != null) {
				dto.getIdDoAnimal().forEach(a -> {
					if(animalRepository.findById(a).isPresent()) {
						Animal animal = animalRepository.findById(a).get();
						List<Animal> animais = clienteOriginal.getAnimais();
						if(animal.getDono() == null) {
							if(animais == null) {
								animais = new ArrayList<>(Arrays.asList(animal));
							} else {
								animais.add(animal);
							}
							animal.setDono(cliente);
							cliente.setAnimais(animais);
						} else {
							throw new LackOfSpaceException("Esse animal já possui um dono!");
						}
					} else {
						throw new EntityMissingException("Animal não encontrado!");
					}
				});
			}
			if(dto.getAtedimentosId() != null) {
				dto.getAtedimentosId().forEach(a -> {
					if(atendimentoRepository.findById(a).isPresent()) {
						Atendimento atendimento = atendimentoRepository.findById(a).get();
						List<Atendimento> atendimentos = clienteOriginal.getAtendimentos();
						if(atendimento.getCliente() == null) {
							if(atendimentos == null) {
								atendimentos = new ArrayList<>(Arrays.asList(atendimento));
							} else {							
								atendimentos.add(atendimento);
							}
							atendimento.setCliente(cliente);
							cliente.setAtendimentos(atendimentos);
						} else {
							throw new LackOfSpaceException("Esse atendimento já possui um cliente cadastrado!");
						}
					} else {
						throw new EntityMissingException("Atendimento não encontrado!");
					}
				});
			}
			cliente.setId(clienteOriginal.getId());
			if (clienteRepository.findByCpf(cliente.getCpf()).isEmpty()) {
				return salvarCliente(cliente);
			} else {
				throw new AlreadyExistsException("Já existe um cliente com esse CPF!");
			}
		} else {
			throw new InvalidCpfException("Cpf inválido!");
		}
		
	}

	
	
}
