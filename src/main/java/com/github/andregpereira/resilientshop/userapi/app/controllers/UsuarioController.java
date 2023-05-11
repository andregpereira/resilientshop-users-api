package com.github.andregpereira.resilientshop.userapi.app.controllers;

import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.app.services.UsuarioConsultaService;
import com.github.andregpereira.resilientshop.userapi.app.services.UsuarioManutencaoService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Validated
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioManutencaoService usuarioManutencaoService;
    private final UsuarioConsultaService usuarioConsultaService;

    // Registrar usuário
    @PostMapping
    public ResponseEntity<UsuarioDetalhesDto> registrar(@RequestBody @Valid UsuarioRegistroDto dto,
            UriComponentsBuilder uriBuilder) {
        log.info("Criando usuário...");
        UsuarioDetalhesDto usuario = usuarioManutencaoService.registrar(dto);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.id()).toUri();
        log.info("Usuário criado com sucesso");
        return ResponseEntity.created(uri).body(usuario);
    }

    // Atualizar usuário por id
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDetalhesDto> atualizar(@PathVariable Long id,
            @RequestBody @Valid UsuarioAtualizacaoDto dto) {
        log.info("Atualizando usuário...");
        return ResponseEntity.ok(usuarioManutencaoService.atualizar(id, dto));
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

    // Listar usuários
    @GetMapping
    public ResponseEntity<Page<UsuarioDto>> listar(
            @Parameter(hidden = true) @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10)
            Pageable pageable) {
        log.info("Listando usuários...");
        return ResponseEntity.ok(usuarioConsultaService.listar(pageable));
    }

    // Pesquisar por id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDetalhesDto> consultarPorId(@PathVariable Long id) {
        log.info("Consultando usuário por id {}...", id);
        return ResponseEntity.ok(usuarioConsultaService.consultarPorId(id));
    }

    // Pesquisar por CPF
    @GetMapping("/cpf")
    public ResponseEntity<UsuarioDetalhesDto> consultarPorCpf(@RequestParam
    @Pattern(message = "CPF inválido. Formatos aceitos: xxx.xxx.xxx-xx, xxxxxxxxx-xx ou xxxxxxxxxxx",
            regexp = "^(\\d{3}[.]\\d{3}[.]\\d{3}-\\d{2})|(\\d{9}-\\d{2})|(\\d{11})$") String cpf) {
        log.info("Consultando usuário por CPF...");
        return ResponseEntity.ok(usuarioConsultaService.consultarPorCpf(cpf.replace(".", "").replace("-", "")));
    }

    // Pesquisar por nome
    @GetMapping("/nome")
    public ResponseEntity<Page<UsuarioDto>> consultarPorNome(
            @RequestParam @Size(message = "O nome deve ter pelo menos 2 caracteres", min = 2) String nome,
            @RequestParam(required = false) String sobrenome,
            @PageableDefault(sort = "nome", direction = Direction.ASC, page = 0, size = 10) Pageable pageable) {
        log.info("Consultando usuário por nome...");
        return ResponseEntity.ok(usuarioConsultaService.consultarPorNome(nome.trim(), Optional.ofNullable(
                sobrenome).map(String::trim).orElse(""), pageable));
    }

}
