package com.gft.veterinario.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.veterinario.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Page<Cliente> findAll(Pageable pageable);

    Optional<Cliente> findByNome(String nome);
    
    Optional<Cliente> findByCpf(String cpf);
}
