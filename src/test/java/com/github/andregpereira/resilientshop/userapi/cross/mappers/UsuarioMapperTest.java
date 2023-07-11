package com.github.andregpereira.resilientshop.userapi.cross.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoDtoConstants.LISTA_ENDERECOS_DTO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.USUARIO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.USUARIO_MAPEADO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioDtoConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = UsuarioMapperImpl.class)
class UsuarioMapperTest {

    @InjectMocks
    private UsuarioMapperImpl usuarioMapper;

    @Mock
    private EnderecoMapper enderecoMapper;

    @BeforeEach
    void beforeEach() {
//        USUARIO.setNome("nome");
//        USUARIO.setSobrenome("apelido");
        USUARIO_MAPEADO.setEnderecos(LISTA_ENDERECOS_MAPEADO);
    }

    @Test
    void usuarioRegistroDtoRetornaUsuario() {
        given(enderecoMapper.toEndereco(USUARIO_REGISTRO_DTO.endereco())).willReturn(ENDERECO_MAPEADO);
        assertThat(usuarioMapper.toUsuario(USUARIO_REGISTRO_DTO)).isEqualTo(USUARIO_MAPEADO);
    }

    @Test
    void usuarioRegistroDtoNuloRetornaNull() {
        assertThat(usuarioMapper.toUsuario(null)).isNull();
    }

    @Test
    void usuarioRetornaUsuarioDto() {
        assertThat(usuarioMapper.toUsuarioDto(USUARIO)).isEqualTo(USUARIO_DTO);
    }

    @Test
    void usuarioNuloRetornaUsuarioDtoNull() {
        assertThat(usuarioMapper.toUsuarioDto(null)).isNull();
    }

    @Test
    void usuarioRetornaUsuarioDetalhesDto() {
        given(enderecoMapper.toListaEnderecosDto(LISTA_ENDERECOS)).willReturn(LISTA_ENDERECOS_DTO);
        assertThat(usuarioMapper.toUsuarioDetalhesDto(USUARIO)).isEqualTo(USUARIO_DETALHES_DTO);
    }

    @Test
    void usuarioNuloRetornaUsuarioDetalhesDtoNull() {
        assertThat(usuarioMapper.toUsuarioDetalhesDto(null)).isNotEqualTo(USUARIO_DETALHES_DTO);
    }

}
