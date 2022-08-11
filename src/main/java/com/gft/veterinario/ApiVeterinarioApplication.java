package com.gft.veterinario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gft.veterinario.entities.Veterinario;

@SpringBootApplication
public class ApiVeterinarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiVeterinarioApplication.class, args);
	}
	
	Veterinario veterinario = new Veterinario();
	

}
