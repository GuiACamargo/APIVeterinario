package com.gft.veterinario.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.gft.veterinario.dto.raca.RacaMapper;
import com.gft.veterinario.dto.raca.RegistroRacaDTO;
import com.gft.veterinario.dto.raca.dados.MostraRacaComImagemDTO;
import com.gft.veterinario.dto.raca.dados.MostraRacaDTO;
import com.gft.veterinario.dto.raca.gif.MostraGifDTO;
import com.gft.veterinario.dto.raca.imagem.MostraImagemDTO;
import com.gft.veterinario.entities.Raca;
import com.gft.veterinario.exceptions.CantRetrieveInformationFromApiException;
import com.gft.veterinario.exceptions.EntityMissingException;

import reactor.core.publisher.Flux;

@Service
public class RacaService {
	
	public Flux<MostraRacaComImagemDTO> buscaTodasAsRacas() {
		try {
			String uri = "https://api.thedogapi.com/v1/breeds/?api_key=78d56926-6be0-4d78-86d3-448f3b88eaf5";
			WebClient client = WebClient.create(uri);
			WebClient.ResponseSpec responseSpec = client.get().retrieve();
			Flux<MostraRacaComImagemDTO> responseBody = responseSpec.bodyToFlux(MostraRacaComImagemDTO.class);
			
			return responseBody;
		} catch (RuntimeException e) {
			throw new CantRetrieveInformationFromApiException("Não foi possível buscar as informações");
		}
	}
	
	public Flux<MostraRacaDTO> buscaRacaPorNome(String nome) {
		try {
			String uri = "https://api.thedogapi.com/v1/breeds/search?api_key=78d56926-6be0-4d78-86d3-448f3b88eaf5&q="+nome;
			WebClient client = WebClient.create(uri);
			WebClient.ResponseSpec responseSpec = client.get().retrieve();
			Flux<MostraRacaDTO> responseBody = responseSpec.bodyToFlux(MostraRacaDTO.class);
			
			return responseBody;
		} catch (RuntimeException e) {
			throw new CantRetrieveInformationFromApiException("Não foi possível buscar as informações");
		}
		
	}
	
	public Raca criaRacaPelaBusca (Long id) {
		try {
			String uri = "https://api.thedogapi.com/v1/breeds/"+id+"?api_key=78d56926-6be0-4d78-86d3-448f3b88eaf5";
			WebClient client = WebClient.create(uri);
			WebClient.ResponseSpec responseSpec = client.get().retrieve();
			Flux<RegistroRacaDTO> responseBody = responseSpec.bodyToFlux(RegistroRacaDTO.class);
			List<RegistroRacaDTO> listaDTO = responseBody.collectList().block();
			Raca raca = RacaMapper.fromDTO(listaDTO.get(0));
			if(raca.getNome() != null) {
				return raca;
			} else {
				throw new EntityMissingException("Não existe raça com esse ID");
			}
			
		} catch (RuntimeException e) {
			throw new CantRetrieveInformationFromApiException("Não foi possível buscar as informações");
		}	
	}
	
	public Flux<MostraRacaDTO> buscaRacaPorId(Long id) {
		try {
			String uri = "https://api.thedogapi.com/v1/breeds/"+id+"?api_key=78d56926-6be0-4d78-86d3-448f3b88eaf5";
			WebClient client = WebClient.create(uri);
			WebClient.ResponseSpec responseSpec = client.get().retrieve();
			Flux<MostraRacaDTO> responseBody = responseSpec.bodyToFlux(MostraRacaDTO.class);
			
			return responseBody;
		} catch (RuntimeException e) {
			throw new CantRetrieveInformationFromApiException("Não foi possível buscar as informações");
		}
	}
	
	public Flux<MostraImagemDTO> buscaImagensAleatoriasDasRacas() {
		try {
			String uri = "https://api.thedogapi.com/v1/images/search/?api_key=78d56926-6be0-4d78-86d3-448f3b88eaf5&mime_types=png,jpg";
			WebClient client = WebClient.create(uri);
			WebClient.ResponseSpec responseSpec = client.get().retrieve();
			Flux<MostraImagemDTO> responseBody = responseSpec.bodyToFlux(MostraImagemDTO.class);

			return responseBody;
		} catch (RuntimeException e) {
			throw new CantRetrieveInformationFromApiException("Não foi possível buscar as informações");
		}	
	}
	
	public Flux<MostraGifDTO> buscaGifsAleatorios() {
		try {
			String uri = "https://api.thedogapi.com/v1/images/search/?api_key=78d56926-6be0-4d78-86d3-448f3b88eaf5&mime_types=gif";
			WebClient client = WebClient.create(uri);
			WebClient.ResponseSpec responseSpec = client.get().retrieve();
			Flux<MostraGifDTO> responseBody = responseSpec.bodyToFlux(MostraGifDTO.class);
			
			return responseBody;
		} catch (RuntimeException e) {
			throw new CantRetrieveInformationFromApiException("Não foi possível buscar as informações");
		}	
	}
	
	public Flux<MostraImagemDTO> buscaImagensDaRacaPeloIdDaRaca(Long id) {
		try {
			String uri = "https://api.thedogapi.com/v1/images/search/?api_key=78d56926-6be0-4d78-86d3-448f3b88eaf5&breed_id="+id;
			WebClient client = WebClient.create(uri);
			WebClient.ResponseSpec responseSpec = client.get().retrieve();
			Flux<MostraImagemDTO> responseBody = responseSpec.bodyToFlux(MostraImagemDTO.class);
			
			return responseBody;
		} catch (RuntimeException e) {
			throw new CantRetrieveInformationFromApiException("Não foi possível buscar as informações");
		}		
	}
	
	public Flux<MostraImagemDTO> buscaGifOuImagemPorId (String id) {
		try {
			String uri = "https://api.thedogapi.com/v1/images/"+id+"?api_key=78d56926-6be0-4d78-86d3-448f3b88eaf5";
			WebClient client = WebClient.create(uri);
			WebClient.ResponseSpec responseSpec = client.get().retrieve();
			Flux<MostraImagemDTO> responseBody = responseSpec.bodyToFlux(MostraImagemDTO.class);
			
			return responseBody;
		} catch (RuntimeException e) {
			throw new CantRetrieveInformationFromApiException("Não foi possível buscar as informações");
		}	
	}
}
