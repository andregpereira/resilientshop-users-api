package com.github.andregpereira.resilientshop.userapi.infra.repositories;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.config.PostgreSQLContainerConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS_NOVO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.USUARIO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class EnderecoRepositoryPostgreSQLContainerTest extends PostgreSQLContainerConfig {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private TestEntityManager em;

    @AfterEach
    public void afterEach() {
        USUARIO.setId(null);
        ENDERECO.setId(null);
        ENDERECO_ATUALIZADO.setId(null);
        ENDERECO_ATUALIZADO_PAIS_NOVO.setId(null);
        PAIS.setId(null);
        PAIS_NOVO.setId(null);
    }

    @Test
    void criarEnderecoComDadosValidosRetornaEndereco() {
        em.persist(PAIS);
        em.persist(USUARIO);
        Endereco endereco = repository.save(ENDERECO);
        Endereco sut = em.find(Endereco.class, endereco.getId());
        assertThat(sut).isNotNull();
        assertThat(sut.getCep()).isEqualTo(ENDERECO.getCep());
        assertThat(sut.getEstado()).isEqualTo(ENDERECO.getEstado());
        assertThat(sut.getCidade()).isEqualTo(ENDERECO.getCidade());
        assertThat(sut.getBairro()).isEqualTo(ENDERECO.getBairro());
        assertThat(sut.getRua()).isEqualTo(ENDERECO.getRua());
        assertThat(sut.getNumero()).isEqualTo(ENDERECO.getNumero());
        assertThat(sut.getComplemento()).isEqualTo(ENDERECO.getComplemento());
        assertThat(sut.getPais()).isEqualTo(ENDERECO.getPais());
    }

    @Test
    void criarEnderecoComDadosInvalidosThrowsRuntimeException() {
        assertThatThrownBy(() -> repository.saveAndFlush(ENDERECO_VAZIO)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> repository.save(ENDERECO_INVALIDO)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void atualizarEnderecoComDadosValidosRetornaEndereco() {
        em.persist(PAIS);
        em.persist(USUARIO);
        Endereco enderecoAntigo = em.persistFlushFind(ENDERECO);
        Endereco enderecoAtualizado = ENDERECO_ATUALIZADO;
        enderecoAtualizado.setId(enderecoAntigo.getId());
        Endereco sut = repository.save(enderecoAtualizado);
        assertThat(sut).isNotNull();
        assertThat(sut.getId()).isEqualTo(enderecoAntigo.getId());
        assertThat(sut.getCep()).isEqualTo(enderecoAntigo.getCep());
        assertThat(sut.getEstado()).isEqualTo(enderecoAntigo.getEstado());
        assertThat(sut.getCidade()).isEqualTo(enderecoAntigo.getCidade());
        assertThat(sut.getBairro()).isEqualTo(enderecoAntigo.getBairro());
        assertThat(sut.getRua()).isEqualTo(enderecoAntigo.getRua());
        assertThat(sut.getNumero()).isEqualTo(enderecoAntigo.getNumero());
        assertThat(sut.getComplemento()).isEqualTo(enderecoAntigo.getComplemento());
        assertThat(sut.getPais()).isEqualTo(enderecoAntigo.getPais());
    }

    @Test
    void atualizarEnderecoComDadosInvalidosThrowsRuntimeException() {
        em.persist(PAIS);
        em.persist(USUARIO);
        Endereco enderecoAntigo = em.persistFlushFind(ENDERECO);
        Endereco sutVazio = ENDERECO_VAZIO;
        Endereco sutInvalido = ENDERECO_INVALIDO;
        sutVazio.setId(enderecoAntigo.getId());
        sutInvalido.setId(enderecoAntigo.getId());
        assertThatThrownBy(() -> repository.saveAndFlush(sutVazio)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> repository.saveAndFlush(sutInvalido)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void consultarEnderecoPorIdExistenteRetornaTrueEEndereco() {
        em.persist(PAIS);
        em.persist(USUARIO);
        Endereco endereco = em.persistFlushFind(ENDERECO);
        Optional<Endereco> optionalEndereco = repository.findById(endereco.getId());
        assertThat(repository.existsById(endereco.getId())).isTrue();
        assertThat(optionalEndereco).isNotEmpty().get().isNotNull().isEqualTo(ENDERECO);
    }

    @Test
    void consultarEnderecoPorIdInexistenteRetornaFalseEEmpty() {
        Optional<Endereco> optionalEndereco = repository.findById(10L);
        assertThat(repository.existsById(10L)).isFalse();
        assertThat(optionalEndereco).isEmpty();
    }

    @Test
    void removerEnderecoPorIdExistenteRetornaNulo() {
        em.persist(PAIS);
        em.persist(USUARIO);
        Endereco endereco = em.persistFlushFind(ENDERECO);
        repository.deleteById(endereco.getId());
        Endereco enderecoRemovido = em.find(Endereco.class, endereco.getId());
        assertThat(enderecoRemovido).isNull();
    }

}

