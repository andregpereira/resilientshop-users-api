package com.github.andregpereira.resilientshop.userapi.mappers;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;
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
public class UsuarioMapperTest {

    @InjectMocks
    private UsuarioMapperImpl mapper;

    @Test
    public void usuarioRegistroDtoRetornaUsuario() {
        assertThat(mapper.toUsuario(USUARIO_REGISTRO_DTO)).isNotNull().isExactlyInstanceOf(Usuario.class);
    }

    @Test
    public void usuarioAtualizacaoDtoRetornaUsuario() {
        assertThat(mapper.toUsuario(USUARIO_ATUALIZACAO_DTO)).isNotNull().isExactlyInstanceOf(Usuario.class);
    }

    @Test
    public void usuarioRegistroDtoNuloRetornaNull() {
        assertThat(USUARIO).isNotEqualTo(mapper.toUsuario((UsuarioRegistroDto) null));
    }

    @Test
    public void usuarioAtualizacaoDtoNuloRetornaNull() {
        assertThat(USUARIO).isNotEqualTo(mapper.toUsuario((UsuarioAtualizacaoDto) null));
    }

    @Test
    public void usuarioRetornaUsuarioDto() {
        assertThat(USUARIO_DTO).isEqualTo(mapper.toUsuarioDto(USUARIO));
    }

    @Test
    public void usuarioRetornaUsuarioDetalhesDto() {
        assertThat(USUARIO_DETALHES_DTO).isEqualTo(mapper.toUsuarioDetalhesDto(USUARIO));
    }

    @Test
    public void usuarioNuloRetornaUsuarioDtoNull() {
        assertThat(USUARIO_DTO).isNotEqualTo(mapper.toUsuarioDto(null));
    }

    @Test
    public void usuarioNuloRetornaUsuarioDetalhesDtoNull() {
        assertThat(USUARIO_DETALHES_DTO).isNotEqualTo(mapper.toUsuarioDetalhesDto(null));
    }

    @Test
    public void enderecoRegistroDtoRetornaEndereco() {
        assertThat(ENDERECO).isEqualTo(mapper.enderecoRegistroDtoToEndereco(ENDERECO_REGISTRO_DTO));
    }

    @Test
    public void enderecoRegistroDtoNuloRetornaNull() {
        assertThat(ENDERECO).isNotEqualTo(mapper.enderecoRegistroDtoToEndereco(null));
    }

    @Test
    public void enderecoRetornaEnderecoDto() {
        assertThat(ENDERECO_DTO).isEqualTo(mapper.enderecoToEnderecoDto(ENDERECO));
    }

    @Test
    public void enderecoNuloRetornaNull() {
        assertThat(ENDERECO_DTO).isNotEqualTo(mapper.enderecoToEnderecoDto(null));
    }

    @Test
    public void paisRegistroDtoRetornaPais() {
        assertThat(PAIS).isEqualTo(mapper.paisRegistroDtoToPais(PAIS_REGISTRO_DTO));
    }

    @Test
    public void paisRegistroDtoNuloRetornaNull() {
        assertThat(PAIS).isNotEqualTo(mapper.paisRegistroDtoToPais(null));
    }

    @Test
    public void paisRetornaPaisDto() {
        assertThat(PAIS_DTO).isEqualTo(mapper.paisToPaisDto(PAIS));
    }

    @Test
    public void paisNuloRetornaNull() {
        assertThat(PAIS_DTO).isNotEqualTo(mapper.paisToPaisDto(null));
    }

}
