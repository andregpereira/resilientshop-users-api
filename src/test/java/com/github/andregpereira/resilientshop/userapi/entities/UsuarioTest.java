package com.github.andregpereira.resilientshop.userapi.entities;

import org.junit.jupiter.api.Test;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.ENDERECO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

class UsuarioTest {

    @Test
    void equalsIgualRetornaTrue() {
        assertThat((Object) USUARIO).isEqualTo(USUARIO);
        assertThat(new Usuario(null, "nome", "sobrenome", "22426853093", null, LOCAL_DATE, LOCAL_DATE, true,
                ENDERECO)).isEqualTo(USUARIO);
    }

    @Test
    void equalsDivergenteRetornaFalse() {
        assertThat(USUARIO).isNotEqualTo(null);
        assertThat(new Object()).isNotEqualTo(USUARIO);
        assertThat(USUARIO_INVALIDO).isNotEqualTo(USUARIO);
    }

    @Test
    void hashCodeIgualRetornaTrue() {
        assertThat(USUARIO.hashCode()).isEqualTo((Object) USUARIO.hashCode());
    }

    @Test
    void hashCodeDivergenteRetornaFalse() {
        assertThat(USUARIO.hashCode()).isNotEqualTo((Object) USUARIO_ATUALIZADO.hashCode());
    }

}
