package com.github.andregpereira.resilientshop.userapi.constants;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoDtoConstants.*;

import com.github.andregpereira.resilientshop.userapi.dtos.pais.PaisDto;
import com.github.andregpereira.resilientshop.userapi.dtos.pais.PaisRegistroDto;

public class PaisDtoConstants {

	public static final PaisDto PAIS_DTO = ENDERECO_DTO.pais();
	public static final PaisRegistroDto PAIS_REGISTRO_DTO = ENDERECO_REGISTRO_DTO.pais();

}
