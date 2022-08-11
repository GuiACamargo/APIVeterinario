package com.gft.veterinario.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gft.veterinario.dto.atendimento.AtendimentoMapper;
import com.gft.veterinario.dto.atendimento.RegistroAtendimentoDTO;
import com.gft.veterinario.entities.Animal;
import com.gft.veterinario.entities.Atendimento;
import com.gft.veterinario.entities.Cliente;
import com.gft.veterinario.entities.Veterinario;
import com.gft.veterinario.exceptions.DeleteException;
import com.gft.veterinario.exceptions.EntityMissingException;
import com.gft.veterinario.repositories.AnimalRepository;
import com.gft.veterinario.repositories.AtendimentoRepository;
import com.gft.veterinario.repositories.VeterinarioRepository;

@Service
public class AtendimentoService {
	
	private final AtendimentoRepository atendimentoRepository;
	
	private final AnimalRepository animalRepository;
	
	private final VeterinarioRepository veterinarioRepository;
	
	public AtendimentoService(AtendimentoRepository atendimentoRepository, AnimalRepository animalRepository,
			VeterinarioRepository veterinarioRepository) {
		this.atendimentoRepository = atendimentoRepository;
		this.animalRepository = animalRepository;
		this.veterinarioRepository = veterinarioRepository;
	}

	public Atendimento salvarAtendimento (Atendimento atendimento) {
		return atendimentoRepository.save(atendimento);
	}
	
	public Page<Atendimento> listarTodosOsAtendimentos (Pageable pageable) {
		return atendimentoRepository.findAll(pageable);
	}
	
	public Atendimento buscarAtendimento (Long id) {
		Optional<Atendimento> atendimento = atendimentoRepository.findById(id);
		
		return atendimento.orElseThrow(() -> new EntityMissingException("Atendimento não encontrada!"));
	}
	
	public void excluirAtendimento (Long id) {
		Atendimento atendimento = buscarAtendimento(id);
		if(atendimento != null) {
			if(atendimento.getAnimal() != null) {
				atendimento.setAnimal(null);
			}
			if(atendimento.getCliente() != null) {
				atendimento.setCliente(null);
			}
			if(atendimento.getVeterinario() != null) {
				atendimento.setVeterinario(null);
			}
			atendimentoRepository.deleteById(id);
		} else {
			throw new DeleteException("Erro ao deletar atendimento!");
		}
		
	}
	
	public Atendimento criaAtendimentoComAnimalClienteVeterinario (RegistroAtendimentoDTO dto) {
		Atendimento atendimento = AtendimentoMapper.fromDTO(dto);
		if(dto.getIdAnimal() != null) {
			if(animalRepository.findById(dto.getIdAnimal()).isPresent()) {
				Animal animal = animalRepository.findById(dto.getIdAnimal()).get();
				
				Cliente cliente = animal.getDono();
			
				atendimento.setAnimal(animal);
				atendimento.setCliente(cliente);
				atendimento.setNomeAnimal(animal.getNome());
				atendimento.setNomeCliente(cliente.getNome());
			} else {
				throw new EntityMissingException("Animal não encontrado!");
			}
		}
		if(dto.getIdVeterinario() != null) {
			if(veterinarioRepository.findById(dto.getIdVeterinario()).isPresent()) {
				Veterinario veterinario = veterinarioRepository.findById(dto.getIdVeterinario()).get();

				atendimento.setVeterinario(veterinario);
				atendimento.setNomeVeterinario(veterinario.getNome());		
			} else {
				throw new EntityMissingException("Veterinário não encontrado!");
			}
		}
		return atendimento;
	}
	
	public Atendimento alteraAtendimento (RegistroAtendimentoDTO dto, Long id) {
		Atendimento atendimento = AtendimentoMapper.fromDTO(dto);
		Atendimento atendimentoOriginal = buscarAtendimento(id);
		if(atendimentoOriginal.getAnimal() != null) {
			atendimentoOriginal.setAnimal(null);
		}
		if(atendimentoOriginal.getCliente() != null) {
			atendimentoOriginal.setCliente(null);
		}
		if(atendimentoOriginal.getVeterinario() != null) {
			atendimentoOriginal.setVeterinario(null);
		}
		if(dto.getIdAnimal() != null) {
			if(animalRepository.findById(dto.getIdAnimal()).isPresent()) {
				Animal animal = animalRepository.findById(dto.getIdAnimal()).get();
				
				Cliente cliente = animal.getDono();
				
				atendimento.setAnimal(animal);
				atendimento.setCliente(cliente);
				atendimento.setNomeAnimal(animal.getNome());
				atendimento.setNomeCliente(cliente.getNome());
				
			} else {
				throw new EntityMissingException("Animal não encontrado!");
			}
		}
		if(dto.getIdVeterinario() != null) {
			if(veterinarioRepository.findById(dto.getIdVeterinario()).isPresent()) {
				Veterinario veterinario = veterinarioRepository.findById(dto.getIdVeterinario()).get();
				
				atendimento.setVeterinario(veterinario);
				atendimento.setNomeVeterinario(veterinario.getNome());		
			} else {
				throw new EntityMissingException("Veterinário não encontrado!");
			}
		}
		atendimento.setId(atendimentoOriginal.getId());
		return salvarAtendimento(atendimento);
	}

}
