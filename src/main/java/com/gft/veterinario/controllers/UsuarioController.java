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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.veterinario.dto.usuario.ConsultaUsuarioDTO;
import com.gft.veterinario.dto.usuario.MudarUsuarioDTO;
import com.gft.veterinario.dto.usuario.UsuarioMapper;
import com.gft.veterinario.entities.Usuario;
import com.gft.veterinario.services.UsuarioService;

@RestController
@RequestMapping("v1/usuarios")
public class UsuarioController {

private final UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<Page<ConsultaUsuarioDTO>> buscarTodosOsUsuarios(@PageableDefault Pageable pageable) {
		return ResponseEntity.ok(usuarioService.listarTodosOsUsuarios(pageable).map(UsuarioMapper::fromEntity));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaUsuarioDTO> buscarUsuario(@PathVariable Long id) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(id);
		
		return ResponseEntity.ok(UsuarioMapper.fromEntity(usuario));
	}
	
	@PutMapping
	@PreAuthorize("hasAnyAuthority('veterinario', 'cliente')")
	public ResponseEntity<ConsultaUsuarioDTO> alterarUsuario(@RequestBody @Valid MudarUsuarioDTO dto) {
		Usuario usuario = usuarioService.atualizarUsuario(dto);
			
		return ResponseEntity.ok(UsuarioMapper.fromEntity(usuario));
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('veterinario')")
	public ResponseEntity<ConsultaUsuarioDTO> excluirUsuario(@PathVariable Long id) {
		usuarioService.excluirUsuario(id);
			
		return ResponseEntity.ok().build();
	}
}
	
