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

import com.gft.veterinario.dto.atendimento.AtendimentoMapper;
import com.gft.veterinario.dto.atendimento.ConsultaAtendimentoDTO;
import com.gft.veterinario.dto.atendimento.RegistroAtendimentoDTO;
import com.gft.veterinario.entities.Atendimento;
import com.gft.veterinario.services.AtendimentoService;
import com.gft.veterinario.services.ClienteService;

@RestController
@RequestMapping("v1/atendimentos")
public class AtendimentoController {
	
	private final AtendimentoService atendimentoService;
	
	private final ClienteService clienteService;

	public AtendimentoController(AtendimentoService atendimentoService, ClienteService clienteService) {
		this.atendimentoService = atendimentoService;
		this.clienteService = clienteService;
	}

	@GetMapping
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<Page<ConsultaAtendimentoDTO>> buscarTodosOsAtendimentos (@PageableDefault Pageable pageable) {
		return ResponseEntity.ok(atendimentoService.listarTodosOsAtendimentos(pageable).map(AtendimentoMapper::fromEntity));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaAtendimentoDTO> buscarAtendimento (@PathVariable Long id) {
		return ResponseEntity.ok(AtendimentoMapper.fromEntity(atendimentoService.buscarAtendimento(id)));
	}
	
	@GetMapping(value = "/buscaPorCpf")
	@PreAuthorize("hasAnyAuthority('veterinario', 'cliente')")
	public ResponseEntity<List<Atendimento>> buscarAtendimentosDoClientePorCpf(@RequestParam String cpf) {
		
		return ResponseEntity.ok(clienteService.buscarAtendimentosDoClientePorCpf(cpf));
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaAtendimentoDTO> salvarAtendimento (@RequestBody @Valid RegistroAtendimentoDTO dto) {
		Atendimento atendimento = atendimentoService.criaAtendimentoComAnimalClienteVeterinario(dto);
		
		return ResponseEntity.ok(AtendimentoMapper.fromEntity(atendimentoService.salvarAtendimento(atendimento)));
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaAtendimentoDTO> alterarAtendimento (@RequestBody @Valid RegistroAtendimentoDTO dto, @PathVariable Long id) {
		Atendimento atendimento = atendimentoService.alteraAtendimento(dto, id);
			
		return ResponseEntity.ok(AtendimentoMapper.fromEntity(atendimento));
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaAtendimentoDTO> excluirAtendimento (@PathVariable Long id) {
		atendimentoService.excluirAtendimento(id);
			
		return ResponseEntity.ok().build();
	}	
}
