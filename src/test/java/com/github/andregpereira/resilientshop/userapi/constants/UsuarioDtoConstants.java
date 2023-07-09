package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioRegistroDto;

import java.util.ArrayList;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoDtoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.LOCAL_DATE;

public class UsuarioDtoConstants {

    public static final UsuarioDto USUARIO_DTO = new UsuarioDto(null, "nome", "apelido", "22426853093", LOCAL_DATE,
            "teste@teste.com", null, LOCAL_DATE, LOCAL_DATE, true);

    public static final UsuarioDto USUARIO_DTO_ATUALIZADO = new UsuarioDto(null, "nome2", "sobrenome2", "22426853093",
            LOCAL_DATE, "teste2@teste2.com", null, LOCAL_DATE, LOCAL_DATE, true);

    public static final UsuarioDetalhesDto USUARIO_DETALHES_DTO = new UsuarioDetalhesDto(null, "nome", "apelido",
            "22426853093", LOCAL_DATE, "teste@teste.com", null, LOCAL_DATE, LOCAL_DATE, true, LISTA_ENDERECOS_DTO);

    public static final UsuarioDetalhesDto USUARIO_DETALHES_DTO_SEM_ENDERECO = new UsuarioDetalhesDto(null, "nome",
            "apelido", "22426853093", LOCAL_DATE, "teste@teste.com", null, LOCAL_DATE, LOCAL_DATE, true,
            new ArrayList<>());

    public static final UsuarioDetalhesDto USUARIO_DETALHES_DTO_PAIS_NOVO = new UsuarioDetalhesDto(null, "nome",
            "apelido", "22426853093", LOCAL_DATE, "teste@teste.com", null, LOCAL_DATE, LOCAL_DATE, true,
            LISTA_ENDERECOS_DTO_PAIS_NOVO);

    public static final UsuarioDetalhesDto USUARIO_DETALHES_DTO_ATUALIZADO = new UsuarioDetalhesDto(null, "nome2",
            "sobrenome2", "22426853093", LOCAL_DATE, "teste2@teste2.com", null, LOCAL_DATE, LOCAL_DATE, true,
            LISTA_ENDERECOS_DTO);

    public static final UsuarioDetalhesDto USUARIO_DETALHES_DTO_ATUALIZADO_PAIS_NOVO = new UsuarioDetalhesDto(null,
            "nome2", "sobrenome2", "22426853093", LOCAL_DATE, "teste2@teste2.com", null, LOCAL_DATE, LOCAL_DATE, true,
            LISTA_ENDERECOS_DTO_ATUALIZADO_PAIS_NOVO);

    public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO = new UsuarioRegistroDto("nome", "apelido",
            "22426853093", LOCAL_DATE, "teste@teste.com", null, "senha123", "senha123",
            ENDERECO_REGISTRO_USUARIO_NOVO_DTO);

    public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO_SEM_ENDERECO = new UsuarioRegistroDto("nome", "apelido",
            "22426853093", LOCAL_DATE, "teste@teste.com", null, "senha123", "senha123", null);

    public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO_PAIS_NOVO = new UsuarioRegistroDto("nome", "apelido",
            "22426853093", LOCAL_DATE, "teste@teste.com", null, "senha123", "senha123",
            ENDERECO_REGISTRO_USUARIO_NOVO_DTO_PAIS_NOVO);

    public static final UsuarioRegistroDto USUARIO_REGISTRO_DTO_INVALIDO = new UsuarioRegistroDto("", "", "",
            LOCAL_DATE, "teste@teste.com", null, "", "", ENDERECO_REGISTRO_USUARIO_NOVO_DTO_INVALIDO);

    public static final UsuarioAtualizacaoDto USUARIO_ATUALIZACAO_DTO = new UsuarioAtualizacaoDto("nome2", "sobrenome2",
            "teste2@teste2.com", null, "senha678", "senha678");

    public static final UsuarioAtualizacaoDto USUARIO_ATUALIZACAO_DTO_PAIS_NOVO = new UsuarioAtualizacaoDto("nome2",
            "sobrenome2", "teste2@teste2.com", null, "senha678", "senha678");

    public static final UsuarioAtualizacaoDto USUARIO_ATUALIZACAO_DTO_INVALIDO = new UsuarioAtualizacaoDto("", "", "",
            "", "", "");

}
