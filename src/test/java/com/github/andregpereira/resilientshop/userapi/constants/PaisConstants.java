package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Pais;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.*;

public class PaisConstants {

    public static final Pais PAIS = new Pais(1L, "Brasil", "+055", LISTA_ENDERECOS);

    public static final Pais PAIS_MAPEADO = new Pais(1L, "Brasil", "+055", LISTA_ENDERECOS_MAPEADO);

    public static final Pais PAIS_NOVO = new Pais(1L, "EUA", "+001", LISTA_ENDERECOS_PAIS_NOVO);

    public static final Pais PAIS_INVALIDO = new Pais(null, "", "", LISTA_ENDERECOS_INVALIDO);

    public static final Pais PAIS_VAZIO = new Pais();

}
