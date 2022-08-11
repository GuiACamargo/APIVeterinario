package com.gft.veterinario.dto.usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gft.veterinario.entities.Usuario;

public class UsuarioMapper {

	public static Usuario fromDTO(RegistroUsuarioDTO dto) {		
		return new Usuario(null, dto.getEmail(), new BCryptPasswordEncoder().encode(dto.getSenha()), null);
	}
	
	public static ConsultaUsuarioDTO fromEntity(Usuario usuario) {
		
		return new ConsultaUsuarioDTO(usuario.getCpf(), usuario.getPerfil().getNome());
		
	}
	
}
