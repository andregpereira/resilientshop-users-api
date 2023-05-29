package com.github.andregpereira.resilientshop.userapi.app.dtos.endereco;

import com.github.andregpereira.resilientshop.userapi.app.dtos.pais.PaisDto;

public record EnderecoDto(Long id,
        String apelido,
        String cep,
        String estado,
        String cidade,
        String bairro,
        String rua,
        String numero,
        String complemento,
        PaisDto pais) {

}
