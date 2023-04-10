package com.github.andregpereira.resilientshop.userapi.entities;

import org.junit.jupiter.api.Test;

import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS_NOVO;
import static org.assertj.core.api.Assertions.assertThat;

class PaisTest {

    @Test
    void equalsIgualRetornaTrue() {
        assertThat((Object) PAIS).isEqualTo(PAIS);
        assertThat(new Pais(null, "Brasil", "+055")).isEqualTo(PAIS);
    }

    @Test
    void equalsDivergenteRetornaFalse() {
        assertThat(PAIS).isNotEqualTo(null);
        assertThat(new Object()).isNotEqualTo(PAIS);
        assertThat(new Pais(1L, "", "")).isNotEqualTo(PAIS);
        assertThat(new Pais(null, "Brasil", "")).isNotEqualTo(PAIS);
        assertThat(new Pais(null, "", "+055")).isNotEqualTo(PAIS);
    }

    @Test
    void hashCodeIgualRetornaTrue() {
        assertThat(PAIS.hashCode()).isEqualTo((Object) PAIS.hashCode());
    }

    @Test
    void hashCodeDivergenteRetornaFalse() {
        assertThat(PAIS.hashCode()).isNotEqualTo((Object) PAIS_NOVO.hashCode());
    }

}
