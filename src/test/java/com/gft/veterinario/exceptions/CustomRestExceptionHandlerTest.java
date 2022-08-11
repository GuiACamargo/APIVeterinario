package com.gft.veterinario.exceptions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.gft.veterinario.dto.error.ApiErrorDTO;

@SpringBootTest
class CustomRestExceptionHandlerTest {
	
	@InjectMocks
	private CustomRestExceptionHandler handler;
	
	private MockHttpServletRequest servletRequest;
	
	@BeforeEach
	void setUp() throws Exception {
		startAll();
	}
	
	private void startAll() {
		servletRequest = new MockHttpServletRequest();
	}
	
	@Test
	void quandoMethodArgumentTypeMismatchRetornarUmResponseEntity() {
		WebRequest webRequest = new ServletWebRequest(servletRequest);
		ResponseEntity<Object> response = handler
				.handleMethodArgumentTypeMismatch(new MethodArgumentTypeMismatchException(null, String.class, null, null, null), webRequest);
		
		assertAll(() -> assertNotNull(response),
				() -> assertNotNull(response.getBody()),
				() -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
				() -> assertEquals(ResponseEntity.class, response.getClass()),
				() -> assertEquals(ApiErrorDTO.class, response.getBody().getClass()),
				() -> assertEquals(400, response.getStatusCodeValue()));
	}

	@Test
	void quandoMensagemExceptionRetornarUmResponseEntity() {
		MensagemException mensagem = new MensagemException("mudar");
		mensagem.setMensagem("Erro no sistema!");
		WebRequest webRequest = new ServletWebRequest(servletRequest);
		ResponseEntity<ApiErrorDTO> response = handler
				.handleAPIException(mensagem, webRequest);
		
		
		assertAll(() -> assertNotNull(response),
				  () -> assertNotNull(response.getBody()),
				  () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()),
				  () -> assertEquals(ResponseEntity.class, response.getClass()),
				  () -> assertEquals(ApiErrorDTO.class, response.getBody().getClass()),
				  () -> assertEquals("Erro no sistema!", response.getBody().getMessage()),
				  () -> assertEquals(500, response.getStatusCodeValue()));
	}
	
	@Test
	void quandoDeleteExceptionRetornarUmResponseEntity() {
		WebRequest webRequest = new ServletWebRequest(servletRequest);
		ResponseEntity<ApiErrorDTO> response = handler
				.handleDeleteException(new DeleteException("Erro ao deletar!"), webRequest);
		
		assertAll(() -> assertNotNull(response),
				() -> assertNotNull(response.getBody()),
				() -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()),
				() -> assertEquals(ResponseEntity.class, response.getClass()),
				() -> assertEquals(ApiErrorDTO.class, response.getBody().getClass()),
				() -> assertEquals("Erro ao deletar!", response.getBody().getMessage()),
				() -> assertEquals(500, response.getStatusCodeValue()));
	}
	
	@Test
	void quandoEntityMissingExceptionRetornarUmResponseEntity() {
		WebRequest webRequest = new ServletWebRequest(servletRequest);
		ResponseEntity<ApiErrorDTO> response = handler
				.handleEntityMissingException(new EntityMissingException("Erro ao buscar objeto!"), webRequest);
		
		assertAll(() -> assertNotNull(response),
				() -> assertNotNull(response.getBody()),
				() -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
				() -> assertEquals(ResponseEntity.class, response.getClass()),
				() -> assertEquals(ApiErrorDTO.class, response.getBody().getClass()),
				() -> assertEquals("Erro ao buscar objeto!", response.getBody().getMessage()),
				() -> assertEquals(404, response.getStatusCodeValue()));
	}
	
	@Test
	void quandoInvalidCpfExceptionRetornarUmResponseEntity() {
		WebRequest webRequest = new ServletWebRequest(servletRequest);
		ResponseEntity<ApiErrorDTO> response = handler
				.handleInvalidCpfException(new InvalidCpfException("CPF não encontrado!"), webRequest);
		
		assertAll(() -> assertNotNull(response),
				() -> assertNotNull(response.getBody()),
				() -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
				() -> assertEquals(ResponseEntity.class, response.getClass()),
				() -> assertEquals(ApiErrorDTO.class, response.getBody().getClass()),
				() -> assertEquals("CPF não encontrado!", response.getBody().getMessage()),
				() -> assertEquals(400, response.getStatusCodeValue()));
	}
	
	@Test
	void quandoLackOfSpaceExceptionRetornarUmResponseEntity() {
		WebRequest webRequest = new ServletWebRequest(servletRequest);
		ResponseEntity<ApiErrorDTO> response = handler
				.handleLackOfSpaceException(new LackOfSpaceException("Espaço já ocupado!"), webRequest);
		
		assertAll(() -> assertNotNull(response),
				() -> assertNotNull(response.getBody()),
				() -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
				() -> assertEquals(ResponseEntity.class, response.getClass()),
				() -> assertEquals(ApiErrorDTO.class, response.getBody().getClass()),
				() -> assertEquals("Espaço já ocupado!", response.getBody().getMessage()),
				() -> assertEquals(400, response.getStatusCodeValue()));
	}
	
	@Test
	void quandoAnimalAlreadyExistsExceptionRetornarUmResponseEntity() {
		WebRequest webRequest = new ServletWebRequest(servletRequest);
		ResponseEntity<ApiErrorDTO> response = handler
				.handleAnimalAlreadyExistsException(new AnimalAlreadyExistsException("Animal ja adicionado!"), webRequest);
		
		assertAll(() -> assertNotNull(response),
				() -> assertNotNull(response.getBody()),
				() -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()),
				() -> assertEquals(ResponseEntity.class, response.getClass()),
				() -> assertEquals(ApiErrorDTO.class, response.getBody().getClass()),
				() -> assertEquals("Animal ja adicionado!", response.getBody().getMessage()),
				() -> assertEquals(500, response.getStatusCodeValue()));
	}
	
	@Test
	void quandoMissingInformationsExceptionRetornarUmResponseEntity() {
		WebRequest webRequest = new ServletWebRequest(servletRequest);
		ResponseEntity<ApiErrorDTO> response = handler
				.handleMissingInformationsException(new MissingInformationsException("Informações faltando!"), webRequest);
		
		assertAll(() -> assertNotNull(response),
				() -> assertNotNull(response.getBody()),
				() -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
				() -> assertEquals(ResponseEntity.class, response.getClass()),
				() -> assertEquals(ApiErrorDTO.class, response.getBody().getClass()),
				() -> assertEquals("Informações faltando!", response.getBody().getMessage()),
				() -> assertEquals(400, response.getStatusCodeValue()));
	}
	
	@Test
	void quandoCantRetrieveInformationFromApiExceptionRetornarUmResponseEntity() {
		WebRequest webRequest = new ServletWebRequest(servletRequest);
		ResponseEntity<ApiErrorDTO> response = handler
				.handleCantRetrieveInformationFromApiException(new CantRetrieveInformationFromApiException("Não foi possível acessar a API"), webRequest);
		
		assertAll(() -> assertNotNull(response),
				() -> assertNotNull(response.getBody()),
				() -> assertEquals(HttpStatus.GATEWAY_TIMEOUT, response.getStatusCode()),
				() -> assertEquals(ResponseEntity.class, response.getClass()),
				() -> assertEquals(ApiErrorDTO.class, response.getBody().getClass()),
				() -> assertEquals("Não foi possível acessar a API", response.getBody().getMessage()),
				() -> assertEquals(504, response.getStatusCodeValue()));
	}
	
	@Test
	void quandoAlreadyExistsExceptionRetornarUmResponseEntity() {
		WebRequest webRequest = new ServletWebRequest(servletRequest);
		ResponseEntity<ApiErrorDTO> response = handler
				.handleAlreadyExistsException(new AlreadyExistsException("Objeto já existe!"), webRequest);
		
		assertAll(() -> assertNotNull(response),
				() -> assertNotNull(response.getBody()),
				() -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()),
				() -> assertEquals(ResponseEntity.class, response.getClass()),
				() -> assertEquals(ApiErrorDTO.class, response.getBody().getClass()),
				() -> assertEquals("Objeto já existe!", response.getBody().getMessage()),
				() -> assertEquals(500, response.getStatusCodeValue()));
	}
	
	@Test
	void quandoInvalidLoginExceptionRetornarUmResponseEntity() {
		WebRequest webRequest = new ServletWebRequest(servletRequest);
		ResponseEntity<ApiErrorDTO> response = handler
				.handleInvalidLoginException(new InvalidLoginException("Login inválido!"), webRequest);
		
		assertAll(() -> assertNotNull(response),
				() -> assertNotNull(response.getBody()),
				() -> assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode()),
				() -> assertEquals(ResponseEntity.class, response.getClass()),
				() -> assertEquals(ApiErrorDTO.class, response.getBody().getClass()),
				() -> assertEquals("Login inválido!", response.getBody().getMessage()),
				() -> assertEquals(401, response.getStatusCodeValue()));
	}
}
