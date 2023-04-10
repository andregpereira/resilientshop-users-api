package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.dtos.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.dtos.endereco.EnderecoRegistroDto;

import static com.github.andregpereira.resilientshop.userapi.constants.PaisDtoConstants.*;

public class EnderecoDtoConstants {

    public static final EnderecoDto ENDERECO_DTO = new EnderecoDto(null, "12345-678", "estado", "cidade", "bairro",
            "rua", "20", null, PAIS_DTO);

    public static final EnderecoDto ENDERECO_DTO_PAIS_NOVO = new EnderecoDto(null, "12345-678", "estado", "cidade",
            "bairro", "rua", "20", null, PAIS_DTO_NOVO);

    public static final EnderecoDto ENDERECO_DTO_ATUALIZADO = new EnderecoDto(null, "12345-670", "estado2", "cidade2",
            "bairro2", "rua2", "202", null, PAIS_DTO);

    public static final EnderecoDto ENDERECO_DTO_ATUALIZADO_PAIS_NOVO = new EnderecoDto(null, "12345-670", "estado2",
            "cidade2", "bairro2", "rua2", "202", null, PAIS_DTO_NOVO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO = new EnderecoRegistroDto("12345-678", "estado",
            "cidade", "bairro", "rua", "20", null, PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_PAIS_NOVO = new EnderecoRegistroDto("12345-678",
            "estado", "cidade", "bairro", "rua", "20", null, PAIS_REGISTRO_DTO_NOVO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_ATUALIZADO = new EnderecoRegistroDto("12345-670",
            "estado2", "cidade2", "bairro2", "rua2", "202", null, PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_ATUALIZADO_PAIS_NOVO = new EnderecoRegistroDto(
            "12345-670", "estado2", "cidade2", "bairro2", "rua2", "202", null, PAIS_REGISTRO_DTO_NOVO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_INVALIDO = new EnderecoRegistroDto("", "", "", "", "",
            "", null, PAIS_REGISTRO_DTO_INVALIDO);

}
