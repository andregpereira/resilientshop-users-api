package com.github.andregpereira.resilientshop.userapi.cross.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.ENDERECO;
import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.LISTA_ENDERECOS;
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
    void listaEnderecosRegistroDtoRetornaListaEnderecos() {
        assertThat(mapper.listaEnderecoRegistroDtoToListaEnderecos(LISTA_ENDERECOS_REGISTRO_DTO)).isEqualTo(
                LISTA_ENDERECOS);
    }

    @Test
    void listaEnderecosRegistroDtoNuloRetornaNullListaEnderecos() {
        assertThat(mapper.listaEnderecoRegistroDtoToListaEnderecos(null)).isNull();
    }

    @Test
    void listaEnderecosDtoRetornaListaEnderecos() {
        assertThat(mapper.listaEnderecoDtoToListaEnderecos(LISTA_ENDERECOS_DTO)).isEqualTo(LISTA_ENDERECOS);
    }

    @Test
    void listaEnderecosDtoNuloRetornaNullListaEnderecos() {
        assertThat(mapper.listaEnderecoDtoToListaEnderecos(null)).isNull();
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
    void enderecoRegistroDtoRetornaEndereco() {
        assertThat(mapper.enderecoRegistroDtoToEndereco(ENDERECO_REGISTRO_DTO)).isEqualTo(ENDERECO);
    }

    @Test
    void enderecoRegistroDtoNuloRetornaEnderecoNull() {
        assertThat(mapper.enderecoRegistroDtoToEndereco(null)).isNull();
    }

    @Test
    void enderecoDtoRetornaEndereco() {
        assertThat(mapper.enderecoDtoToEndereco(ENDERECO_DTO)).isEqualTo(ENDERECO);
    }

    @Test
    void enderecoDtoNuloRetornaEnderecoNull() {
        assertThat(mapper.enderecoDtoToEndereco(null)).isNull();
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
    void paisRegistroDtoRetornaPais() {
        assertThat(mapper.paisRegistroDtoToPais(PAIS_REGISTRO_DTO)).isEqualTo(PAIS);
    }

    @Test
    void paisRegistroDtoNuloRetornaPaisNull() {
        assertThat(mapper.paisRegistroDtoToPais(null)).isNull();
    }

    @Test
    void paisDtoRetornaPais() {
        assertThat(mapper.paisDtoToPais(PAIS_DTO)).isEqualTo(PAIS);
    }

    @Test
    void paisDtoNuloRetornaPaisNull() {
        assertThat(mapper.paisDtoToPais(null)).isNull();
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
