package com.github.andregpereira.resilientshop.userapi.cross.validations;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Pais;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.PaisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS_NOVO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PaisValidationTest {

    @InjectMocks
    private PaisValidationImpl paisValidation;

    @Mock
    private PaisRepository repository;

    @Test
    void consultarPaisExistenteRetornaPaisSalvo() {
        given(repository.findByNomeOrCodigo(PAIS.getNome(), PAIS.getCodigo())).willReturn(Optional.of(PAIS));
        Pais pais = paisValidation.validarPais(PAIS);
        assertThat(pais).isEqualTo(PAIS);
        then(repository).should().findByNomeOrCodigo(PAIS.getNome(), PAIS.getCodigo());
    }

    @Test
    void consultarPaisInexistenteRetornaPaisNovo() {
        given(repository.findByNomeOrCodigo(PAIS_NOVO.getNome(), PAIS_NOVO.getCodigo())).willReturn(Optional.empty());
        given(repository.save(PAIS_NOVO)).willReturn(PAIS_NOVO);
        Pais pais = paisValidation.validarPais(PAIS_NOVO);
        assertThat(pais).isEqualTo(PAIS_NOVO);
        then(repository).should().save(PAIS_NOVO);
    }

}
