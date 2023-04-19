package com.github.andregpereira.resilientshop.userapi.cross.mappers;

import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.ENDERECO;
import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoDtoConstants.ENDERECO_DTO;
import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoDtoConstants.ENDERECO_REGISTRO_DTO;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisDtoConstants.PAIS_DTO;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisDtoConstants.PAIS_REGISTRO_DTO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.USUARIO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioDtoConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UsuarioMapperImpl.class)
class UsuarioMapperTest {

    @InjectMocks
    private UsuarioMapperImpl mapper;

    @Test
    void usuarioRegistroDtoRetornaUsuario() {
        assertThat(mapper.toUsuario(USUARIO_REGISTRO_DTO)).isNotNull().isExactlyInstanceOf(Usuario.class);
    }

    @Test
    void usuarioAtualizacaoDtoRetornaUsuario() {
        assertThat(mapper.toUsuario(USUARIO_ATUALIZACAO_DTO)).isNotNull().isExactlyInstanceOf(Usuario.class);
    }

    @Test
    void usuarioRegistroDtoNuloRetornaNull() {
        assertThat(mapper.toUsuario((UsuarioRegistroDto) null)).isNotEqualTo(USUARIO);
    }

    @Test
    void usuarioAtualizacaoDtoNuloRetornaNull() {
        assertThat(mapper.toUsuario((UsuarioAtualizacaoDto) null)).isNotEqualTo(USUARIO);
    }

    @Test
    void usuarioRetornaUsuarioDto() {
        assertThat(mapper.toUsuarioDto(USUARIO)).isEqualTo(USUARIO_DTO);
    }

    @Test
    void usuarioRetornaUsuarioDetalhesDto() {
        assertThat(mapper.toUsuarioDetalhesDto(USUARIO)).isEqualTo(USUARIO_DETALHES_DTO);
    }

    @Test
    void usuarioNuloRetornaUsuarioDtoNull() {
        assertThat(mapper.toUsuarioDto(null)).isNotEqualTo(USUARIO_DTO);
    }

    @Test
    void usuarioNuloRetornaUsuarioDetalhesDtoNull() {
        assertThat(mapper.toUsuarioDetalhesDto(null)).isNotEqualTo(USUARIO_DETALHES_DTO);
    }

    @Test
    void enderecoRegistroDtoRetornaEndereco() {
        assertThat(mapper.enderecoRegistroDtoToEndereco(ENDERECO_REGISTRO_DTO)).isEqualTo(ENDERECO);
    }

    @Test
    void enderecoRegistroDtoNuloRetornaNull() {
        assertThat(mapper.enderecoRegistroDtoToEndereco(null)).isNotEqualTo(ENDERECO);
    }

    @Test
    void enderecoRetornaEnderecoDto() {
        assertThat(mapper.enderecoToEnderecoDto(ENDERECO)).isEqualTo(ENDERECO_DTO);
    }

    @Test
    void enderecoNuloRetornaNull() {
        assertThat(mapper.enderecoToEnderecoDto(null)).isNotEqualTo(ENDERECO_DTO);
    }

    @Test
    void paisRegistroDtoRetornaPais() {
        assertThat(mapper.paisRegistroDtoToPais(PAIS_REGISTRO_DTO)).isEqualTo(PAIS);
    }

    @Test
    void paisRegistroDtoNuloRetornaNull() {
        assertThat(mapper.paisRegistroDtoToPais(null)).isNotEqualTo(PAIS);
    }

    @Test
    void paisRetornaPaisDto() {
        assertThat(mapper.paisToPaisDto(PAIS)).isEqualTo(PAIS_DTO);
    }

    @Test
    void paisNuloRetornaNull() {
        assertThat(mapper.paisToPaisDto(null)).isNotEqualTo(PAIS_DTO);
    }

}
