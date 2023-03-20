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
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.github.andregpereira.resilientshop.userapi.services.UsuarioConsultaService;
import com.github.andregpereira.resilientshop.userapi.services.UsuarioManutencaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioManutencaoService usuarioManutencaoService;

	@Autowired
	private UsuarioConsultaService usuarioConsultaService;

	// Registrar usuário
	@PostMapping
	public ResponseEntity<UsuarioDetalhesDto> registrar(@RequestBody @Valid UsuarioRegistroDto usuarioRegistroDto) {
		UsuarioDetalhesDto usuarioDetalhesDto = usuarioManutencaoService.registrar(usuarioRegistroDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDetalhesDto);
	}

	// Atualizar usuário por id
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDetalhesDto> atualizar(@PathVariable Long id,
			@RequestBody @Valid UsuarioRegistroDto usuarioRegistroDto) {
		return ResponseEntity.ok(usuarioManutencaoService.atualizar(id, usuarioRegistroDto));
	}

	// Desativar por id
	@DeleteMapping("/{id}")
	private ResponseEntity<String> desativar(@PathVariable Long id) {
		return ResponseEntity.ok(usuarioManutencaoService.desativar(id));
	}

	// Ativar por id
	@PatchMapping("/{id}")
	private ResponseEntity<String> ativar(@PathVariable Long id) {
		return ResponseEntity.ok(usuarioManutencaoService.ativar(id));
	}

	// Pesquisar por id
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDetalhesDto> consultarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(usuarioConsultaService.consultarPorId(id));
	}

	// Pesquisar por CPF
	@GetMapping("/cpf")
	public ResponseEntity<UsuarioDetalhesDto> consultarPorCpf(@RequestParam(required = true) String cpf) {
		return ResponseEntity.ok(usuarioConsultaService.consultarPorCpf(cpf));
	}

	// Pesquisar por nome
	@GetMapping("/nome")
	public ResponseEntity<Page<UsuarioDto>> consultarPorNome(@RequestParam(required = true) String nome,
			@RequestParam(required = false) String sobrenome,
			@PageableDefault(sort = "nome", direction = Direction.DESC, page = 0, size = 10) Pageable pageable) {
		return ResponseEntity.ok(usuarioConsultaService.consultarPorNome(nome, sobrenome, pageable));
	}

}
