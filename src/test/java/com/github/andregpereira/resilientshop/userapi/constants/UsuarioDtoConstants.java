package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioRegistroDto;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoDtoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.LOCAL_DATE;

public class UsuarioDtoConstants {

    public static final UsuarioDto USUARIO_DTO = new UsuarioDto(null, "nome", "sobrenome", "22426853093", null,
            LOCAL_DATE, LOCAL_DATE, true);

    public static final UsuarioDto USUARIO_DTO_ATUALIZADO = new UsuarioDto(null, "nome2", "sobrenome2", "22426853093",
            null, LOCAL_DATE, LOCAL_DATE, true);

    public static final UsuarioDetalhesDto USUARIO_DETALHES_DTO = new UsuarioDetalhesDto(null, "nome", "sobrenome",
            "22426853093", null, LOCAL_DATE, LOCAL_DATE, true, LISTA_ENDERECO_DTO);

    public static final UsuarioDetalhesDto USUARIO_DETALHES_DTO_PAIS_NOVO = new UsuarioDetalhesDto(null, "nome",
            "sobrenome", "22426853093", null, LOCAL_DATE, LOCAL_DATE, true, LISTA_ENDERECO_DTO_PAIS_NOVO);

    public static final UsuarioDetalhesDto USUARIO_DETALHES_DTO_ATUALIZADO = new UsuarioDetalhesDto(null, "nome2",
            "sobrenome2", "22426853093", null, LOCAL_DATE, LOCAL_DATE, true, LISTA_ENDERECO_DTO_ATUALIZADO);

    public static final UsuarioDetalhesDto USUARIO_DETALHES_DTO_ATUALIZADO_PAIS_NOVO = new UsuarioDetalhesDto(null,
            "nome2", "sobrenome2", "22426853093", null, LOCAL_DATE, LOCAL_DATE, true,
            LISTA_ENDERECO_DTO_ATUALIZADO_PAIS_NOVO);

    public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO = new UsuarioRegistroDto("nome", "sobrenome",
            "22426853093", null, LISTA_ENDERECO_REGISTRO_DTO);

    public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO_PAIS_NOVO = new UsuarioRegistroDto("nome", "sobrenome",
            "22426853093", null, LISTA_ENDERECO_REGISTRO_DTO_PAIS_NOVO);

    public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO_INVALIDO = new UsuarioRegistroDto("", "", "", null,
            LISTA_ENDERECO_REGISTRO_DTO_INVALIDO);

    public static final UsuarioAtualizacaoDto USUARIO_ATUALIZACAO_DTO = new UsuarioAtualizacaoDto("nome2", "sobrenome2",
            null, LISTA_ENDERECO_REGISTRO_DTO_ATUALIZADO);

    public static final UsuarioAtualizacaoDto USUARIO_ATUALIZACAO_DTO_PAIS_NOVO = new UsuarioAtualizacaoDto("nome2",
            "sobrenome2", null, LISTA_ENDERECO_REGISTRO_DTO_ATUALIZADO_PAIS_NOVO);

    public static final UsuarioAtualizacaoDto USUARIO_ATUALIZACAO_DTO_INVALIDO = new UsuarioAtualizacaoDto("", "", null,
            LISTA_ENDERECO_REGISTRO_DTO_INVALIDO);

}
