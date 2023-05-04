package com.github.andregpereira.resilientshop.userapi.infra.repositories;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import java.util.Optional;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS_NOVO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TestEntityManager em;

    @AfterEach
    public void afterEach() {
        USUARIO.setId(null);
        USUARIO.setAtivo(true);
        USUARIO_INATIVO.setId(null);
        USUARIO_INATIVO.setAtivo(false);
        USUARIO_ATUALIZADO.setId(null);
        ENDERECO.setId(null);
        ENDERECO_USUARIO_INATIVO.setId(null);
        ENDERECO_ATUALIZADO.setId(null);
        PAIS.setId(null);
        PAIS_NOVO.setId(null);
    }

    @BeforeEach
    void beforeEach() {
        ENDERECO.setUsuario(USUARIO);
        ENDERECO_USUARIO_INATIVO.setUsuario(USUARIO_INATIVO);
    }

    @Test
    void criarUsuarioComDadosValidosRetornaUsuario() {
        em.persist(PAIS);
        Usuario usuario = repository.save(USUARIO);
        em.persist(ENDERECO);
        Usuario sut = em.find(Usuario.class, usuario.getId());
        assertThat(sut).isNotNull();
        assertThat(sut.getNome()).isEqualTo(USUARIO.getNome());
        assertThat(sut.getSobrenome()).isEqualTo(USUARIO.getSobrenome());
        assertThat(sut.getCpf()).isEqualTo(USUARIO.getCpf());
        assertThat(sut.getTelefone()).isEqualTo(USUARIO.getTelefone());
        assertThat(sut.getDataCriacao()).isEqualTo(USUARIO.getDataCriacao());
        assertThat(sut.getDataModificacao()).isEqualTo(USUARIO.getDataModificacao());
        assertThat(sut.isAtivo()).isEqualTo(USUARIO.isAtivo());
        assertThat(sut.getEnderecos()).isEqualTo(USUARIO.getEnderecos());
    }

    @Test
    void criarUsuarioComDadosInvalidosThrowsRuntimeException() {
        assertThatThrownBy(() -> repository.saveAndFlush(USUARIO_VAZIO)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> repository.saveAndFlush(USUARIO_INVALIDO)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void criarUsuarioComCpfExistenteThrowsRuntimeException() {
        System.out.println(ENDERECO.getId());
        System.out.println(ENDERECO.getId());
        System.out.println(ENDERECO.getId());
        System.out.println(ENDERECO.getId());
        System.out.println(ENDERECO.getId());
        System.out.println(ENDERECO.getId());
        em.persist(PAIS);
        Usuario sut = em.persistFlushFind(USUARIO);
        em.persist(ENDERECO);
        sut.setId(null);
        assertThatThrownBy(() -> repository.saveAndFlush(sut)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void consultarUsuarioPorIdExistenteEAtivoRetornaTrueEUsuario() {
        em.persist(PAIS);
        Usuario sut = em.persistFlushFind(USUARIO);
        em.persist(ENDERECO);
        Optional<Usuario> optionalUsuario = repository.findByIdAndAtivoTrue(sut.getId());
        assertThat(repository.existsById(sut.getId())).isTrue();
        assertThat(optionalUsuario).isNotEmpty().get().isEqualTo(sut);
    }

    @Test
    void consultarUsuarioPorIdExistenteEInativoRetornaTrueEUsuario() {
        Usuario usuario = em.persistFlushFind(USUARIO_INATIVO);
        em.persist(PAIS);
        em.persist(ENDERECO_USUARIO_INATIVO);
        Optional<Usuario> optionalUsuario = repository.findByIdAndAtivoFalse(usuario.getId());
        assertThat(repository.existsById(usuario.getId())).isTrue();
        assertThat(optionalUsuario).isNotEmpty().get().isEqualTo(usuario);
    }

    @Test
    void consultarUsuarioPorIdInexistenteRetornaFalseEEmpty() {
        Optional<Usuario> optionalUsuario = repository.findById(10L);
        assertThat(repository.existsById(10L)).isFalse();
        assertThat(optionalUsuario).isEmpty();
    }

    @Test
    void consultarUsuarioPorCpfExistenteEAtivoRetornaTrueEUsuario() {
        em.persist(PAIS);
        Usuario usuario = em.persistFlushFind(USUARIO);
        em.persist(ENDERECO);
        Optional<Usuario> optionalUsuario = repository.findByCpfAndAtivoTrue(usuario.getCpf());
        assertThat(repository.existsByCpf(usuario.getCpf())).isTrue();
        assertThat(optionalUsuario).isNotEmpty();
    }

    @Test
    void consultarUsuarioPorCpfExistenteEInativoRetornaTrueEEmpty() {
        em.persist(PAIS);
        Usuario usuario = em.persistFlushFind(USUARIO_INATIVO);
        em.persist(ENDERECO_USUARIO_INATIVO);
        Optional<Usuario> optionalUsuario = repository.findByCpfAndAtivoTrue(usuario.getCpf());
        assertThat(repository.existsByCpf(usuario.getCpf())).isTrue();
        assertThat(optionalUsuario).isEmpty();
    }

    @Test
    void consultarUsuarioPorCpfInexistenteRetornaFalseEEmpty() {
        Optional<Usuario> optionalUsuario = repository.findByCpfAndAtivoTrue("99999999999");
        assertThat(repository.existsByCpf("99999999999")).isFalse();
        assertThat(optionalUsuario).isEmpty();
    }

    @Test
    void consultarUsuarioPorNomeExistenteEAtivoRetornaUsuario() {
        em.persist(PAIS);
        Usuario usuario = em.persistFlushFind(USUARIO);
        em.persist(ENDERECO);
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = repository.findAllByNomeAndSobrenomeAndAtivoTrue(usuario.getNome(),
                usuario.getSobrenome(), pageable);
        assertThat(pageUsuarios).isNotEmpty().hasSize(1);
        assertThat(pageUsuarios.getContent().get(0)).isEqualTo(usuario);
    }

    @Test
    void consultarUsuariosExistentesEAtivosPorParametroVazioRetornaUsuarios() {
        em.persist(PAIS);
        Usuario sut = em.persistFlushFind(USUARIO);
        em.persist(ENDERECO);
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = repository.findAllByNomeAndSobrenomeAndAtivoTrue("", "", pageable);
        assertThat(pageUsuarios).isNotEmpty().hasSize(1);
        assertThat(pageUsuarios.getContent().get(0)).isEqualTo(sut);
    }

    @Test
    void consultarUsuarioPorNomeExistenteEInativoRetornaEmpty() {
        Usuario sut = em.persistFlushFind(USUARIO_INATIVO);
        em.persist(PAIS);
        em.persist(ENDERECO_USUARIO_INATIVO);
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = repository.findAllByNomeAndSobrenomeAndAtivoTrue(sut.getNome(), sut.getSobrenome(),
                pageable);
        assertThat(pageUsuarios).isEmpty();
    }

    @Test
    void consultarUsuarioPorNomeInexistenteRetornaEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = repository.findAllByNomeAndSobrenomeAndAtivoTrue("", "", pageable);
        assertThat(pageUsuarios).isEmpty();
    }

    @Test
    void desativarUsuarioPorIdExistenteRetornaUsuarioDesativado() {
        em.persist(PAIS);
        Usuario usuario = em.persistFlushFind(USUARIO);
        em.persist(ENDERECO);
        usuario.setAtivo(false);
        repository.save(usuario);
        Usuario usuarioDesativado = em.find(Usuario.class, usuario.getId());
        assertThat(usuarioDesativado).isNotNull();
        assertThat(usuarioDesativado.isAtivo()).isFalse();
    }

    @Test
    void reativarUsuarioPorIdExistenteRetornaUsuarioAtivado() {
        em.persist(PAIS);
        Usuario usuario = em.persistFlushFind(USUARIO_INATIVO);
        em.persist(ENDERECO_USUARIO_INATIVO);
        usuario.setAtivo(true);
        repository.save(usuario);
        Usuario usuarioDesativado = em.find(Usuario.class, usuario.getId());
        assertThat(usuarioDesativado).isNotNull();
        assertThat(usuarioDesativado.isAtivo()).isTrue();
    }

    @Test
    void removerUsuarioPorIdExistenteRetornaNulo() {
        em.persist(PAIS);
        Usuario sut = em.persistFlushFind(USUARIO);
        em.persist(ENDERECO);
        repository.deleteById(sut.getId());
        Usuario usuarioRemovido = em.find(Usuario.class, sut.getId());
        assertThat(usuarioRemovido).isNull();
    }

}

