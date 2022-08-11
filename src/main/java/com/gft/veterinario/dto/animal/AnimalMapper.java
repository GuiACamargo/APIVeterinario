package com.gft.veterinario.dto.animal;

import com.gft.veterinario.entities.Animal;

public class AnimalMapper {
	
	public static Animal fromDTO (RegistroAnimalDTO dto) {
		return new Animal(null, dto.getNome(), dto.getIdade(), null, null, null, dto.getDadosIniciaisDoAnimal());
	}
	
	public static ConsultaAnimalDTO fromEntity (Animal animal) {
		String dono;
		if(animal.getDono() != null) {
			dono = animal.getDono().getNome();
		} else {
			dono = null;
		}
		return new ConsultaAnimalDTO (animal.getId(), animal.getNome(), animal.getIdade(), animal.getRaca(), dono, animal.getAtendimentos(), animal.getDados());
	}

}
