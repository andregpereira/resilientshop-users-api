package com.github.andregpereira.resilientshop.userapi.repositories;

import com.github.andregpereira.resilientshop.userapi.entities.Pais;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class PaisRepositoryTest {

    @Autowired
    private PaisRepository repository;

    @Autowired
    private TestEntityManager em;

    @AfterEach
    public void afterEach() {
        PAIS.setId(null);
        PAIS_NOVO.setId(null);
    }

    @Test
    public void criarPaisComDadosValidosRetornaPais() {
        Pais pais = repository.save(PAIS);
        Pais sut = em.find(Pais.class, pais.getId());
        assertThat(sut).isNotNull();
        assertThat(sut.getNome()).isEqualTo(PAIS.getNome());
        assertThat(sut.getCodigo()).isEqualTo(PAIS.getCodigo());
    }

    @Test
    public void criarPaisComDadosInvalidosThrowsRuntimeException() {
        assertThatThrownBy(() -> repository.saveAndFlush(PAIS_VAZIO)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> repository.saveAndFlush(PAIS_INVALIDO)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void consultarPaisPorIdExistenteRetornaTrueEPais() {
        Pais pais = em.persistFlushFind(PAIS);
        Optional<Pais> optionalPais = repository.findById(pais.getId());
        assertThat(repository.existsById(pais.getId())).isTrue();
        assertThat(optionalPais).isNotEmpty().get().isNotNull().isEqualTo(PAIS);
    }

    @Test
    public void consultarPaisPorIdInexistenteRetornaFalseEEmpty() {
        Optional<Pais> optionalPais = repository.findById(10L);
        assertThat(repository.existsById(10L)).isFalse();
        assertThat(optionalPais).isEmpty();
    }

    @Test
    public void consultarPaisPorNomeOuCodigoExistentesRetornaPais() {
        em.persist(PAIS);
        em.persist(PAIS_NOVO);
        Optional<Pais> brasil = repository.findByNomeOrCodigo("Brasil", null);
        Optional<Pais> eua = repository.findByNomeOrCodigo(null, "+001");
        assertThat(brasil).isNotEmpty().get().isNotNull().isEqualTo(PAIS);
        assertThat(eua).isNotEmpty().get().isNotNull().isEqualTo(PAIS_NOVO);
    }

    @Test
    public void removerPaisPorIdExistenteRetornaNulo() {
        em.persist(PAIS);
        Pais pais = em.persistFlushFind(PAIS);
        repository.deleteById(pais.getId());
        Pais paisRemovido = em.find(Pais.class, pais.getId());
        assertThat(paisRemovido).isNull();
    }

}

