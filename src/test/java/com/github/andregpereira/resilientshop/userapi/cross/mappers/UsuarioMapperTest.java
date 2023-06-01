package com.github.andregpereira.resilientshop.userapi.cross.mappers;

import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioRegistroDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoDtoConstants.LISTA_ENDERECOS_DTO;
import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoDtoConstants.LISTA_ENDERECOS_REGISTRO_NOVO_USUARIO_DTO_ATUALIZADO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.*;
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

    @Test
    void usuarioRegistroDtoRetornaUsuario() {
        given(enderecoMapper.listaEnderecoRegistroDtoToListaEnderecos(USUARIO_REGISTRO_DTO.enderecos())).willReturn(
                LISTA_ENDERECOS_MAPEADO);
        assertThat(usuarioMapper.toUsuario(USUARIO_REGISTRO_DTO)).isEqualTo(USUARIO_MAPEADO);
    }

    @Test
    void usuarioRegistroDtoNuloRetornaNull() {
        assertThat(usuarioMapper.toUsuario((UsuarioRegistroDto) null)).isNull();
    }

    @Test
    void usuarioAtualizacaoDtoRetornaUsuario() {
        given(enderecoMapper.listaEnderecoRegistroDtoToListaEnderecos(
                LISTA_ENDERECOS_REGISTRO_NOVO_USUARIO_DTO_ATUALIZADO)).willReturn(LISTA_ENDERECOS_ATUALIZADO_MAPEADO);
        assertThat(usuarioMapper.toUsuario(USUARIO_ATUALIZACAO_DTO)).isEqualTo(USUARIO_ATUALIZADO_MAPEADO);
    }

    @Test
    void usuarioAtualizacaoDtoNuloRetornaNull() {
        assertThat(usuarioMapper.toUsuario((UsuarioAtualizacaoDto) null)).isNull();
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
