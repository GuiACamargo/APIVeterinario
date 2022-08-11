package com.gft.veterinario.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gft.veterinario.dto.animal.AnimalMapper;
import com.gft.veterinario.dto.animal.RegistroAnimalDTO;
import com.gft.veterinario.entities.Animal;
import com.gft.veterinario.entities.Atendimento;
import com.gft.veterinario.entities.Cliente;
import com.gft.veterinario.entities.Raca;
import com.gft.veterinario.exceptions.AnimalAlreadyExistsException;
import com.gft.veterinario.exceptions.DeleteException;
import com.gft.veterinario.exceptions.EntityMissingException;
import com.gft.veterinario.exceptions.LackOfSpaceException;
import com.gft.veterinario.repositories.AnimalRepository;
import com.gft.veterinario.repositories.AtendimentoRepository;
import com.gft.veterinario.repositories.ClienteRepository;
import com.gft.veterinario.repositories.RacaRepository;

@Service
public class AnimalService {

	private final AnimalRepository animalRepository;
	
	private final ClienteRepository clienteRepository;
	
	private final AtendimentoRepository atendimentoRepository;
	
	private final RacaRepository racaRepository;
	
	@Autowired
	private RacaService racaService;
	
	public AnimalService(AnimalRepository animalRepository, ClienteRepository clienteRepository,
			AtendimentoRepository atendimentoRepository, RacaRepository racaRepository, RacaService racaService) {
		this.animalRepository = animalRepository;
		this.clienteRepository = clienteRepository;
		this.atendimentoRepository = atendimentoRepository;
		this.racaRepository = racaRepository;
		this.racaService = racaService;
	}

	public Animal salvarAnimal (Animal animal) {
		return animalRepository.save(animal);
	}
	
	public Page<Animal> listarTodoOsAnimais (Pageable pageable) {
		return animalRepository.findAll(pageable);
	}
	
	public Animal buscarAnimal(Long id) {
		Optional<Animal> animal = animalRepository.findById(id);
		
		return animal.orElseThrow(() -> new EntityMissingException("Animal não encontrado!"));
	}
	
	public void excluirAnimal (Long id) {
		Animal animal = buscarAnimal(id);
		if(animal != null) {
			if(animal.getAtendimentos() != null) {
				animal.getAtendimentos().forEach(a -> {
					a.setAnimal(null);
					a.setNomeAnimal(null);
				});
			}
			if(animal.getDono() != null) {
				animal.setDono(null);
			}
			animal.setRaca(null);
			animalRepository.deleteById(id);
		} else {
			throw new DeleteException("Erro ao deletar animal!");
		}
		
	}
	
	public Animal criaAnimalComDonoAtendimentoRaca (RegistroAnimalDTO dto) {
		Animal animal = AnimalMapper.fromDTO(dto);
		if(dto.getIdDaRaca() != null) {
			try {
				Raca raca = racaService.criaRacaPelaBusca(dto.getIdDaRaca());
				if(racaRepository.findByNome(raca.getNome()).isPresent()) {
					Raca racaJaExistente = racaRepository.findByNome(raca.getNome()).get();
					animal.setRaca(racaJaExistente);
				} else {
					racaRepository.save(raca);
					animal.setRaca(raca);
				}
			} catch (RuntimeException e) {
				throw new EntityMissingException("Não existe raça com esse ID");
			}
		} else {
			Raca raca = new Raca(null, "Sem Raça Definida", "Varia", "Varia", null);
			if(racaRepository.findByNome(raca.getNome()).isPresent()) {
				Raca racaJaExistente = racaRepository.findByNome(raca.getNome()).get();
				animal.setRaca(racaJaExistente);
			} else {
				racaRepository.save(raca);
				animal.setRaca(raca);
			}
		}
		if(dto.getIdDoDono() != null) {
			if(clienteRepository.findById(dto.getIdDoDono()).isPresent()) {
				Cliente cliente = clienteRepository.findById(dto.getIdDoDono()).get();
				animal.setDono(cliente);
				if (cliente.getAnimais().contains(animal) == false) {
					List<Animal> animaisParaCliente = cliente.getAnimais();
					if(animaisParaCliente == null) {
						animaisParaCliente = new ArrayList<>(Arrays.asList(animal));
					} else {
						animaisParaCliente.add(animal);
					}
					cliente.setAnimais(animaisParaCliente);
				} else {
					throw new AnimalAlreadyExistsException("Animal já está associado ao cliente!");
				}
			} else {
				throw new EntityMissingException("Cliente não encontrado!");
			}
		}
		if(dto.getIdDoAtendimento() != null) {
			dto.getIdDoAtendimento().forEach(a -> {
				if(atendimentoRepository.findById(a).isPresent()) {
					Atendimento atendimento = atendimentoRepository.findById(a).get();
					List<Atendimento> atendimentos = animal.getAtendimentos();
					if(atendimento.getAnimal() == null) {
						if(atendimentos == null) {
							atendimentos = new ArrayList<>(Arrays.asList(atendimento));
						} else {
							atendimentos.add(atendimento);
						}
						atendimento.setAnimal(animal);
						animal.setAtendimentos(atendimentos);
					} else {
						throw new LackOfSpaceException("Esse atendimento já possui um animal cadastrado!");
					}
				} else {
					throw new EntityMissingException("Atendimento não encontrado!");
				}
			});
		}
		return animal;
	}
	
	public Animal alteraAnimal (RegistroAnimalDTO dto, Long id) {
		Animal animal = AnimalMapper.fromDTO(dto);
		Animal animalOriginal = buscarAnimal(id);
		if(animalOriginal.getAtendimentos() != null) {
			animalOriginal.getAtendimentos().forEach(a -> {
				a.setAnimal(null);
				a.setNomeAnimal(null);
			});
		}
		if(animalOriginal.getDono() != null) {
			animalOriginal.setDono(null);
		}
		animalOriginal.setRaca(null);
		if(dto.getIdDaRaca() != null) {
			try {
				Raca raca = racaService.criaRacaPelaBusca(dto.getIdDaRaca());
	 			if(racaRepository.findByNome(raca.getNome()).isPresent()) {
					Raca racaJaExistente = racaRepository.findByNome(raca.getNome()).get();
					animal.setRaca(racaJaExistente);
				} else {
					racaRepository.save(raca);
					animal.setRaca(raca);
				}
			} catch (RuntimeException e) {
				throw new EntityMissingException("Não existe raça com esse ID");
			}		
		} else {
			Raca raca = new Raca(null, "Sem Raça Definida", "Varia", "Varia", null);
			if(racaRepository.findByNome(raca.getNome()).isPresent()) {
				Raca racaJaExistente = racaRepository.findByNome(raca.getNome()).get();
				animal.setRaca(racaJaExistente);
			} else {
				racaRepository.save(raca);
				animal.setRaca(raca);
			}
		}
		if(dto.getIdDoDono() != null) {
			if(clienteRepository.findById(dto.getIdDoDono()).isPresent()) {
				Cliente cliente = clienteRepository.findById(dto.getIdDoDono()).get();
				animal.setDono(cliente);
				if (cliente.getAnimais().contains(animal) == false) {
					List<Animal> animaisParaCliente = cliente.getAnimais();
					if(animaisParaCliente == null) {
						animaisParaCliente = new ArrayList<>(Arrays.asList(animal));
					} else {
						animaisParaCliente.add(animal);
					}
					cliente.setAnimais(animaisParaCliente);
				} else {
					throw new AnimalAlreadyExistsException("Animal já está associado ao cliente!");
				}
			} else {
				throw new EntityMissingException("Cliente não encontrado!");
			}
		}
		if(dto.getIdDoAtendimento() != null) {
			dto.getIdDoAtendimento().forEach(a -> {
				if(atendimentoRepository.findById(a).isPresent()) {
					Atendimento atendimento = atendimentoRepository.findById(a).get();
					List<Atendimento> atendimentos = animal.getAtendimentos();
					if(atendimento.getAnimal() == null) {
						if(atendimentos == null) {
							atendimentos = new ArrayList<>(Arrays.asList(atendimento));
						} else {
							atendimentos.add(atendimento);
						}
						atendimento.setAnimal(animal);
						animal.setAtendimentos(atendimentos);
					} else {
						throw new LackOfSpaceException("Esse atendimento já possui um animal cadastrado!");
					}
				} else {
					throw new EntityMissingException("Atendimento não encontrado!");
				}
			});
		}
		animal.setId(animalOriginal.getId());
		return salvarAnimal(animal);
	}
}
