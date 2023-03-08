package com.github.andregpereira.resilientshop.userapi.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;

public record UsuarioDto(Long id, String nome, String sobrenome, String cpf,
		@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy") LocalDate dataCriacao,
		@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy") LocalDate dataModificacao) {

	public UsuarioDto(Usuario usuario) {
		this(usuario.getId(), usuario.getNome(), usuario.getSobrenome(), usuario.getCpf(), usuario.getDataCriacao(),
				usuario.getDataModificacao());
	}

//	public static List<UsuarioDto> criarLista(List<Usuario> usuarios) {
//		Type usuariosDtoListType = new TypeToken<List<UsuarioDto>>() {
//		}.getType();
//		ModelMapper modelMapper = new ModelMapper();
//		return modelMapper.map(usuarios, usuariosDtoListType);
//	}

}
