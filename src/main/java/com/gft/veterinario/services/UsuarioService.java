package com.gft.veterinario.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gft.veterinario.dto.usuario.MudarUsuarioDTO;
import com.gft.veterinario.entities.Perfil;
import com.gft.veterinario.entities.Usuario;
import com.gft.veterinario.exceptions.DeleteException;
import com.gft.veterinario.exceptions.EntityMissingException;
import com.gft.veterinario.exceptions.MissingInformationsException;
import com.gft.veterinario.repositories.PerfilRepository;
import com.gft.veterinario.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{
	
	@Autowired
	@Lazy
	private AuthenticationManager authManager;
	
	private final UsuarioRepository usuarioRepository;
	
	private final PerfilRepository perfilRepository;
	
	public UsuarioService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
		this.usuarioRepository = usuarioRepository;
		this.perfilRepository = perfilRepository;
	}

	public Page<Usuario> listarTodosOsUsuarios (Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}
	
	public Usuario buscarUsuarioPorEmail(String cpf) {
		Optional<Usuario> optional = usuarioRepository.findByCpf(cpf);
		
		if(optional.isEmpty()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		return optional.get();
	}
	
	public Usuario atualizarUsuario(MudarUsuarioDTO dto) {
		Optional<Usuario> usuarioOriginal = usuarioRepository.findByCpf(dto.getUsuario());
		Usuario usuario = new Usuario();
		Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsuario(), dto.getSenhaInicial()));	
		
		if(usuarioOriginal.isPresent()) {
			if(authenticate.isAuthenticated()) {
				usuario.setCpf(usuarioOriginal.get().getCpf());
				usuario.setSenha(new BCryptPasswordEncoder().encode(dto.getSenhaNova()));
				usuario.setPerfil(usuarioOriginal.get().getPerfil());
				usuario.setId(usuarioOriginal.get().getId());

				return usuarioRepository.save(usuario);
			} else {
				throw new MissingInformationsException("A senha inicial fornecida está errada!");
			}
		} else {
			throw new EntityMissingException("Usuario inicial não encontrado!"); 
		}
	}
	
	public void excluirUsuario(Long id) {
		Usuario usuario = buscarUsuarioPorId(id);
		if (usuario != null) {
			usuarioRepository.delete(usuario);
		} else {
			throw new DeleteException("Usuário não encontrado para exclusão");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return buscarUsuarioPorEmail(username);
	}

	public Usuario buscarUsuarioPorId(Long idUsuario) {
		Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
		
		return optional.orElseThrow(() -> new EntityMissingException("Entidade não encontrada!"));
	}
	
	public Usuario salvarUsuario(Usuario usuario) {
		Optional<Perfil> perfil = perfilRepository.findByNome("veterinario");
		if(perfil.isPresent()) {
			usuario.setPerfil(perfil.get());
		} else {
			Perfil perfilVeterinario = new Perfil(null, "veterinario");
			usuario.setPerfil(perfilVeterinario);
		}
		
		return usuarioRepository.save(usuario);
		
	}

}
