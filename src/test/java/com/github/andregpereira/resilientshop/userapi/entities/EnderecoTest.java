package com.github.andregpereira.resilientshop.userapi.entities;

import org.junit.jupiter.api.Test;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.ENDERECO;
import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.ENDERECO_ATUALIZADO;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS;
import static org.assertj.core.api.Assertions.assertThat;

class EnderecoTest {

    @Test
    void equalsIgualRetornaTrue() {
        assertThat((Object) ENDERECO).isEqualTo(ENDERECO);
        assertThat(new Endereco(null, "12345-678", "estado", "cidade", "bairro", "rua", "20", null, PAIS)).isEqualTo(
                ENDERECO);
    }

    @Test
    void equalsDivergenteRetornaFalse() {
        assertThat(ENDERECO).isNotEqualTo(null);
        assertThat(new Object()).isNotEqualTo(ENDERECO);
    }

    @Test
    void hashCodeIgualRetornaTrue() {
        assertThat(ENDERECO.hashCode()).isEqualTo((Object) ENDERECO.hashCode());
    }

    @Test
    void hashCodeDivergenteRetornaFalse() {
        assertThat(ENDERECO.hashCode()).isNotEqualTo((Object) ENDERECO_ATUALIZADO.hashCode());
    }

}
