package com.github.andregpereira.resilientshop.userapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	// Registrar usuário
	@PostMapping
	public ResponseEntity<UsuarioDetalhesDto> registrar(@RequestBody @Valid UsuarioRegistroDto usuarioRegistroDto) {
		UsuarioDetalhesDto usuarioDetalhesDto = usuarioService.registrar(usuarioRegistroDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDetalhesDto);
	}

	// Teste
	@GetMapping("/ping")
	public String teste() {
		return "pong";
	}

	// Atualizar usuário por id
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDetalhesDto> atualizarPorId(@PathVariable Long id,
			@RequestBody @Valid UsuarioRegistroDto usuarioRegistroDto) {
		return ResponseEntity.ok(usuarioService.atualizar(id, usuarioRegistroDto));
	}

	// Deletar por id
	@DeleteMapping("/{id}")
	private ResponseEntity<String> deletar(@PathVariable Long id) {
		return ResponseEntity.ok(usuarioService.deletar(id));
	}

	// Pesquisa por id
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDto> consultarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(usuarioService.consultarPorId(id));
	}

	// Pesquisa por CPF
	@GetMapping("/cpf")
	public ResponseEntity<UsuarioDto> consultarPorCpf(@RequestParam(required = true) String cpf) {
		return ResponseEntity.ok(usuarioService.consultarPorCpf(cpf));
	}

	// Pesquisa por nome
	@GetMapping("/nome")
	public ResponseEntity<Page<UsuarioDto>> consultarPorNomeOuCpf(@RequestParam(required = true) String nome,
			@RequestParam(required = false) String sobrenome,
			@PageableDefault(sort = "nome", direction = Direction.ASC, page = 0, size = 10) Pageable pageable) {
		return ResponseEntity.ok(usuarioService.consultarPorNome(nome, sobrenome, pageable));
	}

}
