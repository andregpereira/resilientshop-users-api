package com.github.andregpereira.resilientshop.userapi.app.dto.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import java.time.LocalDate;

public record UsuarioDto(Long id,
        String nome,
        String sobrenome,
        String cpf,
        String telefone,
        @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/uuuu") LocalDate dataCriacao,
        @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/uuuu") LocalDate dataModificacao,
        boolean ativo) {

}
