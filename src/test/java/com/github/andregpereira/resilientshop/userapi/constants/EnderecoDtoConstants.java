package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroUsuarioNovoDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.andregpereira.resilientshop.userapi.constants.PaisDtoConstants.*;

public class EnderecoDtoConstants {

    public static final EnderecoDto ENDERECO_DTO = new EnderecoDto(null, "apelido", "12345-678", "estado", "cidade",
            "bairro", "rua", "20", null, true, PAIS_DTO);

    public static final EnderecoDto ENDERECO_DTO_PAIS_NOVO = new EnderecoDto(null, "apelido", "12345-678", "estado",
            "cidade", "bairro", "rua", "20", null, true, PAIS_DTO_NOVO);

    public static final EnderecoDto ENDERECO_DTO_ATUALIZADO = new EnderecoDto(null, "apelido2", "12345-670", "estado2",
            "cidade2", "bairro2", "rua2", "202", null, true, PAIS_DTO);

    public static final EnderecoDto ENDERECO_DTO_ATUALIZADO_PAIS_NOVO = new EnderecoDto(null, "apelido2", "12345-670",
            "estado2", "cidade2", "bairro2", "rua2", "202", null, true, PAIS_DTO_NOVO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO = new EnderecoRegistroDto("apelido", "12345-678",
            "estado", "cidade", "bairro", "rua", "20", null, true, 1L, PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_PADRAO_FALSE = new EnderecoRegistroDto("apelido",
            "12345-678", "estado", "cidade", "bairro", "rua", "20", null, false, 1L, PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroUsuarioNovoDto ENDERECO_REGISTRO_USUARIO_NOVO_DTO = new EnderecoRegistroUsuarioNovoDto(
            "apelido", "12345-678", "estado", "cidade", "bairro", "rua", "20", null, PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_ATUALIZADO = new EnderecoRegistroDto("apelido2",
            "12345-670", "estado2", "cidade2", "bairro2", "rua2", "202", null, true, 1L, PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_ATUALIZADO_PADRAO_FALSE = new EnderecoRegistroDto(
            "apelido2", "12345-670", "estado2", "cidade2", "bairro2", "rua2", "202", null, false, 1L,
            PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroUsuarioNovoDto ENDERECO_REGISTRO_USUARIO_NOVO_DTO_ATUALIZADO = new EnderecoRegistroUsuarioNovoDto(
            "apelido2", "12345-670", "estado2", "cidade2", "bairro2", "rua2", "202", null, PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_PAIS_NOVO = new EnderecoRegistroDto("apelido",
            "12345-678", "estado", "cidade", "bairro", "rua", "20", null, true, 1L, PAIS_REGISTRO_DTO_NOVO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_PAIS_NOVO_PADRAO_FALSE = new EnderecoRegistroDto(
            "apelido", "12345-678", "estado", "cidade", "bairro", "rua", "20", null, false, 1L, PAIS_REGISTRO_DTO_NOVO);

    public static final EnderecoRegistroUsuarioNovoDto ENDERECO_REGISTRO_USUARIO_NOVO_DTO_PAIS_NOVO = new EnderecoRegistroUsuarioNovoDto(
            "apelido", "12345-678", "estado", "cidade", "bairro", "rua", "20", null, PAIS_REGISTRO_DTO_NOVO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_ATUALIZADO_PAIS_NOVO = new EnderecoRegistroDto(
            "apelido2", "12345-670", "estado2", "cidade2", "bairro2", "rua2", "202", null, true, 2L,
            PAIS_REGISTRO_DTO_NOVO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_ATUALIZADO_PAIS_NOVO_PADRAO_FALSE = new EnderecoRegistroDto(
            "apelido2", "12345-670", "estado2", "cidade2", "bairro2", "rua2", "202", null, false, 2L,
            PAIS_REGISTRO_DTO_NOVO);

    public static final EnderecoRegistroUsuarioNovoDto ENDERECO_REGISTRO_USUARIO_NOVO_DTO_ATUALIZADO_PAIS_NOVO = new EnderecoRegistroUsuarioNovoDto(
            "apelido2", "12345-670", "estado2", "cidade2", "bairro2", "rua2", "202", null, PAIS_REGISTRO_DTO_NOVO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_INVALIDO = new EnderecoRegistroDto("", "", "", "", "",
            "", "", null, true, null, PAIS_REGISTRO_DTO_INVALIDO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_INVALIDO_PADRAO_FALSE = new EnderecoRegistroDto("",
            "", "", "", "", "", "", null, false, null, PAIS_REGISTRO_DTO_INVALIDO);

    public static final EnderecoRegistroUsuarioNovoDto ENDERECO_REGISTRO_USUARIO_NOVO_DTO_INVALIDO = new EnderecoRegistroUsuarioNovoDto(
            "", "", "", "", "", "", "", null, PAIS_REGISTRO_DTO_INVALIDO);

    public static final List<EnderecoDto> LISTA_ENDERECOS_DTO = Collections.singletonList(ENDERECO_DTO);

    //    public static final List<EnderecoDto> LISTA_ENDERECOS_DTO = new ArrayList<>() {
//        {
//            add(ENDERECO_DTO);
//        }
//    };
//
    public static final List<EnderecoDto> LISTA_ENDERECOS_DTO_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_DTO_PAIS_NOVO);
        }
    };

    public static final List<EnderecoDto> LISTA_ENDERECOS_DTO_ATUALIZADO = new ArrayList<>() {
        {
            add(ENDERECO_DTO_ATUALIZADO);
        }
    };

    public static final List<EnderecoDto> LISTA_ENDERECOS_DTO_ATUALIZADO_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_DTO_ATUALIZADO_PAIS_NOVO);
        }
    };

    public static final List<EnderecoRegistroUsuarioNovoDto> LISTA_ENDERECOS_REGISTRO_DTO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_USUARIO_NOVO_DTO);
        }
    };

    public static final List<EnderecoRegistroUsuarioNovoDto> LISTA_ENDERECOS_REGISTRO_DTO_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_USUARIO_NOVO_DTO_PAIS_NOVO);
        }
    };

    public static final List<EnderecoRegistroDto> LISTA_ENDERECOS_REGISTRO_DTO_ATUALIZADO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_DTO_ATUALIZADO);
        }
    };

    public static final List<EnderecoRegistroUsuarioNovoDto> LISTA_ENDERECOS_REGISTRO_NOVO_USUARIO_DTO_ATUALIZADO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_USUARIO_NOVO_DTO_ATUALIZADO);
        }
    };

    public static final List<EnderecoRegistroUsuarioNovoDto> LISTA_ENDERECOS_REGISTRO_DTO_ATUALIZADO_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_USUARIO_NOVO_DTO_ATUALIZADO_PAIS_NOVO);
        }
    };

    public static final List<EnderecoRegistroUsuarioNovoDto> LISTA_ENDERECOS_REGISTRO_DTO_INVALIDO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_USUARIO_NOVO_DTO_INVALIDO);
        }
    };

}
