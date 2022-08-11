package com.gft.veterinario.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gft.veterinario.dto.error.ApiErrorDTO;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    List<String> errors = new ArrayList<String>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        errors.add(error.getField() + ": " + error.getDefaultMessage());
	    }
	    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	        errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	    }
	    
	    ApiErrorDTO apiError = new ApiErrorDTO("A validação falhou", errors, HttpStatus.BAD_REQUEST);
	    return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    String error = ex.getParameterName() + " está faltando";
	    
	    ApiErrorDTO apiError = new ApiErrorDTO("O envio do dado falhou", error, HttpStatus.BAD_REQUEST);
	    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    String error = "JSON mal formatado! Confira se não há uma vírgula extra no fim do último atributo";
	    
	    ApiErrorDTO apiError = new ApiErrorDTO("O envio do dado falhou", error, HttpStatus.BAD_REQUEST);
	    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
	    String error = ex.getName() + " deveria ser do tipo " + ex.getRequiredType().getName();

	    ApiErrorDTO apiError = new ApiErrorDTO("O envio do dado falhou", error, HttpStatus.BAD_REQUEST);
	    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
	    List<String> errors = new ArrayList<String>();
	    
	    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
	        errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
	    }

	    ApiErrorDTO apiError = new ApiErrorDTO("A validação falhou", errors, HttpStatus.BAD_REQUEST);
	    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@ExceptionHandler({MensagemException.class})
	public ResponseEntity<ApiErrorDTO> handleAPIException(MensagemException ex, WebRequest request) {
		
		String error = "Erro no sistema";
		
		ApiErrorDTO apiError = new ApiErrorDTO(ex.getMensagem(), error, HttpStatus.INTERNAL_SERVER_ERROR);
		
		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
		
	}
	
	@ExceptionHandler({DeleteException.class})
	public ResponseEntity<ApiErrorDTO> handleDeleteException(DeleteException ex, WebRequest request) {
		
		String error = "Erro ao deletar recurso";
		
		ApiErrorDTO apiError = new ApiErrorDTO(ex.getMensagem(), error, HttpStatus.INTERNAL_SERVER_ERROR);
		
		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
		
	}
	
	@ExceptionHandler({EntityMissingException.class})
	public ResponseEntity<ApiErrorDTO> handleEntityMissingException(EntityMissingException ex, WebRequest request) {
		
		String error = "Recurso não encontrado";
		
		ApiErrorDTO apiError = new ApiErrorDTO(ex.getMensagem(), error, HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
		
	}
	
	@ExceptionHandler({InvalidCpfException.class})
	public ResponseEntity<ApiErrorDTO> handleInvalidCpfException(InvalidCpfException ex, WebRequest request) {
		
		String error = "Cpf inválido, siga o padrão: 000.000.000-00";
		
		ApiErrorDTO apiError = new ApiErrorDTO(ex.getMensagem(), error, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
		
	}
	
	@ExceptionHandler({LackOfSpaceException.class})
	public ResponseEntity<ApiErrorDTO> handleLackOfSpaceException(LackOfSpaceException ex, WebRequest request) {
		
		String error = "Id fornecido já está ocupado";
		
		ApiErrorDTO apiError = new ApiErrorDTO(ex.getMensagem(), error, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
		
	}
	
	@ExceptionHandler({AnimalAlreadyExistsException.class})
	public ResponseEntity<ApiErrorDTO> handleAnimalAlreadyExistsException(AnimalAlreadyExistsException ex, WebRequest request) {
		
		String error = "Animal já foi adicionando ao cliente";
		
		ApiErrorDTO apiError = new ApiErrorDTO(ex.getMensagem(), error, HttpStatus.INTERNAL_SERVER_ERROR);
		
		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
		
	}
	
	@ExceptionHandler({MissingInformationsException.class})
	public ResponseEntity<ApiErrorDTO> handleMissingInformationsException(MissingInformationsException ex, WebRequest request) {
		
		String error = "Complete todos os dados";
		
		ApiErrorDTO apiError = new ApiErrorDTO(ex.getMensagem(), error, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
		
	}
	
	@ExceptionHandler({CantRetrieveInformationFromApiException.class})
	public ResponseEntity<ApiErrorDTO> handleCantRetrieveInformationFromApiException(CantRetrieveInformationFromApiException ex, WebRequest request) {
		
		String error = "Não foi possível obter os dados da API";
		
		ApiErrorDTO apiError = new ApiErrorDTO(ex.getMensagem(), error, HttpStatus.GATEWAY_TIMEOUT);
		
		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
		
	}
	
	@ExceptionHandler({AlreadyExistsException.class})
	public ResponseEntity<ApiErrorDTO> handleAlreadyExistsException(AlreadyExistsException ex, WebRequest request) {
		
		String error = "Já existe uma entidade com esse dado";
		
		ApiErrorDTO apiError = new ApiErrorDTO(ex.getMensagem(), error, HttpStatus.INTERNAL_SERVER_ERROR);
		
		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
		
	}
	
	@ExceptionHandler({InvalidLoginException.class})
	public ResponseEntity<ApiErrorDTO> handleInvalidLoginException(InvalidLoginException ex, WebRequest request) {
		
		String error = "Login ou senha incorretos";
		
		ApiErrorDTO apiError = new ApiErrorDTO(ex.getMensagem(), error, HttpStatus.UNAUTHORIZED);
		
		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
		
	}
}
