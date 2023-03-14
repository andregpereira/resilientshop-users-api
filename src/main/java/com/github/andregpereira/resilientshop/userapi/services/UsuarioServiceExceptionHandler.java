package com.github.andregpereira.resilientshop.userapi.services;

import java.util.stream.Stream;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class UsuarioServiceExceptionHandler {

	private record DadoInvalido(String campo, String mensagem) {
		public DadoInvalido(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Stream<DadoInvalido>> erro400(MethodArgumentNotValidException e) {
		Stream<FieldError> erros = e.getFieldErrors().stream();
		return ResponseEntity.badRequest().body(erros.map(DadoInvalido::new));
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> erro404(EntityNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado na nossa base de dados");
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<String> erro404(EmptyResultDataAccessException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum usuário encontrado");
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	private ResponseEntity<String> erro409(DataIntegrityViolationException e) {
		if (e.getMessage().contains("alterar_cpf")) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Você não pode alterar o campo CPF");
		} else if (e.getMessage().contains("uc_cpf")) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já castrado no nosso banco de dados");
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Houve um erro");
	}

}
