package com.gft.veterinario.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.veterinario.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByCpf(String Cpf);
	
	Page<Usuario> findAll(Pageable pageable);
}
