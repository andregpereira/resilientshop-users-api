package com.github.andregpereira.resilientshop.userapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andregpereira.resilientshop.userapi.dtos.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.dtos.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	// Registrar usuário
	@PostMapping
	public ResponseEntity<UsuarioDto> registrar(@RequestBody @Valid UsuarioRegistroDto usuarioRegistroDto) {
		UsuarioDto usuarioDto = usuarioService.registrar(usuarioRegistroDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);
	}

	@GetMapping("/ping")
	public String teste() {
		return "pong";
	}

	// Pesquisa por id
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDto> consultarPorId(@PathVariable Long id) {
		return usuarioService.consultarPorId(id).map(dto -> ResponseEntity.ok(dto))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Pesquisa por CPF
	@GetMapping("/cpf")
	public ResponseEntity<UsuarioDto> consultarPorCpf(@RequestBody @Valid UsuarioDto usuarioDto) {
		return usuarioService.consultarPorCpf(usuarioDto).map(dto -> ResponseEntity.ok(dto))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Pesquisa por nome
	@GetMapping("/nome")
	public ResponseEntity<UsuarioDto> consultarPorNome(@RequestBody @Valid UsuarioDto usuarioDto) {
		return usuarioService.consultarPorNome(usuarioDto).map(dto -> ResponseEntity.ok(dto))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

}
