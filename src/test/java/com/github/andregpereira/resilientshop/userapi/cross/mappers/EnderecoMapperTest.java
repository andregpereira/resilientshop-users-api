package com.github.andregpereira.resilientshop.userapi.cross.mappers;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroUsuarioNovoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoDtoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisDtoConstants.PAIS_DTO;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisDtoConstants.PAIS_REGISTRO_DTO;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = EnderecoMapperImpl.class)
class EnderecoMapperTest {

    @InjectMocks
    private EnderecoMapperImpl mapper;

    @Test
    void enderecoRegistroDtoRetornaEndereco() {
        assertThat(mapper.toEndereco(ENDERECO_REGISTRO_DTO_PADRAO_FALSE)).isEqualTo(ENDERECO_MAPEADO);
    }

    @Test
    void enderecoRegistroDtoNuloRetornaEnderecoNull() {
        assertThat(mapper.toEndereco((EnderecoRegistroDto) null)).isNull();
    }
    @Test
    void enderecoRegistroUsuarioNovoDtoRetornaEndereco() {
        assertThat(mapper.toEndereco(ENDERECO_REGISTRO_USUARIO_NOVO_DTO)).isEqualTo(ENDERECO_MAPEADO);
    }

    @Test
    void enderecoRegistroUsuarioNovoDtoNuloRetornaEnderecoNull() {
        assertThat(mapper.toEndereco((EnderecoRegistroUsuarioNovoDto) null)).isNull();
    }

    @Test
    void enderecoRetornaEnderecoDto() {
        assertThat(mapper.toEnderecoDto(ENDERECO)).isEqualTo(ENDERECO_DTO);
    }

    @Test
    void enderecoNuloRetornaEnderecoDtoNull() {
        assertThat(mapper.toEnderecoDto(null)).isNull();
    }

    @Test
    void listaEnderecosRetornaListaEnderecosDto() {
        assertThat(mapper.toListaEnderecosDto(LISTA_ENDERECOS)).isEqualTo(LISTA_ENDERECOS_DTO);
    }

    @Test
    void listaEnderecosNuloRetornaListaEnderecosDtoNull() {
        assertThat(mapper.toListaEnderecosDto(null)).isNull();
    }

    @Test
    void paisRegistroDtoRetornaPais() {
        assertThat(mapper.paisRegistroDtoToPais(PAIS_REGISTRO_DTO)).isEqualTo(PAIS);
    }

    @Test
    void paisRegistroDtoNuloRetornaPaisNull() {
        assertThat(mapper.paisRegistroDtoToPais(null)).isNull();
    }

    @Test
    void paisRetornaPaisDto() {
        assertThat(mapper.paisToPaisDto(PAIS)).isEqualTo(PAIS_DTO);
    }

    @Test
    void paisNuloRetornaPaisDtoNull() {
        assertThat(mapper.paisToPaisDto(null)).isNull();
    }

}
