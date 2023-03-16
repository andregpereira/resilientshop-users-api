package com.github.andregpereira.resilientshop.userapi.services;

import java.security.InvalidParameterException;
import java.util.stream.Stream;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<String> erro400(MissingServletRequestParameterException e) {
		if (e.getMessage().contains("nome")) {
			return ResponseEntity.badRequest()
					.body("Desculpe, não foi possível realizar a busca por nome. Digite um nome e tente novamente.");
		}
		if (e.getMessage().contains("cpf")) {
			return ResponseEntity.badRequest()
					.body("Desculpe, não foi possível realizar a busca por CPF. Digite um CPF e tente novamente.");
		}
		return ResponseEntity.badRequest().body("Houve um erro");
	}

	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<String> erro400(InvalidParameterException e) {
		if (e.getMessage().contains("usuario_consulta_nome_tamanho_invalido")) {
			return ResponseEntity.badRequest().body(
					"Desculpe, não foi possível realizar a busca por nome. O nome informado deve ter, pelo menos, 2 caracteres.");
		} else if (e.getMessage().contains("usuario_consulta_nome_invalido")) {
			return ResponseEntity.badRequest()
					.body("Desculpe, não foi possível realizar a busca por nome. Digite um nome e tente novamente.");
		} else if (e.getMessage().contains("usuario_consulta_cpf_invalido")) {
			return ResponseEntity.badRequest().body(
					"Desculpe, não foi possível realizar a busca por CPF. O CPF não foi digitado corretamente. Verifique e tente novamente.");
		}
		return ResponseEntity.badRequest().body("Houve um erro.");
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> erro400(HttpMessageNotReadableException e) {
		return ResponseEntity.badRequest().body("Houve um erro");
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> erro404(EntityNotFoundException e) {
		if (e.getMessage().contains("usuario_nao_encontrado_nome")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Não foi possível encontrar um usuário com este nome. Verifique e tente novamente.");
		} else if (e.getMessage().contains("usuario_nao_encontrado_cpf")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Desculpe, não foi possível encontrar um usuário com este CPF. Verifique e tente novamente.");
		} else if (e.getMessage().contains("pais_nao_encontrado")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("País não encontrado na nossa base de dados.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dado não encontrado na nossa base de dados");
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<String> erro404(EmptyResultDataAccessException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("Nenhum usuário foi encontrado. Verifique e tente novamente.");
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	private ResponseEntity<String> erro409(DataIntegrityViolationException e) {
		if (e.getMessage().contains("usuario_existente")) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já cadastrado no nosso banco de dados.");
		}
		if (e.getMessage().contains("alterar_cpf")) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Você não pode alterar o campo CPF.");
		} else if (e.getMessage().contains("uc_cpf")) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já cadastrado no nosso banco de dados.");
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Houve um erro");
	}

}
