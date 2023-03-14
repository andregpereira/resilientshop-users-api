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

	// Teste
	@GetMapping("/ping")
	public String teste() {
		return "pong";
	}

//	// Pesquisa por id
//	@GetMapping("/lista")
//	public ResponseEntity<List<UsuarioDto>> consultarPorId() {
//		return ResponseEntity.ok(usuarioService.listar());
//	}

	// Atualizar usuário por id
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDto> atualizarPorId(@PathVariable Long id,
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
//	@GetMapping("/cpf")
//	public ResponseEntity<Page<UsuarioDto>> consultarPorCpf(@RequestParam String cpf,
//			@PageableDefault(sort = "nome", direction = Direction.ASC, page = 0, size = 20) Pageable pageable) {
//		return ResponseEntity.ok(usuarioService.consultarPorCpf(cpf, pageable));
//	}

	// Pesquisa por nome ou CPF
	@GetMapping
	public ResponseEntity<Page<UsuarioDto>> consultarPorNomeOuCpf(@RequestParam(required = false) String nome,
			@RequestParam(required = false) String sobrenome, @RequestParam(required = false) String cpf,
			@PageableDefault(sort = "nome", direction = Direction.ASC, page = 0, size = 20) Pageable pageable) {
		if (cpf != null) {
			return ResponseEntity.ok(usuarioService.consultarPorCpf(cpf, pageable));
		}
		return ResponseEntity.ok(usuarioService.consultarPorNome(nome, sobrenome, pageable));
	}

}
