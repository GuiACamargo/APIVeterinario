package com.gft.veterinario.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gft.veterinario.dto.animal.AnimalMapper;
import com.gft.veterinario.dto.animal.ConsultaAnimalDTO;
import com.gft.veterinario.dto.animal.RegistroAnimalDTO;
import com.gft.veterinario.entities.Animal;
import com.gft.veterinario.services.AnimalService;
import com.gft.veterinario.services.ClienteService;

@RestController
@RequestMapping("v1/animais")
public class AnimalController {

	private final AnimalService animalService;
	
	private final ClienteService clienteService;
	
	public AnimalController(AnimalService animalService, ClienteService clienteService) {
		this.animalService = animalService;
		this.clienteService = clienteService;
	}

	@GetMapping
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<Page<ConsultaAnimalDTO>> buscarTodosOsAnimais(@PageableDefault Pageable pageable) {
		return ResponseEntity.ok(animalService.listarTodoOsAnimais(pageable).map(AnimalMapper::fromEntity));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaAnimalDTO> buscarAnimal(@RequestParam Long id) {
		Animal animal = animalService.buscarAnimal(id);
		return ResponseEntity.ok(AnimalMapper.fromEntity(animal));
	}
	
	@GetMapping(value = "/buscaPorCpf")
	@PreAuthorize("hasAnyAuthority('veterinario', 'cliente')")
	public ResponseEntity<List<Animal>> buscarAnimaisDoClientePorCpf(@RequestParam String cpf) {
		
		return ResponseEntity.ok(clienteService.buscarAnimaisDoClientePorCpf(cpf));
	}
	
	@PostMapping
	@PreAuthorize("hasAnyAuthority('veterinario', 'cliente')")
	public ResponseEntity<ConsultaAnimalDTO> salvarAnimal(@RequestBody @Valid RegistroAnimalDTO dto) {
		Animal animal = animalService.criaAnimalComDonoAtendimentoRaca(dto);
		
		return ResponseEntity.ok(AnimalMapper.fromEntity(animalService.salvarAnimal(animal)));
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaAnimalDTO> alterarAnimal(@RequestBody @Valid RegistroAnimalDTO dto, @PathVariable Long id) {
		Animal animal = animalService.alteraAnimal(dto, id);
			
		return ResponseEntity.ok(AnimalMapper.fromEntity(animal));
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaAnimalDTO> excluirAnimal(@RequestParam Long id) {
		animalService.excluirAnimal(id);
			
		return ResponseEntity.ok().build();
	}	
}
