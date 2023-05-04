package com.github.andregpereira.resilientshop.userapi.app.controllers;

import com.github.andregpereira.resilientshop.userapi.app.dtos.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.app.services.EnderecoConsultaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@Validated
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoConsultaService enderecoConsultaService;

    @GetMapping("/{id}/usuario/{idUsuario}")
    public ResponseEntity<EnderecoDto> consultarPorId(@PathVariable Long id, @PathVariable Long idUsuario) {
        return ResponseEntity.ok(enderecoConsultaService.consultarPorId(id, idUsuario));
    }

    @GetMapping("/usuario/{idUsuario}/apelido")
    public ResponseEntity<EnderecoDto> consultarEnderecoPorApelido(@PathVariable Long idUsuario,
            @RequestParam String apelido) {
        return ResponseEntity.ok(enderecoConsultaService.consultarPorApelido(idUsuario, apelido));
    }

}
