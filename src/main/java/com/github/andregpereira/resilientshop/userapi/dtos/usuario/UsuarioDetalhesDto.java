package com.github.andregpereira.resilientshop.userapi.dtos.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.github.andregpereira.resilientshop.userapi.dtos.endereco.EnderecoDto;

import java.time.LocalDate;

public record UsuarioDetalhesDto(Long id,
        String nome,
        String sobrenome,
        String cpf,
        String telefone,
        @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy") LocalDate dataCriacao,
        @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy") LocalDate dataModificacao,
        boolean ativo,
        EnderecoDto endereco) {

}
