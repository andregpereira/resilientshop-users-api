package com.github.andregpereira.resilientshop.userapi.infra.repositories;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.config.PostgreSQLContainerConfig;
import org.junit.jupiter.api.AfterEach;
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
class UsuarioRepositoryPostgreSQLContainerTest extends PostgreSQLContainerConfig {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TestEntityManager em;

    @AfterEach
    public void afterEach() {
        USUARIO.setId(null);
        USUARIO.setAtivo(true);
        USUARIO_ATUALIZADO.setId(null);
        ENDERECO.setId(null);
        ENDERECO_ATUALIZADO.setId(null);
        ENDERECO_ATUALIZADO_PAIS_NOVO.setId(null);
        PAIS.setId(null);
        PAIS_NOVO.setId(null);
    }

    @Test
    void criarUsuarioComDadosValidosRetornaUsuario() {
        Usuario usuario = repository.save(USUARIO);
        em.persist(PAIS);
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
//        assertThat(sut.getEnderecos().getPais()).isEqualTo(USUARIO.getEnderecos().getPais());
    }

    @Test
    void criarUsuarioComDadosInvalidosThrowsRuntimeException() {
        assertThatThrownBy(() -> repository.saveAndFlush(USUARIO_VAZIO)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> repository.saveAndFlush(USUARIO_INVALIDO)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void criarUsuarioComCpfExistenteThrowsRuntimeException() {
        Usuario sut = em.persistFlushFind(USUARIO);
        em.persist(PAIS);
        em.persist(ENDERECO);
        sut.setId(null);
        assertThatThrownBy(() -> repository.saveAndFlush(sut)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void atualizarUsuarioComDadosValidosRetornaUsuario() {
        Usuario usuarioAntigo = em.persistFlushFind(USUARIO);
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario usuarioAtualizado = USUARIO_ATUALIZADO;
        usuarioAtualizado.setId(usuarioAntigo.getId());
        usuarioAtualizado.setCpf(USUARIO_ATUALIZADO.getCpf());
        usuarioAtualizado.setDataCriacao(USUARIO_ATUALIZADO.getDataCriacao());
        usuarioAtualizado.setDataModificacao(LOCAL_DATE);
        usuarioAtualizado.setAtivo(true);
//        usuarioAtualizado.getEnderecos().setId(USUARIO_ATUALIZADO.getEndereco().getId());
//        usuarioAtualizado.getEnderecos().setPais(USUARIO_ATUALIZADO.getEndereco().getPais());
        em.persist(ENDERECO_ATUALIZADO);
        Usuario sut = repository.save(usuarioAtualizado);
        assertThat(sut).isNotNull();
        assertThat(sut.getId()).isEqualTo(usuarioAntigo.getId());
        assertThat(sut.getNome()).isEqualTo(USUARIO_ATUALIZADO.getNome());
        assertThat(sut.getSobrenome()).isEqualTo(USUARIO_ATUALIZADO.getSobrenome());
        assertThat(sut.getCpf()).isEqualTo(USUARIO_ATUALIZADO.getCpf());
        assertThat(sut.getTelefone()).isEqualTo(USUARIO_ATUALIZADO.getTelefone());
        assertThat(sut.getDataCriacao()).isEqualTo(USUARIO_ATUALIZADO.getDataCriacao());
        assertThat(sut.getDataModificacao()).isEqualTo(USUARIO_ATUALIZADO.getDataModificacao());
        assertThat(sut.isAtivo()).isEqualTo(USUARIO_ATUALIZADO.isAtivo());
//        assertThat(sut.getEnderecos()).isEqualTo(USUARIO_ATUALIZADO.getEnderecos());
//        assertThat(sut.getEnderecos().getPais()).isEqualTo(USUARIO_ATUALIZADO.getEnderecos().getPais());
    }

    @Test
    void atualizarUsuarioComDadosInvalidosThrowsRuntimeException() {
        Usuario usuarioAntigo = em.persistFlushFind(USUARIO);
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sutVazio = USUARIO_VAZIO;
        Usuario sutInvalido = USUARIO_INVALIDO;
        sutVazio.setId(usuarioAntigo.getId());
        sutInvalido.setId(usuarioAntigo.getId());
        assertThatThrownBy(() -> repository.saveAndFlush(sutVazio)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> repository.saveAndFlush(sutInvalido)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void consultarUsuarioPorIdExistenteEAtivoRetornaTrueEUsuario() {
        Usuario sut = em.persistFlushFind(USUARIO);
        em.persist(PAIS);
        em.persist(ENDERECO);
        Optional<Usuario> optionalUsuario = repository.findByIdAndAtivoTrue(sut.getId());
        assertThat(repository.existsById(sut.getId())).isTrue();
        assertThat(optionalUsuario).isNotEmpty().get().isEqualTo(sut);
    }

    @Test
    void consultarUsuarioPorIdExistenteEInativoRetornaTrueEUsuario() {
        USUARIO.setAtivo(false);
        Usuario usuario = em.persistFlushFind(USUARIO);
        em.persist(PAIS);
        em.persist(ENDERECO);
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
        Usuario usuario = em.persistFlushFind(USUARIO);
        em.persist(PAIS);
        em.persist(ENDERECO);
        Optional<Usuario> optionalUsuario = repository.findByCpfAndAtivoTrue(usuario.getCpf());
        assertThat(repository.existsByCpf(usuario.getCpf())).isTrue();
        assertThat(optionalUsuario).isNotEmpty();
    }

    @Test
    void consultarUsuarioPorCpfExistenteEInativoRetornaTrueEEmpty() {
        USUARIO.setAtivo(false);
        Usuario usuario = em.persistFlushFind(USUARIO);
        em.persist(PAIS);
        em.persist(ENDERECO);
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
        Usuario usuario = em.persistFlushFind(USUARIO);
        em.persist(PAIS);
        em.persist(ENDERECO);
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = repository.findAllByNomeAndAtivoTrue(usuario.getNome(), usuario.getSobrenome(),
                pageable);
        assertThat(pageUsuarios).isNotEmpty().hasSize(1);
        assertThat(pageUsuarios.getContent().get(0)).isEqualTo(usuario);
    }

    @Test
    void consultarUsuariosExistentesEAtivosPorParametroVazioRetornaUsuarios() {
        Usuario sut = em.persistFlushFind(USUARIO);
        ENDERECO.setUsuario(USUARIO);
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut2 = new Usuario(null, "nome2", "sobrenome2", "12345678901", null, LOCAL_DATE, LOCAL_DATE, true,
                LISTA_ENDERECOS);
        em.persist(sut2);
        em.persist(PAIS);
        em.persist(ENDERECO);
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = repository.findAllByNomeAndAtivoTrue("", "", pageable);
        assertThat(pageUsuarios).isNotEmpty().hasSize(2);
        assertThat(pageUsuarios.getContent().get(0)).isEqualTo(sut);
        assertThat(pageUsuarios.getContent().get(1)).isEqualTo(sut2);
    }

    @Test
    void consultarUsuarioPorNomeExistenteEInativoRetornaEmpty() {
        USUARIO.setAtivo(false);
        Usuario sut = em.persistFlushFind(USUARIO);
        em.persist(PAIS);
        em.persist(ENDERECO);
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = repository.findAllByNomeAndAtivoTrue(sut.getNome(), sut.getSobrenome(), pageable);
        assertThat(pageUsuarios).isEmpty();
    }

    @Test
    void consultarUsuarioPorNomeInexistenteRetornaEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = repository.findAllByNomeAndAtivoTrue("", "", pageable);
        assertThat(pageUsuarios).isEmpty();
    }

    @Test
    void desativarUsuarioPorIdExistenteRetornaUsuarioDesativado() {
        Usuario usuario = em.persistFlushFind(USUARIO);
        em.persist(PAIS);
        em.persist(ENDERECO);
        usuario.setAtivo(false);
        repository.save(usuario);
        Usuario usuarioDesativado = em.find(Usuario.class, usuario.getId());
        assertThat(usuarioDesativado).isNotNull();
        assertThat(usuarioDesativado.isAtivo()).isFalse();
    }

    @Test
    void reativarUsuarioPorIdExistenteRetornaUsuarioAtivado() {
        USUARIO.setAtivo(false);
        Usuario usuario = em.persistFlushFind(USUARIO);
        em.persist(PAIS);
        em.persist(ENDERECO);
        usuario.setAtivo(true);
        repository.save(usuario);
        Usuario usuarioDesativado = em.find(Usuario.class, usuario.getId());
        assertThat(usuarioDesativado).isNotNull();
        assertThat(usuarioDesativado.isAtivo()).isTrue();
    }

    @Test
    void removerUsuarioPorIdExistenteRetornaNulo() {
        Usuario sut = em.persistFlushFind(USUARIO);
        em.persist(PAIS);
        em.persist(ENDERECO);
        repository.deleteById(sut.getId());
        Usuario usuarioRemovido = em.find(Usuario.class, sut.getId());
        assertThat(usuarioRemovido).isNull();
    }

}

