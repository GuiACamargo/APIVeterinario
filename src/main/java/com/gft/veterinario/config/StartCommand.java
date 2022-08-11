package com.gft.veterinario.config;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

import com.gft.veterinario.dto.raca.RacaMapper;
import com.gft.veterinario.dto.raca.RegistroRacaDTO;
import com.gft.veterinario.entities.Animal;
import com.gft.veterinario.entities.Atendimento;
import com.gft.veterinario.entities.Cliente;
import com.gft.veterinario.entities.DadosDoAnimal;
import com.gft.veterinario.entities.Perfil;
import com.gft.veterinario.entities.Raca;
import com.gft.veterinario.entities.Usuario;
import com.gft.veterinario.entities.Veterinario;
import com.gft.veterinario.repositories.AnimalRepository;
import com.gft.veterinario.repositories.AtendimentoRepository;
import com.gft.veterinario.repositories.ClienteRepository;
import com.gft.veterinario.repositories.PerfilRepository;
import com.gft.veterinario.repositories.RacaRepository;
import com.gft.veterinario.repositories.UsuarioRepository;
import com.gft.veterinario.repositories.VeterinarioRepository;

import reactor.core.publisher.Flux;

@Configuration
public class StartCommand implements CommandLineRunner {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	PerfilRepository perfilRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	VeterinarioRepository veterinarioRepository;
	
	@Autowired
	AnimalRepository animalRepository;
	
	@Autowired
	AtendimentoRepository atendimentoRepository;
	
	@Autowired
	RacaRepository racaRepository;

	@Override
	public void run(String... args) throws Exception {
		
		String uri = "https://api.thedogapi.com/v1/breeds/115?api_key=78d56926-6be0-4d78-86d3-448f3b88eaf5";
		WebClient client = WebClient.create(uri);
		WebClient.ResponseSpec responseSpec = client.get().retrieve();
		Flux<RegistroRacaDTO> responseBody = responseSpec.bodyToFlux(RegistroRacaDTO.class);
		List<RegistroRacaDTO> listaDTO = responseBody.collectList().block();
		Raca raca = RacaMapper.fromDTO(listaDTO.get(0));
		
		DadosDoAnimal dados1 = new DadosDoAnimal(9, 15, 16);
		DadosDoAnimal dados2 = new DadosDoAnimal(8, 16, 17);
		
		Animal a1 = new Animal(null, "Bolt", 9, null, null, null, dados1);
		Cliente c1 = new Cliente(null, "João", "859.111.510-49", null, null);
		Veterinario v1 = new Veterinario(null, "Marcos", "859.111.510-49", "Médio porte", null);
		Atendimento atendimento = new Atendimento(null, "26/08/2022", "16:08", "Saudável", "Fazer caminhadas", null, null, null, dados2, null, null, null);
		
		if(clienteRepository.findByNome("João").isEmpty()) {
			clienteRepository.save(c1);
		}
		
		if(veterinarioRepository.findByNome("Marcos").isEmpty()) {
			veterinarioRepository.save(v1);
		}
		
		if(animalRepository.findByNome("Bolt").isEmpty()) {
			Optional<Raca> racaAnimal = racaRepository.findByNome("Yorkshire Terrier");			
			if (racaAnimal.isPresent()) {
				a1.setRaca(racaAnimal.get());
			} else {
				racaRepository.save(raca);
				a1.setRaca(raca);
			}
			Optional<Cliente> cliente = clienteRepository.findByNome("João");
			if (cliente.isPresent()) {
				a1.setDono(cliente.get());		
			}
			animalRepository.save(a1);
		}
		
		if(atendimentoRepository.findByData("26/08").isEmpty()) {
			Optional<Cliente> cliente = clienteRepository.findByNome("João");
			Optional<Veterinario> veterinario = veterinarioRepository.findByNome("Marcos");
			Optional<Animal> animal = animalRepository.findByNome("Bolt");
			if(cliente.isPresent()) {
				atendimento.setCliente(cliente.get());
				atendimento.setNomeCliente(cliente.get().getNome());
			}
			if(veterinario.isPresent()) {
				atendimento.setVeterinario(veterinario.get());
				atendimento.setNomeVeterinario(veterinario.get().getNome());
			}
			if(animal.isPresent()) {
				atendimento.setAnimal(animal.get());
				atendimento.setNomeAnimal(animal.get().getNome());
			}

			atendimentoRepository.save(atendimento);
		}
		
		Perfil p1 = new Perfil(null, "veterinario");
		Perfil p2 = new Perfil(null, "cliente");
		
		if (perfilRepository.findByNome("veterinario").isEmpty()) {
			perfilRepository.save(p1);
		}
		if (perfilRepository.findByNome("cliente").isEmpty()) {
			perfilRepository.save(p2);
		}
		
		Usuario u1 = new Usuario(null, "clienteInicial", new BCryptPasswordEncoder().encode("123"), null);
		Usuario u2 = new Usuario(null, "veterinarioInicial", new BCryptPasswordEncoder().encode("123"), null);
		
		if (usuarioRepository.findByCpf("clienteInicial").isEmpty()) {
			Optional<Perfil> perfil = perfilRepository.findByNome("cliente");
			if(perfil.isPresent()) {
				u1.setPerfil(perfil.get());
			}
			usuarioRepository.save(u1);
		}
		if (usuarioRepository.findByCpf("veterinarioInicial").isEmpty()) {
			Optional<Perfil> perfil = perfilRepository.findByNome("veterinario");
			if(perfil.isPresent()) {
				u2.setPerfil(perfil.get());
			}
			usuarioRepository.save(u2);
		}
	}
}
