package com.gft.veterinario.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.veterinario.entities.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{

	Optional<Perfil> findByNome (String nome);
	
}
