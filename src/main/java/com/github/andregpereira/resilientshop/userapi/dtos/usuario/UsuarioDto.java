package com.github.andregpereira.resilientshop.userapi.dtos.usuario;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;

import lombok.Builder;

@Builder
public record UsuarioDto(Long id, String nome, String sobrenome, String cpf, String telefone,
		@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy") LocalDate dataCriacao,
		@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy") LocalDate dataModificacao, boolean ativo) {

	public UsuarioDto(Usuario usuario) {
		this(usuario.getId(), usuario.getNome(), usuario.getSobrenome(), usuario.getCpf(), usuario.getTelefone(),
				usuario.getDataCriacao(), usuario.getDataModificacao(), usuario.isAtivo());
	}

	public static Page<UsuarioDto> criarLista(Page<Usuario> usuariosPage) {
		return usuariosPage.map(UsuarioDto::new);
	}

}
