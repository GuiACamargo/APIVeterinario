package com.gft.veterinario.controllers;

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

import com.gft.veterinario.dto.veterinario.ConsultaVeterinarioDTO;
import com.gft.veterinario.dto.veterinario.RegistroVeterinarioComSenhaDTO;
import com.gft.veterinario.dto.veterinario.RegistroVeterinarioDTO;
import com.gft.veterinario.dto.veterinario.VeterinarioMapper;
import com.gft.veterinario.entities.Veterinario;
import com.gft.veterinario.services.VeterinarioService;

@RestController
@RequestMapping("v1/veterinarios")
public class VeterinarioController {
	
	private final VeterinarioService veterinarioService;

	public VeterinarioController(VeterinarioService veterinarioService) {
		this.veterinarioService = veterinarioService;
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<Page<ConsultaVeterinarioDTO>> buscarTodosOsVeterinarios (@PageableDefault Pageable pageable) {
		
		return ResponseEntity.ok(veterinarioService.listarTodosOsVeterinarios(pageable).map(VeterinarioMapper::fromEntity));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaVeterinarioDTO> buscarVeterinario(@PathVariable Long id) {
		Veterinario veterinario = veterinarioService.buscarVeterinario(id);
		
		return ResponseEntity.ok(VeterinarioMapper.fromEntity(veterinario));
	}
	
	@GetMapping("/buscaPorCpf")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaVeterinarioDTO> buscarVeterinarioPorCpf(@RequestParam String cpf) {
		Veterinario veterinario = veterinarioService.buscarVeterinarioPeloCpf(cpf);
		
		return ResponseEntity.ok(VeterinarioMapper.fromEntity(veterinario));
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaVeterinarioDTO> salvarVeterinario(@RequestBody @Valid RegistroVeterinarioComSenhaDTO dto) {
		Veterinario veterinario = veterinarioService.criaVeterinarioComAtendimento(dto);
		
		return ResponseEntity.ok(VeterinarioMapper.fromEntity(veterinario));
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaVeterinarioDTO> alterarVeterinario (@RequestBody @Valid RegistroVeterinarioDTO dto, @PathVariable Long id) {
		Veterinario veterinario = veterinarioService.alteraVeterinario(dto, id);
			
		return ResponseEntity.ok(VeterinarioMapper.fromEntity(veterinario));
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaVeterinarioDTO> excluirVeterinario (@PathVariable Long id) {
		veterinarioService.excluirVeterinario(id);
			
		return ResponseEntity.ok().build();
	}
}
