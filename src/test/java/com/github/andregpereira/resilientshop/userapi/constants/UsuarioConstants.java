package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;

import java.time.LocalDate;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.*;

public class UsuarioConstants {

    public static final LocalDate LOCAL_DATE = LocalDate.now();

    public static final Usuario USUARIO = new Usuario(null, "nome", "sobrenome", "22426853093", null, LOCAL_DATE,
            LOCAL_DATE, true, ENDERECO);

    public static final Usuario USUARIO_PAIS_NOVO = new Usuario(null, "nome", "sobrenome", "22426853093", null,
            LOCAL_DATE, LOCAL_DATE, true, ENDERECO_PAIS_NOVO);

    public static final Usuario USUARIO_ATUALIZADO = new Usuario(null, "nome2", "sobrenome2", "22426853093", null,
            LOCAL_DATE, LOCAL_DATE, true, ENDERECO_ATUALIZADO);

    public static final Usuario USUARIO_ATUALIZADO_PAIS_NOVO = new Usuario(null, "nome2", "sobrenome2", "22426853093",
            null, LOCAL_DATE, LOCAL_DATE, true, ENDERECO_ATUALIZADO_PAIS_NOVO);

    public static final Usuario USUARIO_INATIVO = new Usuario(null, "nome", "sobrenome", "22426853093", null,
            LOCAL_DATE, LOCAL_DATE, false, ENDERECO);

    public static final Usuario USUARIO_INVALIDO = new Usuario(null, "", "", "", null, null, null, false,
            ENDERECO_INVALIDO);

    public static final Usuario USUARIO_VAZIO = new Usuario();

}
