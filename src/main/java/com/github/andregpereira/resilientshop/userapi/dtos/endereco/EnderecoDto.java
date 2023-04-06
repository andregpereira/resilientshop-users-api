package com.github.andregpereira.resilientshop.userapi.dtos.endereco;

import com.github.andregpereira.resilientshop.userapi.dtos.pais.PaisDto;

public record EnderecoDto(Long id, String cep, String estado, String cidade, String bairro, String rua, String numero,
        String complemento, PaisDto pais) {

}
