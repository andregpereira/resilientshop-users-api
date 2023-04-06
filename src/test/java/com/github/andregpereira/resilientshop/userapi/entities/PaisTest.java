package com.github.andregpereira.resilientshop.userapi.entities;

import org.junit.jupiter.api.Test;

import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS_NOVO;
import static org.assertj.core.api.Assertions.assertThat;

public class PaisTest {

    @Test
    public void equalsIgualRetornaTrue() {
        assertThat(PAIS).isEqualTo((Object) PAIS);
        assertThat(PAIS).isEqualTo(new Pais(null, "Brasil", "+055"));
    }

    @Test
    public void equalsDivergenteRetornaFalse() {
        assertThat(PAIS).isNotEqualTo(null);
        assertThat(PAIS).isNotEqualTo(new Object());
        assertThat(PAIS).isNotEqualTo(new Pais(1L, "", ""));
        assertThat(PAIS).isNotEqualTo(new Pais(null, "Brasil", ""));
        assertThat(PAIS).isNotEqualTo(new Pais(null, "", "+055"));
    }

    @Test
    public void hashCodeIgualRetornaTrue() {
        assertThat(PAIS.hashCode()).isEqualTo((Object) PAIS.hashCode());
    }

    @Test
    public void hashCodeDivergenteRetornaFalse() {
        assertThat(PAIS.hashCode()).isNotEqualTo((Object) PAIS_NOVO.hashCode());
    }

}
