package com.gft.veterinario.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gft.veterinario.dto.raca.dados.MostraRacaComImagemDTO;
import com.gft.veterinario.dto.raca.dados.MostraRacaDTO;
import com.gft.veterinario.dto.raca.gif.MostraGifDTO;
import com.gft.veterinario.dto.raca.imagem.MostraImagemDTO;
import com.gft.veterinario.services.RacaService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("v1/raca")
public class RacaController {
	
	private final RacaService racaService;

	public RacaController(RacaService racaService) {
		this.racaService = racaService;
	}

	@GetMapping("busca/todas")
	private ResponseEntity<Flux<MostraRacaComImagemDTO>> buscaTodasAsRacas() {
		return ResponseEntity.ok(racaService.buscaTodasAsRacas());
	}
	
	@GetMapping("busca/porNome")
	private ResponseEntity<Flux<MostraRacaDTO>> buscaRacaPorNome(@RequestParam String nome) {
		return ResponseEntity.ok(racaService.buscaRacaPorNome(nome));
	}
	
	@GetMapping("busca/{id}")
	private ResponseEntity<Flux<MostraRacaDTO>> buscaRacaPorId(@PathVariable Long id) {
		return ResponseEntity.ok(racaService.buscaRacaPorId(id));
	}
	
	@GetMapping("gif/aleatorio")
	private ResponseEntity<Flux<MostraGifDTO>> buscaGifAleatorio() {
		return ResponseEntity.ok(racaService.buscaGifsAleatorios());
	}
	
	@GetMapping("imagem/aleatoria")
	private ResponseEntity<Flux<MostraImagemDTO>> buscaImagemAleatoriaDeUmaRaca() {
		return ResponseEntity.ok(racaService.buscaImagensAleatoriasDasRacas());
	}
	
	@GetMapping("imagem/peloIdRaca")
	private ResponseEntity<Flux<MostraImagemDTO>> buscaImagensDaRacaPeloIdDaRaca(@RequestParam Long raca_id) {
		return ResponseEntity.ok(racaService.buscaImagensDaRacaPeloIdDaRaca(raca_id));
	}
	
	@GetMapping("imagemGif/busca/{id}")
	private ResponseEntity<Flux<MostraImagemDTO>> buscaGifOuImagemPorId(@RequestParam String id) {
		return ResponseEntity.ok(racaService.buscaGifOuImagemPorId(id));
	}
	
}
