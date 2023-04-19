package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Pais;

public class PaisConstants {

    public static final Pais PAIS = new Pais(null, "Brasil", "+055");

    public static final Pais PAIS_NOVO = new Pais(null, "EUA", "+001");

    public static final Pais PAIS_INVALIDO = new Pais(null, "", "");

    public static final Pais PAIS_VAZIO = new Pais();

}
