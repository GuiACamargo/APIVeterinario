package com.gft.veterinario.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.veterinario.controllers.form.AutenticacaoForm;
import com.gft.veterinario.dto.token.TokenDTO;
import com.gft.veterinario.exceptions.InvalidLoginException;
import com.gft.veterinario.services.AutenticacaoService;

@RestController
@RequestMapping("v1/autenticacoes")
public class AutenticacaoController {

	@Autowired
	AutenticacaoService autenticacaoService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> autenticar(@RequestBody AutenticacaoForm authForm) {
		
		try {
			return ResponseEntity.ok(autenticacaoService.autenticar(authForm));
		} catch (AuthenticationException ae) {
			throw new InvalidLoginException("Confira os dados digitados, caso acredite ser um erro contate um administrador!");
		}
		
	}
	
}
