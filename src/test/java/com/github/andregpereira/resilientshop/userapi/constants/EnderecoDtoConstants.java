package com.github.andregpereira.resilientshop.userapi.constants;

import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioDtoConstants.USUARIO_DETALHES_DTO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioDtoConstants.USUARIO_REGISTRO_DTO;

import com.github.andregpereira.resilientshop.userapi.dtos.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.dtos.endereco.EnderecoRegistroDto;

public class EnderecoDtoConstants {

	public static final EnderecoDto ENDERECO_DTO = USUARIO_DETALHES_DTO.endereco();
	public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO = USUARIO_REGISTRO_DTO.endereco();

}
