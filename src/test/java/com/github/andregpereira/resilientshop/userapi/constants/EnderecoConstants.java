package com.github.andregpereira.resilientshop.userapi.constants;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.*;

public class EnderecoConstants {

    public static final PageRequest PAGEABLE_ENDERECOS = PageRequest.of(0, 10, Sort.Direction.ASC, "id");

    public static final Endereco ENDERECO = new Endereco(null, "apelido", "12345-678", "estado", "cidade", "bairro",
            "rua", "20", null, true, PAIS, USUARIO);

    public static final Endereco ENDERECO_PADRAO_FALSE = new Endereco(null, "apelido", "12345-678", "estado", "cidade",
            "bairro", "rua", "20", null, false, PAIS, USUARIO);

    public static final Endereco ENDERECO_MAPEADO = new Endereco(null, "apelido", "12345-678", "estado", "cidade",
            "bairro", "rua", "20", null, true, PAIS_MAPEADO, USUARIO_MAPEADO);

    public static final Endereco ENDERECO_PADRAO_FALSE_MAPEADO = new Endereco(null, "apelido", "12345-678", "estado",
            "cidade", "bairro", "rua", "20", null, false, PAIS_MAPEADO, USUARIO_MAPEADO);

    public static final Endereco ENDERECO_USUARIO_INATIVO = new Endereco(null, "apelido", "12345-678", "estado",
            "cidade", "bairro", "rua", "20", null, true, PAIS, USUARIO_INATIVO);

    public static final Endereco ENDERECO_PAIS_NOVO = new Endereco(null, "apelido", "12345-678", "estado", "cidade",
            "bairro", "rua", "20", null, true, PAIS_NOVO, USUARIO_PAIS_NOVO);

    public static final Endereco ENDERECO_PAIS_NOVO_MAPEADO = new Endereco(null, "apelido", "12345-678", "estado",
            "cidade", "bairro", "rua", "20", null, true, PAIS_NOVO, USUARIO_PAIS_NOVO_MAPEADO);

    public static final Endereco ENDERECO_ATUALIZADO = new Endereco(null, "apelido2", "12345-670", "estado2", "cidade2",
            "bairro2", "rua2", "202", null, true, PAIS, USUARIO_ATUALIZADO);

    public static final Endereco ENDERECO_ATUALIZADO_MAPEADO = new Endereco(null, "apelido2", "12345-670", "estado2",
            "cidade2", "bairro2", "rua2", "202", null, true, PAIS_MAPEADO, USUARIO_ATUALIZADO_MAPEADO);

    public static final Endereco ENDERECO_ATUALIZADO_PADRAO_FALSE_MAPEADO = new Endereco(null, "apelido2", "12345-670",
            "estado2", "cidade2", "bairro2", "rua2", "202", null, false, PAIS_MAPEADO, USUARIO_ATUALIZADO_MAPEADO);

    public static final Endereco ENDERECO_ATUALIZADO_PAIS_NOVO = new Endereco(null, "apelido2", "12345-670", "estado2",
            "cidade2", "bairro2", "rua2", "202", null, true, PAIS_NOVO, USUARIO_ATUALIZADO_PAIS_NOVO);

    public static final Endereco ENDERECO_ATUALIZADO_PAIS_NOVO_MAPEADO = new Endereco(null, "apelido2", "12345-670",
            "estado2", "cidade2", "bairro2", "rua2", "202", null, true, PAIS_NOVO, USUARIO_ATUALIZADO_PAIS_NOVO);

    public static final Endereco ENDERECO_INVALIDO = new Endereco(null, "", "", "", "", "", "", null, null, true,
            PAIS_INVALIDO, USUARIO_INVALIDO);

    public static final Endereco ENDERECO_VAZIO = new Endereco();

    public static final List<Endereco> LISTA_ENDERECOS = new ArrayList<>() {
        {
            add(ENDERECO);
        }
    };

    public static final Page<Endereco> PAGE_ENDERECOS = new PageImpl<>(LISTA_ENDERECOS);

    public static final List<Endereco> LISTA_ENDERECOS_ATUALIZADO = new ArrayList<>() {
        {
            add(ENDERECO_ATUALIZADO);
        }
    };

    public static final List<Endereco> LISTA_ENDERECOS_MAPEADO = new ArrayList<>() {
        {
            add(ENDERECO_MAPEADO);
        }
    };

    public static final List<Endereco> LISTA_ENDERECOS_USUARIO_INATIVO = new ArrayList<>() {
        {
            add(ENDERECO_USUARIO_INATIVO);
        }
    };

    public static final List<Endereco> LISTA_ENDERECOS_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_PAIS_NOVO);
        }
    };

    public static final List<Endereco> LISTA_ENDERECOS_PAIS_NOVO_MAPEADO = new ArrayList<>() {
        {
            add(ENDERECO_PAIS_NOVO_MAPEADO);
        }
    };

    public static final List<Endereco> LISTA_ENDERECOS_ATUALIZADO_MAPEADO = new ArrayList<>() {
        {
            add(ENDERECO_ATUALIZADO_MAPEADO);
        }
    };

    public static final List<Endereco> LISTA_ENDERECOS_ATUALIZADO_PAIS_NOVO = new ArrayList<>() {
        {
            add(ENDERECO_ATUALIZADO_PAIS_NOVO);
        }
    };

    public static final List<Endereco> LISTA_ENDERECOS_INVALIDO = new ArrayList<>() {
        {
            add(ENDERECO_INVALIDO);
        }
    };

}
