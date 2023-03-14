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
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<String> erro404(EmptyResultDataAccessException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum usuário encontrado");
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	private ResponseEntity<String> erro409(DataIntegrityViolationException e) {
		if (e.getMessage().contains("uc_telefone")) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("O telefone já está em uso");
		} else if (e.getMessage().contains("uc_cpf")) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuário com o CPF informado");
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Houve um erro");
	}

}
