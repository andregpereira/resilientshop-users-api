package com.github.andregpereira.resilientshop.userapi.app.dto.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoDto;

import java.time.LocalDate;
import java.util.List;

public record UsuarioDetalhesDto(Long id,
        String nome,
        String sobrenome,
        String cpf,
        String email,
        String telefone,
        @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/uuuu") LocalDate dataCriacao,
        @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/uuuu") LocalDate dataModificacao,
        boolean ativo,
        List<EnderecoDto> enderecos) {

}
