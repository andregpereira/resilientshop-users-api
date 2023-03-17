package com.github.andregpereira.resilientshop.userapi.dtos.pais;

import org.springframework.data.domain.Page;

import com.github.andregpereira.resilientshop.userapi.entities.Pais;

import lombok.Builder;

@Builder
public record PaisDto(Long id, String nome, String codigo) {

	public PaisDto(Pais pais) {
		this(pais.getId(), pais.getNome(), pais.getCodigo());
	}

	public static Page<PaisDto> criarLista(Page<Pais> PaisesPage) {
		return PaisesPage.map(PaisDto::new);
	}

}
