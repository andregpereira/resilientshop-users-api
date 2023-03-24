package com.github.andregpereira.resilientshop.userapi;

import java.time.LocalDate;

import com.github.andregpereira.resilientshop.userapi.dtos.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.dtos.endereco.EnderecoRegistroDto;
import com.github.andregpereira.resilientshop.userapi.dtos.pais.PaisDto;
import com.github.andregpereira.resilientshop.userapi.dtos.pais.PaisRegistroDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.entities.Endereco;
import com.github.andregpereira.resilientshop.userapi.entities.Pais;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;

public class UsuarioConstants {

	public static final Usuario USUARIO = new Usuario(null, "nome", "sobrenome", "22426853093", null, LocalDate.now(),
			LocalDate.now(), true, new Endereco(null, "12345-678", "estado", "cidade", "bairro", "rua", "20", null,
					new Pais(null, "Brasil", "+055", null), null));

	public static final Usuario USUARIO_VAZIO = null;

	public static final Usuario USUARIO_INVALIDO = new Usuario(null, "", "", "", null, null, null, false,
			new Endereco(null, "", "", "", "", "", "", null, new Pais(null, "", "", null), null));

	public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO = new UsuarioRegistroDto("nome", "sobrenome",
			"22426853093", null, new EnderecoRegistroDto("12345-678", "estado", "cidade", "bairro", "rua", "20", null,
					new PaisRegistroDto("Brasil", "+055")));

	public static final UsuarioDetalhesDto USUARIO_DETALHES_DTO = UsuarioDetalhesDto.builder().id(1L)
			.nome(USUARIO.getNome()).sobrenome(USUARIO.getSobrenome()).cpf(USUARIO.getCpf())
			.telefone(USUARIO.getTelefone()).dataCriacao(LocalDate.now()).dataModificacao(LocalDate.now()).ativo(true)
			.endereco(EnderecoDto.builder().id(1L).cep("12345-678").estado("estado").cidade("cidade").bairro("bairro")
					.rua("rua").numero("20").pais(PaisDto.builder().id(1L).nome("Brasil").codigo("+055").build())
					.build())
			.build();

	public static final UsuarioDto USUARIO_DTO = new UsuarioDto(USUARIO);

	public static final Endereco ENDERECO = USUARIO.getEndereco();

	public static final EnderecoDto ENDERECO_DTO = new EnderecoDto(ENDERECO);

	public static final Pais PAIS = USUARIO.getEndereco().getPais();

	public static final PaisRegistroDto PAIS_REGISTRO_DTO = new PaisRegistroDto(
			USUARIO_REGISTRO_DTO.endereco().pais().nome(), USUARIO_REGISTRO_DTO.endereco().pais().codigo());

	public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO = new EnderecoRegistroDto("", "", "", "", "", "", "",
			PAIS_REGISTRO_DTO);

	public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO_VAZIO = UsuarioRegistroDto.builder().build();

	public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO_INVALIDO = new UsuarioRegistroDto("", "", "", null,
			new EnderecoRegistroDto("", "", "", "", "", "", null, new PaisRegistroDto("", "")));

}
