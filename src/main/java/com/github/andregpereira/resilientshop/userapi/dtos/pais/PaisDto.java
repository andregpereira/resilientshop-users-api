package com.github.andregpereira.resilientshop.userapi.dtos.pais;

import com.github.andregpereira.resilientshop.userapi.entities.Pais;

import lombok.Builder;

@Builder
public record PaisDto(Long id, String nome, String codigo) {

	public PaisDto(Pais pais) {
		this(pais.getId(), pais.getNome(), pais.getCodigo());
	}

}
