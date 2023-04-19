package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;

import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.*;

public class EnderecoConstants {

    public static final Endereco ENDERECO = new Endereco(null, "12345-678", "estado", "cidade", "bairro", "rua", "20",
            null, PAIS);

    public static final Endereco ENDERECO_PAIS_NOVO = new Endereco(null, "12345-678", "estado", "cidade", "bairro",
            "rua", "20", null, PAIS_NOVO);

    public static final Endereco ENDERECO_ATUALIZADO = new Endereco(null, "12345-670", "estado2", "cidade2", "bairro2",
            "rua2", "202", null, PAIS);

    public static final Endereco ENDERECO_ATUALIZADO_PAIS_NOVO = new Endereco(null, "12345-670", "estado2", "cidade2",
            "bairro2", "rua2", "202", null, PAIS_NOVO);

    public static final Endereco ENDERECO_INVALIDO = new Endereco(null, "", "", "", "", "", "", null, PAIS_INVALIDO);

    public static final Endereco ENDERECO_VAZIO = new Endereco();

}
