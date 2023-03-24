package com.github.andregpereira.resilientshop.userapi;

import static com.github.andregpereira.resilientshop.userapi.UsuarioConstants.*;


import com.github.andregpereira.resilientshop.userapi.dtos.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.dtos.endereco.EnderecoRegistroDto;
import com.github.andregpereira.resilientshop.userapi.dtos.pais.PaisDto;
import com.github.andregpereira.resilientshop.userapi.dtos.pais.PaisRegistroDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioRegistroDto;

public class UsuarioDtoConstants {

	public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO = new UsuarioRegistroDto("nome", "sobrenome",
			"22426853093", null, new EnderecoRegistroDto("12345-678", "estado", "cidade", "bairro", "rua", "20", null,
					new PaisRegistroDto("Brasil", "+055")));

	public static final UsuarioDetalhesDto USUARIO_DETALHES_DTO = UsuarioDetalhesDto.builder().id(1L)
			.nome(USUARIO.getNome()).sobrenome(USUARIO.getSobrenome()).cpf(USUARIO.getCpf())
			.telefone(USUARIO.getTelefone()).dataCriacao(USUARIO.getDataCriacao())
			.dataModificacao(USUARIO.getDataModificacao()).ativo(USUARIO.isAtivo())
			.endereco(EnderecoDto.builder().id(USUARIO.getEndereco().getId()).cep(USUARIO.getEndereco().getCep()).estado(USUARIO.getEndereco().getEstado()).cidade(USUARIO.getEndereco().getCidade()).bairro(USUARIO.getEndereco().getBairro())
					.rua(USUARIO.getEndereco().getRua()).numero(USUARIO.getEndereco().getNumero()).pais(PaisDto.builder().id(USUARIO.getEndereco().getPais().getId()).nome(USUARIO.getEndereco().getPais().getNome()).codigo(USUARIO.getEndereco().getPais().getCodigo()).build())
					.build())
			.build();

//	public static final UsuarioDetalhesDto USUARIO_DETALHES_DTO = UsuarioDetalhesDto.builder().id(1L)
//			.nome(USUARIO.getNome()).sobrenome(USUARIO.getSobrenome()).cpf(USUARIO.getCpf())
//			.telefone(USUARIO.getTelefone()).dataCriacao(USUARIO.getDataCriacao())
//			.dataModificacao(USUARIO.getDataModificacao()).ativo(true)
//			.endereco(EnderecoDto.builder().id(1L).cep("12345-678").estado("estado").cidade("cidade").bairro("bairro")
//					.rua("rua").numero("20").pais(PaisDto.builder().id(1L).nome("Brasil").codigo("+055").build())
//					.build())
//			.build();
	
	public static final UsuarioDto USUARIO_DTO = new UsuarioDto(USUARIO);

	public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO_VAZIO = UsuarioRegistroDto.builder().build();

	public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO_INVALIDO = new UsuarioRegistroDto("", "", "", null,
			new EnderecoRegistroDto("", "", "", "", "", "", null, new PaisRegistroDto("", "")));

}
