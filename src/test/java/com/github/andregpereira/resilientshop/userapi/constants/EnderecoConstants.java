package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;

import java.util.ArrayList;
import java.util.List;

import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.*;

public class EnderecoConstants {

    public static final Endereco ENDERECO = new Endereco(null, "apelido", "12345-678", "estado", "cidade", "bairro",
            "rua", "20", null, PAIS, USUARIO);

    public static final Endereco ENDERECO_PAIS_NOVO = new Endereco(null, "apelido", "12345-678", "estado", "cidade",
            "bairro", "rua", "20", null, PAIS_NOVO, USUARIO_PAIS_NOVO);

    public static final Endereco ENDERECO_ATUALIZADO = new Endereco(null, "apelido2", "12345-670", "estado2", "cidade2",
            "bairro2", "rua2", "202", null, PAIS, USUARIO_ATUALIZADO);

    public static final Endereco ENDERECO_ATUALIZADO_PAIS_NOVO = new Endereco(null, "apelido2", "12345-670", "estado2",
            "cidade2", "bairro2", "rua2", "202", null, PAIS_NOVO, USUARIO_ATUALIZADO_PAIS_NOVO);

    public static final Endereco ENDERECO_INVALIDO = new Endereco(null, "", "", "", "", "", "", null, null,
            PAIS_INVALIDO, USUARIO_INVALIDO);

    public static final Endereco ENDERECO_VAZIO = new Endereco();

    public static final List<Endereco> LISTA_ENDERECOS = new ArrayList<>() {
        {
            add(ENDERECO);
        }
    };
    public static final List<Endereco> LISTA_ENDERECOS_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_PAIS_NOVO);
        }
    };
    public static final List<Endereco> LISTA_ENDERECOS_ATUALIZADO = new ArrayList<>() {
        {
            add(ENDERECO_ATUALIZADO);
        }
    };
    public static final List<Endereco> LISTA_ENDERECOS_ATUALIZADO_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_ATUALIZADO_PAIS_NOVO);
        }
    };

    public static final List<Endereco> LISTA_ENDERECOS_INVALIDO = new ArrayList<>() {
        {
            add(ENDERECO_INVALIDO);
        }
    };

}
