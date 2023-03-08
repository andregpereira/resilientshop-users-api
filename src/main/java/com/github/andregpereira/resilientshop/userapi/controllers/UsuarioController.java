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

	@GetMapping("/ping")
	public String teste() {
		return "pong";
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDto> consultarPorId(@PathVariable Long id) {
		return usuarioService.consultarPorId(id).map(usuarioDto -> ResponseEntity.ok(usuarioDto))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<UsuarioDto> registrar(@RequestBody @Valid UsuarioRegistroDto dto) {
		UsuarioDto usuarioDto = usuarioService.registrar(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);
	}

}
