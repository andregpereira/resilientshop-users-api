package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;

import java.time.LocalDate;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.*;

public class UsuarioConstants {

    public static final LocalDate LOCAL_DATE = LocalDate.now();

    public static final Usuario USUARIO = new Usuario(null, "nome", "sobrenome", "22426853093", null, LOCAL_DATE,
            LOCAL_DATE, true, LISTA_ENDERECOS);

    public static final Usuario USUARIO_INATIVO = new Usuario(null, "nome", "sobrenome", "22426853093", null,
            LOCAL_DATE, LOCAL_DATE, false, LISTA_ENDERECOS_USUARIO_INATIVO);

    public static final Usuario USUARIO_MAPEADO = new Usuario(null, "nome", "sobrenome", "22426853093", null, null,
            null, false, LISTA_ENDERECOS_MAPEADO);

    public static final Usuario USUARIO_PAIS_NOVO = new Usuario(null, "nome", "sobrenome", "22426853093", null,
            LOCAL_DATE, LOCAL_DATE, true, LISTA_ENDERECOS_PAIS_NOVO);

    public static final Usuario USUARIO_ATUALIZADO = new Usuario(null, "nome2", "sobrenome2", "22426853093", null,
            LOCAL_DATE, LOCAL_DATE, true, LISTA_ENDERECOS_ATUALIZADO);

    public static final Usuario USUARIO_ATUALIZADO_MAPEADO = new Usuario(null, "nome2", "sobrenome2", null, null,
            LOCAL_DATE, null, false, LISTA_ENDERECOS_ATUALIZADO_MAPEADO);

    public static final Usuario USUARIO_ATUALIZADO_PAIS_NOVO = new Usuario(null, "nome2", "sobrenome2", "22426853093",
            null, LOCAL_DATE, LOCAL_DATE, true, LISTA_ENDERECOS_ATUALIZADO_PAIS_NOVO);

    public static final Usuario USUARIO_INVALIDO = new Usuario(null, "", "", "", null, null, null, false,
            LISTA_ENDERECOS_INVALIDO);

    public static final Usuario USUARIO_VAZIO = new Usuario();

}
