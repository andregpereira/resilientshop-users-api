package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroUsuarioDto;

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

    public static final EnderecoRegistroUsuarioDto ENDERECO_REGISTRO_NOVO_USUARIO_DTO = new EnderecoRegistroUsuarioDto(
            "apelido", "12345-678", "estado", "cidade", "bairro", "rua", "20", null, PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO = new EnderecoRegistroDto("apelido", "12345-678",
            "estado", "cidade", "bairro", "rua", "20", null, 1L, PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroUsuarioDto ENDERECO_REGISTROUSUARIO_NOVO_DTO_PAIS_NOVO = new EnderecoRegistroUsuarioDto(
            "apelido", "12345-678", "estado", "cidade", "bairro", "rua", "20", null, PAIS_REGISTRO_DTO_NOVO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_ATUALIZADO = new EnderecoRegistroDto("apelido2",
            "12345-670", "estado2", "cidade2", "bairro2", "rua2", "202", null, 1L, PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroUsuarioDto ENDERECO_REGISTRO_NOVO_USUARIO_DTO_ATUALIZADO = new EnderecoRegistroUsuarioDto(
            "apelido2", "12345-670", "estado2", "cidade2", "bairro2", "rua2", "202", null, PAIS_REGISTRO_DTO);

    public static final EnderecoRegistroUsuarioDto ENDERECO_REGISTRO_DTO_ATUALIZADO_PAIS_NOVO = new EnderecoRegistroUsuarioDto(
            "apelido2", "12345-670", "estado2", "cidade2", "bairro2", "rua2", "202", null, PAIS_REGISTRO_DTO_NOVO);

    public static final EnderecoRegistroDto ENDERECO_REGISTRO_DTO_INVALIDO = new EnderecoRegistroDto("", "", "", "", "",
            "", "", null, null, PAIS_REGISTRO_DTO_INVALIDO);

    public static final EnderecoRegistroUsuarioDto ENDERECO_REGISTRO_NOVO_USUARIO_DTO_INVALIDO = new EnderecoRegistroUsuarioDto(
            "", "", "", "", "", "", "", null, PAIS_REGISTRO_DTO_INVALIDO);

    public static final List<EnderecoDto> LISTA_ENDERECOS_DTO = new ArrayList<>() {
        {
            add(ENDERECO_DTO);
        }
    };

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

    public static final List<EnderecoRegistroUsuarioDto> LISTA_ENDERECOS_REGISTRO_DTO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_NOVO_USUARIO_DTO);
        }
    };

    public static final List<EnderecoRegistroUsuarioDto> LISTA_ENDERECOS_REGISTRO_DTO_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTROUSUARIO_NOVO_DTO_PAIS_NOVO);
        }
    };

    public static final List<EnderecoRegistroDto> LISTA_ENDERECOS_REGISTRO_DTO_ATUALIZADO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_DTO_ATUALIZADO);
        }
    };

    public static final List<EnderecoRegistroUsuarioDto> LISTA_ENDERECOS_REGISTRO_NOVO_USUARIO_DTO_ATUALIZADO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_NOVO_USUARIO_DTO_ATUALIZADO);
        }
    };

    public static final List<EnderecoRegistroUsuarioDto> LISTA_ENDERECOS_REGISTRO_DTO_ATUALIZADO_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_DTO_ATUALIZADO_PAIS_NOVO);
        }
    };

    public static final List<EnderecoRegistroUsuarioDto> LISTA_ENDERECOS_REGISTRO_DTO_INVALIDO = new ArrayList<>() {
        {
            add(ENDERECO_REGISTRO_NOVO_USUARIO_DTO_INVALIDO);
        }
    };

}
