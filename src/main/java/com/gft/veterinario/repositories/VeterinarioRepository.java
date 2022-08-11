package com.gft.veterinario.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.veterinario.entities.Veterinario;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    Page<Veterinario> findAll(Pageable pageable);

    Optional<Veterinario> findByNome(String nome);
    
    Optional<Veterinario> findByCpf(String cpf);
}
