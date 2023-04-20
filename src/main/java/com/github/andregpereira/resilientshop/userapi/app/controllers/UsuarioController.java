package com.github.andregpereira.resilientshop.userapi.app.controllers;

import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.app.services.UsuarioConsultaService;
import com.github.andregpereira.resilientshop.userapi.app.services.UsuarioManutencaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioManutencaoService usuarioManutencaoService;
    private final UsuarioConsultaService usuarioConsultaService;

    // Registrar usuário
    @PostMapping
    @Operation(summary = "Cria um novo usuário")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", descricao = "Usuário criado", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UsuarioDetalhesDto.class))}),
            @ApiResponse(responseCode = "400", descricao = "JSON não pode ser nulo",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "409", descricao = "Usuário já cadastrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "422", descricao = "Campo inválido",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))})
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
    @Operation(summary = "Atualiza um usuário por id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", descricao = "Usuário atualizado", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UsuarioDetalhesDto.class))}),
            @ApiResponse(responseCode = "404", descricao = "Usuário não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "422", descricao = "Campo inválido",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<UsuarioDetalhesDto> atualizar(@PathVariable Long id,
            @RequestBody @Valid UsuarioAtualizacaoDto dto) {
        log.info("Atualizando usuário...");
        return ResponseEntity.ok(usuarioManutencaoService.atualizar(id, dto));
    }

    // Remoção lógica de usuário por id
    @DeleteMapping("/{id}")
    @Operation(summary = "Desativa um usuário por id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", descricao = "Usuário encontrado e desativado",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", descricao = "Usuário não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<String> desativar(@PathVariable Long id) {
        log.info("Desativando usuário...");
        return ResponseEntity.ok(usuarioManutencaoService.desativar(id));
    }

    // Reativar por id
    @PatchMapping("/reativar/{id}")
    @Operation(summary = "Reativa um usuário por id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", descricao = "Usuário encontrado e reativado",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", descricao = "Usuário não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<String> reativar(@PathVariable Long id) {
        log.info("Reativando usuário...");
        return ResponseEntity.ok(usuarioManutencaoService.reativar(id));
    }

    // Pesquisar por id
    @GetMapping("/{id}")
    @Operation(summary = "Consulta um usuário por id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", descricao = "Usuário encontrado", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UsuarioDetalhesDto.class))}),
            @ApiResponse(responseCode = "404", descricao = "Usuário não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<UsuarioDetalhesDto> consultarPorId(@PathVariable Long id) {
        log.info("Consultando usuário por id {}...", id);
        return ResponseEntity.ok(usuarioConsultaService.consultarPorId(id));
    }

    // Pesquisar por CPF
    @GetMapping("/cpf")
    @Operation(summary = "Consulta um usuário por CPF")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", descricao = "Usuário encontrado", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UsuarioDetalhesDto.class))}),
            @ApiResponse(responseCode = "400", descricao = "Parâmetro CPF não pode ser nulo",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", descricao = "Usuário não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<UsuarioDetalhesDto> consultarPorCpf(@RequestParam String cpf) {
        log.info("Consultando usuário por CPF...");
        return ResponseEntity.ok(usuarioConsultaService.consultarPorCpf(cpf));
    }

    // Pesquisar por nome
    @GetMapping("/nome")
    @Operation(summary = "Consulta um usuário por nome")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", descricao = "Usuário encontrado", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = UsuarioDto.class)))}),
            @ApiResponse(responseCode = "404", descricao = "Usuário não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<Page<UsuarioDto>> consultarPorNome(@RequestParam(required = false) String nome,
            @RequestParam(required = false) String sobrenome,
            @PageableDefault(sort = "nome", direction = Direction.ASC, page = 0, size = 10) Pageable pageable) {
        log.info("Consultando usuário por nome...");
        return ResponseEntity.ok(usuarioConsultaService.consultarPorNome(nome, sobrenome, pageable));
    }

}
