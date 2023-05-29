package com.github.andregpereira.resilientshop.userapi.app.representantion.usuario;

import com.github.andregpereira.resilientshop.userapi.app.representantion.endereco.EnderecoDto;

import java.time.LocalDate;
import java.util.List;

public record UsuarioDetalhesDto(Long id,
        String nome,
        String sobrenome,
        String cpf,
        String telefone,
//        @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/uuuu")
        LocalDate dataCriacao,
//        @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/uuuu")
        LocalDate dataModificacao,
        boolean ativo,
        List<EnderecoDto> enderecos) {

}
