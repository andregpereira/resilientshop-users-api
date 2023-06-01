package com.github.andregpereira.resilientshop.userapi.app.controllers;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroDto;
import com.github.andregpereira.resilientshop.userapi.app.services.EnderecoConsultaService;
import com.github.andregpereira.resilientshop.userapi.app.services.EnderecoManutencaoService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Controller de endereços da API de Usuários.
 *
 * @author André Garcia
 */
@RequiredArgsConstructor
@Slf4j
@Validated
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    /**
     * Injeção da dependência {@link EnderecoManutencaoService} para serviços de manutenção.
     */
    private final EnderecoManutencaoService manutencaoService;

    /**
     * Injeção da dependência {@link EnderecoConsultaService} para serviços de consulta.
     */
    private final EnderecoConsultaService consultaService;

    @PostMapping
    public ResponseEntity<EnderecoDto> criar(@RequestBody @Valid EnderecoRegistroDto dto) {
        log.info("Cadastrando endereço...");
        EnderecoDto endereco = manutencaoService.criar(dto);
        log.info("Endereço cadastrado com sucesso!");
        URI uri = UriComponentsBuilder.fromPath("/enderecos/{id}").buildAndExpand(endereco.id()).toUri();
        return ResponseEntity.created(uri).body(endereco);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDto> atualizar(@PathVariable Long id, @RequestBody @Valid EnderecoRegistroDto dto) {
        log.info("Atualizando endereço...");
        EnderecoDto endereco = manutencaoService.atualizar(id, dto);
        log.info("Endereço atualizado com sucesso!");
        URI uri = UriComponentsBuilder.fromPath("/enderecos/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.ok().location(uri).body(endereco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remover(@PathVariable Long id) {
        log.info("Removendo endereço com id {}...", id);
        return ResponseEntity.ok(manutencaoService.remover(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDto> consultarPorId(@PathVariable Long id) {
        log.info("Procurando endereço com id {}...", id);
        return ResponseEntity.ok(consultaService.consultarPorId(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Page<EnderecoDto>> consultarPorIdUsuario(@PathVariable Long idUsuario,
            @Parameter(hidden = true) @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10)
            Pageable pageable) {
        log.info("Procurando endereços do usuário com id {}...", idUsuario);
        return ResponseEntity.ok(consultaService.consultarPorIdUsuario(idUsuario, pageable));
    }

    @GetMapping("/usuario/{idUsuario}/apelido")
    public ResponseEntity<EnderecoDto> consultarPorApelido(@PathVariable Long idUsuario, @RequestParam String apelido) {
        log.info("Procurando endereço com apelido {} do usuário com id {}", apelido, idUsuario);
        return ResponseEntity.ok(consultaService.consultarPorApelido(idUsuario, apelido));
    }

}
