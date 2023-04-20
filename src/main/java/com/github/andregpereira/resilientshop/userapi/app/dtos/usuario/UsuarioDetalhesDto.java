package com.github.andregpereira.resilientshop.userapi.app.dtos.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.github.andregpereira.resilientshop.userapi.app.dtos.endereco.EnderecoDto;

import java.time.LocalDate;
import java.util.List;

public record UsuarioDetalhesDto(Long id,
        String nome,
        String sobrenome,
        String cpf,
        String telefone,
        @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/uuuu") LocalDate dataCriacao,
        @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/uuuu") LocalDate dataModificacao,
        boolean ativo,
        List<EnderecoDto> enderecos) {

}
