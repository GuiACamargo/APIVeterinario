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

import com.gft.veterinario.dto.cliente.ClienteMapper;
import com.gft.veterinario.dto.cliente.ConsultaClienteDTO;
import com.gft.veterinario.dto.cliente.RegistroClienteComSenhaDTO;
import com.gft.veterinario.dto.cliente.RegistroClienteDTO;
import com.gft.veterinario.entities.Cliente;
import com.gft.veterinario.services.ClienteService;

@RestController
@RequestMapping("v1/clientes")
public class ClienteController {
	
	private final ClienteService clienteService;
	
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@GetMapping
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<Page<ConsultaClienteDTO>> buscarTodosOsClientes(@PageableDefault Pageable pageable) {
		return ResponseEntity.ok(clienteService.buscarTodosOsClientes(pageable).map(ClienteMapper::fromEntity));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaClienteDTO> buscarCliente(@PathVariable Long id) {
		Cliente cliente = clienteService.buscarCliente(id);
		
		return ResponseEntity.ok(ClienteMapper.fromEntity(cliente));
	}
	
	@GetMapping(value = "/buscaPorCpf")
	@PreAuthorize("hasAnyAuthority('veterinario', 'cliente')")
	public ResponseEntity<ConsultaClienteDTO> buscarClientePorCpf(@RequestParam String cpf) {
		Cliente cliente = clienteService.buscarClientePeloCpf(cpf);
		
		return ResponseEntity.ok(ClienteMapper.fromEntity(cliente));
	}
	
	@PostMapping
	public ResponseEntity<ConsultaClienteDTO> salvarCliente(@RequestBody @Valid RegistroClienteComSenhaDTO dto) {
		Cliente cliente = clienteService.criaClienteComAnimalAtendimento(dto);
		
		return ResponseEntity.ok(ClienteMapper.fromEntity(cliente));
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasAnyAuthority('veterinario', 'cliente')")
	public ResponseEntity<ConsultaClienteDTO> alterarCliente(@RequestBody @Valid RegistroClienteDTO dto, @PathVariable Long id) {
		Cliente cliente = clienteService.alteraCliente(dto, id);
		
		return ResponseEntity.ok(ClienteMapper.fromEntity(cliente));
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaClienteDTO> excluirClientePorId(@PathVariable Long id) {
		clienteService.deletarCliente(id);
		
		return ResponseEntity.ok().build();
	}	
	
}
