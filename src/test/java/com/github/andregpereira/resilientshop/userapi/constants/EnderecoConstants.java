package com.github.andregpereira.resilientshop.userapi.constants;

import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.*;

import com.github.andregpereira.resilientshop.userapi.entities.Endereco;

public class EnderecoConstants {

	public static final Endereco ENDERECO = USUARIO.getEndereco();
	public static final Endereco ENDERECO_NULO = null;
	public static final Endereco ENDERECO_VAZIO = new Endereco();
	public static final Endereco ENDERECO_INVALIDO = USUARIO_INVALIDO.getEndereco();

}
