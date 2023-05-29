package com.github.andregpereira.resilientshop.userapi.app.representantion.endereco;

import com.github.andregpereira.resilientshop.userapi.app.representantion.pais.PaisDto;

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
