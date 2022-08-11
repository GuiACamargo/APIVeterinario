package com.gft.veterinario.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.veterinario.entities.Atendimento;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    Page<Atendimento> findAll(Pageable pageable);

    Optional<Atendimento> findByData(String data);
}
