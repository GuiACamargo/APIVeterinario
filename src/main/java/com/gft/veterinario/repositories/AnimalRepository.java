package com.gft.veterinario.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.veterinario.entities.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Page<Animal> findAll(Pageable pageable);
    
    Optional<Animal> findByNome(String nome);

}
