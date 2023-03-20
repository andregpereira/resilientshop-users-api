package com.github.andregpereira.resilientshop.userapi.dtos.endereco;

import com.github.andregpereira.resilientshop.userapi.dtos.pais.PaisDto;
import com.github.andregpereira.resilientshop.userapi.entities.Endereco;

import lombok.Builder;

@Builder
public record EnderecoDto(Long id, String cep, String estado, String cidade, String bairro, String rua, String numero,
		String complemento, PaisDto pais) {

	public EnderecoDto(Endereco endereco) {
		this(endereco.getId(), endereco.getCep(), endereco.getEstado(), endereco.getCidade(), endereco.getBairro(),
				endereco.getRua(), endereco.getNumero(), endereco.getComplemento(), new PaisDto(endereco.getPais()));
	}

}
