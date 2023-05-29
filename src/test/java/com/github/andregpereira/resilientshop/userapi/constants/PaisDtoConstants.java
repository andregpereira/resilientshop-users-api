package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.app.dto.pais.PaisDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.pais.PaisRegistroDto;

public class PaisDtoConstants {

    public static final PaisDto PAIS_DTO = new PaisDto(null, "Brasil", "+055");

    public static final PaisDto PAIS_DTO_NOVO = new PaisDto(null, "EUA", "+001");

    public static final PaisRegistroDto PAIS_REGISTRO_DTO = new PaisRegistroDto("Brasil", "+055");

    public static final PaisRegistroDto PAIS_REGISTRO_DTO_NOVO = new PaisRegistroDto("EUA", "+001");

    public static final PaisRegistroDto PAIS_REGISTRO_DTO_INVALIDO = new PaisRegistroDto("", "");

}
