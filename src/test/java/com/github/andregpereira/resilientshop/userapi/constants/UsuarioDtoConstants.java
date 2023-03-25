package com.github.andregpereira.resilientshop.userapi.constants;

import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.LOCAL_DATE;

import com.github.andregpereira.resilientshop.userapi.dtos.endereco.*;
import com.github.andregpereira.resilientshop.userapi.dtos.pais.*;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.*;

public class UsuarioDtoConstants {

	public static final UsuarioDto USUARIO_DTO = new UsuarioDto(1L, "nome", "sobrenome", "22426853093", null,
			LOCAL_DATE, LOCAL_DATE, true);

	public static final UsuarioDetalhesDto USUARIO_DETALHES_DTO = new UsuarioDetalhesDto(null, "nome", "sobrenome",
			"22426853093", null, LOCAL_DATE, LOCAL_DATE, true, new EnderecoDto(null, "12345-678", "estado", "cidade",
					"bairro", "rua", "20", null, new PaisDto(null, "Brasil", "+055")));

	public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO = new UsuarioRegistroDto("nome", "sobrenome",
			"22426853093", null, new EnderecoRegistroDto("12345-678", "estado", "cidade", "bairro", "rua", "20", null,
					new PaisRegistroDto("Brasil", "+055")));

//	public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO_NULO = null;

	public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO_INVALIDO = new UsuarioRegistroDto("", "", "", null,
			new EnderecoRegistroDto("", "", "", "", "", "", null, new PaisRegistroDto("", "")));

	public static final UsuarioAtualizacaoDto USUARIO_ATUALIZACAO_DTO = new UsuarioAtualizacaoDto("nome", "sobrenome",
			null, new EnderecoRegistroDto("12345-678", "estado", "cidade", "bairro", "rua", "20", null,
					new PaisRegistroDto("Brasil", "+055")));

	public static final UsuarioAtualizacaoDto USUARIO_ATUALIZACAO_DTO_NULO = null;

	public static final UsuarioAtualizacaoDto USUARIO_ATUALIZACAO_DTO_INVALIDO = new UsuarioAtualizacaoDto(null, null,
			null, new EnderecoRegistroDto(null, null, null, null, null, null, null, new PaisRegistroDto(null, null)));

}
