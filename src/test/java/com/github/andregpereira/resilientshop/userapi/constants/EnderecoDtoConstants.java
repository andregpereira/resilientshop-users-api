package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.app.dtos.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.endereco.EnderecoRegistroDto;

import java.util.ArrayList;
import java.util.List;

import static com.github.andregpereira.resilientshop.userapi.constants.PaisDtoConstants.*;

public class EnderecoDtoConstants {

    public static final EnderecoDto ENDERECO_DTO = new EnderecoDto(null, "apelido", "12345-678", "estado", "cidade",
            "bairro", "rua", "20", null, PAIS_DTO);

    public static final EnderecoDto ENDERECO_DTO_PAIS_NOVO = new EnderecoDto(null, "apelido", "12345-678", "estado",
            "cidade", "bairro", "rua", "20", null, PAIS_DTO_NOVO);

    public static final EnderecoDto ENDERECO_DTO_ATUALIZADO = new EnderecoDto(null, "apelido2", "12345-670", "estado2",
            "cidade2", "bairro2", "rua2", "202", null, PAIS_DTO);

    public static final EnderecoDto ENDERECO_DTO_ATUALIZADO_PAIS_NOVO = new EnderecoDto(null, "apelido2", "12345-670",
            "estado2", "cidade2", "bairro2", "rua2", "202", null, PAIS_DTO_NOVO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO = new EnderecoRegistroDto("apelido", "12345-678",
            "estado", "cidade", "bairro", "rua", "20", null, PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_PAIS_NOVO = new EnderecoRegistroDto("apelido",
            "12345-678", "estado", "cidade", "bairro", "rua", "20", null, PAIS_REGISTRO_DTO_NOVO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_ATUALIZADO = new EnderecoRegistroDto("apelido2",
            "12345-670", "estado2", "cidade2", "bairro2", "rua2", "202", null, PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_ATUALIZADO_PAIS_NOVO = new EnderecoRegistroDto(
            "apelido2", "12345-670", "estado2", "cidade2", "bairro2", "rua2", "202", null, PAIS_REGISTRO_DTO_NOVO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_INVALIDO = new EnderecoRegistroDto("", "", "", "", "",
            "", "", null, PAIS_REGISTRO_DTO_INVALIDO);

    public static final List<EnderecoDto> LISTA_ENDERECO_DTO = new ArrayList<>() {
        {
            add(ENDERECO_DTO);
        }
    };
    public static final List<EnderecoDto> LISTA_ENDERECO_DTO_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_DTO_PAIS_NOVO);
        }
    };
    public static final List<EnderecoDto> LISTA_ENDERECO_DTO_ATUALIZADO = new ArrayList<>() {
        {
            add(ENDERECO_DTO_ATUALIZADO);
        }
    };
    public static final List<EnderecoDto> LISTA_ENDERECO_DTO_ATUALIZADO_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_DTO_ATUALIZADO_PAIS_NOVO);
        }
    };
    public static final List<EnderecoRegistroDto> LISTA_ENDERECO_REGISTRO_DTO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_DTO);
        }
    };
    public static final List<EnderecoRegistroDto> LISTA_ENDERECO_REGISTRO_DTO_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_DTO_PAIS_NOVO);
        }
    };
    public static final List<EnderecoRegistroDto> LISTA_ENDERECO_REGISTRO_DTO_ATUALIZADO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_DTO_ATUALIZADO);
        }
    };
    public static final List<EnderecoRegistroDto> LISTA_ENDERECO_REGISTRO_DTO_ATUALIZADO_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_DTO_ATUALIZADO_PAIS_NOVO);
        }
    };
    public static final List<EnderecoRegistroDto> LISTA_ENDERECO_REGISTRO_DTO_INVALIDO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_DTO_INVALIDO);
        }
    };

}
