package com.github.andregpereira.resilientshop.userapi.constants;

import java.time.LocalDate;

import com.github.andregpereira.resilientshop.userapi.entities.Endereco;
import com.github.andregpereira.resilientshop.userapi.entities.Pais;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;

public class UsuarioConstants {

	public static final LocalDate LOCAL_DATE = LocalDate.now();

	public static final Usuario USUARIO = new Usuario(null, "nome", "sobrenome", "22426853093", null, LOCAL_DATE,
			LOCAL_DATE, true, new Endereco(null, "12345-678", "estado", "cidade", "bairro", "rua", "20", null,
					new Pais(null, "Brasil", "+055", null), null));

	public static final Usuario USUARIO_INATIVO = new Usuario(null, "nome", "sobrenome", "22426853093", null,
			LOCAL_DATE, LOCAL_DATE, false, new Endereco(null, "12345-678", "estado", "cidade", "bairro", "rua", "20",
					null, new Pais(null, "Brasil", "+055", null), null));

	public static final Usuario USUARIO_NULO = null;

	public static final Usuario USUARIO_VAZIO = new Usuario();

	public static final Usuario USUARIO_INVALIDO = new Usuario(null, "", "", "", null, null, null, false,
			new Endereco(null, "", "", "", "", "", "", null, new Pais(null, "", "", null), null));

}
