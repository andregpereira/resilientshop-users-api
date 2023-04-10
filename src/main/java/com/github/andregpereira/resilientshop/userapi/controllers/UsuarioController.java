package com.github.andregpereira.resilientshop.userapi.controllers;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.services.UsuarioConsultaService;
import com.github.andregpereira.resilientshop.userapi.services.UsuarioManutencaoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
        log.info("Criando usuário...");
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioManutencaoService.registrar(usuarioRegistroDto));
    }

    // Atualizar usuário por id
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDetalhesDto> atualizar(@PathVariable Long id,
            @RequestBody @Valid UsuarioAtualizacaoDto usuarioAtualizacaoDto) {
        log.info("Atualizando usuário...");
        return ResponseEntity.ok(usuarioManutencaoService.atualizar(id, usuarioAtualizacaoDto));
    }

    // Remoção lógica de usuário por id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> desativar(@PathVariable Long id) {
        log.info("Desativando usuário...");
        return ResponseEntity.ok(usuarioManutencaoService.desativar(id));
    }

    // Reativar por id
    @PatchMapping("/reativar/{id}")
    public ResponseEntity<String> reativar(@PathVariable Long id) {
        log.info("Reativando usuário...");
        return ResponseEntity.ok(usuarioManutencaoService.reativar(id));
    }

    // Pesquisar por id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDetalhesDto> consultarPorId(@PathVariable Long id) {
        log.info("Consultando usuário por id...");
        return ResponseEntity.ok(usuarioConsultaService.consultarPorId(id));
    }

    // Pesquisar por CPF
    @GetMapping("/cpf")
    public ResponseEntity<UsuarioDetalhesDto> consultarPorCpf(@RequestParam String cpf) {
        log.info("Consultando usuário por CPF...");
        return ResponseEntity.ok(usuarioConsultaService.consultarPorCpf(cpf));
    }

    // Pesquisar por nome
    @GetMapping("/nome")
    public ResponseEntity<Page<UsuarioDto>> consultarPorNome(@RequestParam(required = false) String nome,
            @RequestParam(required = false) String sobrenome,
            @PageableDefault(sort = "nome", direction = Direction.ASC, page = 0, size = 10) Pageable pageable) {
        log.info("Consultando usuário por nome...");
        return ResponseEntity.ok(usuarioConsultaService.consultarPorNome(nome, sobrenome, pageable));
    }

}
