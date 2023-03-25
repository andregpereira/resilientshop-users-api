package com.github.andregpereira.resilientshop.userapi.constants;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.*;

import com.github.andregpereira.resilientshop.userapi.entities.Pais;

public class PaisConstants {

	public static final Pais PAIS = ENDERECO.getPais();
	public static final Pais PAIS_NULO = null;
	public static final Pais PAIS_VAZIO = new Pais();
	public static final Pais PAIS_INVALIDO = ENDERECO_INVALIDO.getPais();

}
