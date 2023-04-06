package com.github.andregpereira.resilientshop.userapi.entities;

import org.junit.jupiter.api.Test;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.ENDERECO;
import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.ENDERECO_ATUALIZADO;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS;
import static org.assertj.core.api.Assertions.assertThat;

public class EnderecoTest {

    @Test
    public void equalsIgualRetornaTrue() {
        assertThat(ENDERECO).isEqualTo((Object) ENDERECO);
        assertThat(ENDERECO).isEqualTo(
                new Endereco(null, "12345-678", "estado", "cidade", "bairro", "rua", "20", null, PAIS));
    }

    @Test
    public void equalsDivergenteRetornaFalse() {
        assertThat(ENDERECO).isNotEqualTo(null);
        assertThat(ENDERECO).isNotEqualTo(new Object());
//        assertThat(ENDERECO).isNotEqualTo(ENDERECO_INVALIDO);
//        assertThat(ENDERECO).isNotEqualTo(ENDERECO_PAIS_NOVO);
//        assertThat(ENDERECO).isNotEqualTo(ENDERECO_ATUALIZADO);
//        assertThat(ENDERECO).isNotEqualTo(ENDERECO_ATUALIZADO_PAIS_NOVO);
//        assertThat(ENDERECO).isNotEqualTo(new Endereco(1L, "", "", "", "", "", "", null, null));
//        assertThat(ENDERECO).isNotEqualTo(new Endereco(null, "12345-678", "", "", "", "", "", null, null));
//        assertThat(ENDERECO).isNotEqualTo(new Endereco(null, "", "estado", "", "", "", "", null, null));
//        assertThat(ENDERECO).isNotEqualTo(new Endereco(null, "", "", "cidade", "", "", "", null, null));
//        assertThat(ENDERECO).isNotEqualTo(new Endereco(null, "", "", "", "bairro", "", "", null, null));
//        assertThat(ENDERECO).isNotEqualTo(new Endereco(null, null, null, null, null, "rua", null, null, null));
//        assertThat(ENDERECO).isNotEqualTo(new Endereco(null, "", "", "", "", "", "numero", null, null));
//        assertThat(ENDERECO).isNotEqualTo(new Endereco(null, "", "", "", "", "", "", "complemento", null));
//        assertThat(ENDERECO).isNotEqualTo(new Endereco(null, "", "", "", "", "", "", "", PAIS));
    }

    @Test
    public void hashCodeIgualRetornaTrue() {
        assertThat(ENDERECO.hashCode()).isEqualTo((Object) ENDERECO.hashCode());
    }

    @Test
    public void hashCodeDivergenteRetornaFalse() {
        assertThat(ENDERECO.hashCode()).isNotEqualTo((Object) ENDERECO_ATUALIZADO.hashCode());
    }

}
