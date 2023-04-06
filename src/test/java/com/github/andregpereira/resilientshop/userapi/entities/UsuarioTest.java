package com.github.andregpereira.resilientshop.userapi.entities;

import org.junit.jupiter.api.Test;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.ENDERECO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UsuarioTest {

    @Test
    public void equalsIgualRetornaTrue() {
        assertThat(USUARIO).isEqualTo((Object) USUARIO);
        assertThat(USUARIO).isEqualTo(
                new Usuario(null, "nome", "sobrenome", "22426853093", null, LOCAL_DATE, LOCAL_DATE, true, ENDERECO));
    }

    @Test
    public void equalsDivergenteRetornaFalse() {
        assertThat(USUARIO).isNotEqualTo(new Object());
        assertThat(USUARIO).isNotEqualTo(USUARIO_INVALIDO);
    }

    @Test
    public void hashCodeIgualRetornaTrue() {
        assertThat(USUARIO.hashCode()).isEqualTo((Object) USUARIO.hashCode());
    }

    @Test
    public void hashCodeDivergenteRetornaFalse() {
        assertThat(USUARIO.hashCode()).isNotEqualTo((Object) USUARIO_ATUALIZADO.hashCode());
    }

}
