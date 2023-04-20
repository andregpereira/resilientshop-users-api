package com.github.andregpereira.resilientshop.userapi.infra.repositories;

import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
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
        USUARIO_ATUALIZADO_PAIS_NOVO.setId(null);
        ENDERECO.setId(null);
        ENDERECO_ATUALIZADO.setId(null);
        ENDERECO_ATUALIZADO_PAIS_NOVO.setId(null);
        PAIS.setId(null);
        PAIS_NOVO.setId(null);
    }

    @Test
    void criarUsuarioComDadosValidosRetornaUsuario() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario usuario = repository.save(USUARIO);
        Usuario sut = em.find(Usuario.class, usuario.getId());
        assertThat(sut).isNotNull();
        assertThat(sut.getNome()).isEqualTo(USUARIO.getNome());
        assertThat(sut.getSobrenome()).isEqualTo(USUARIO.getSobrenome());
        assertThat(sut.getCpf()).isEqualTo(USUARIO.getCpf());
        assertThat(sut.getTelefone()).isEqualTo(USUARIO.getTelefone());
        assertThat(sut.getDataCriacao()).isEqualTo(USUARIO.getDataCriacao());
        assertThat(sut.getDataModificacao()).isEqualTo(USUARIO.getDataModificacao());
        assertThat(sut.isAtivo()).isEqualTo(USUARIO.isAtivo());
        assertThat(sut.getEndereco()).isEqualTo(USUARIO.getEndereco());
        assertThat(sut.getEndereco().getPais()).isEqualTo(USUARIO.getEndereco().getPais());
    }

    @Test
    void criarUsuarioComDadosInvalidosThrowsRuntimeException() {
        assertThatThrownBy(() -> repository.saveAndFlush(USUARIO_VAZIO)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> repository.save(USUARIO_INVALIDO)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void criarUsuarioComCpfExistenteThrowsRuntimeException() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO);
        sut.setId(null);
        assertThatThrownBy(() -> repository.saveAndFlush(sut)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void atualizarUsuarioComDadosValidosRetornaUsuario() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario usuarioAntigo = em.persistFlushFind(USUARIO);
        Usuario usuarioAtualizado = USUARIO_ATUALIZADO;
        usuarioAtualizado.setId(usuarioAntigo.getId());
        usuarioAtualizado.setCpf(USUARIO_ATUALIZADO.getCpf());
        usuarioAtualizado.setDataCriacao(USUARIO_ATUALIZADO.getDataCriacao());
        usuarioAtualizado.setDataModificacao(LOCAL_DATE);
        usuarioAtualizado.setAtivo(true);
        usuarioAtualizado.getEndereco().setId(USUARIO_ATUALIZADO.getEndereco().getId());
        usuarioAtualizado.getEndereco().setPais(USUARIO_ATUALIZADO.getEndereco().getPais());
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
        assertThat(sut.getEndereco()).isEqualTo(USUARIO_ATUALIZADO.getEndereco());
        assertThat(sut.getEndereco().getPais()).isEqualTo(USUARIO_ATUALIZADO.getEndereco().getPais());
    }

    @Test
    void atualizarUsuarioComDadosInvalidosThrowsRuntimeException() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario usuarioAntigo = em.persistFlushFind(USUARIO);
        Usuario sutVazio = USUARIO_VAZIO;
        Usuario sutInvalido = USUARIO_INVALIDO;
        sutVazio.setId(usuarioAntigo.getId());
        sutInvalido.setId(usuarioAntigo.getId());
        assertThatThrownBy(() -> repository.saveAndFlush(sutVazio)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> repository.saveAndFlush(sutInvalido)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void consultarUsuarioPorIdExistenteEAtivoRetornaTrueEUsuario() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario usuario = em.persistFlushFind(USUARIO);
        Optional<Usuario> optionalUsuario = repository.findByIdAndAtivoTrue(usuario.getId());
        assertThat(repository.existsById(usuario.getId())).isTrue();
        assertThat(optionalUsuario).isNotEmpty().get().isEqualTo(usuario);
    }

    @Test
    void consultarUsuarioPorIdExistenteEInativoRetornaTrueEUsuario() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario usuario = em.persistFlushFind(USUARIO_INATIVO);
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
        em.persist(ENDERECO);
        Usuario usuario = em.persistFlushFind(USUARIO);
        Optional<Usuario> optionalUsuario = repository.findByCpfAndAtivoTrue(usuario.getCpf());
        assertThat(repository.existsByCpf(usuario.getCpf())).isTrue();
        assertThat(optionalUsuario).isNotEmpty();
    }

    @Test
    void consultarUsuarioPorCpfExistenteEInativoRetornaTrueEEmpty() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario usuario = em.persistFlushFind(USUARIO_INATIVO);
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
        em.persist(ENDERECO);
        Usuario usuario = em.persistFlushFind(USUARIO);
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = repository.findByNomeAndAtivoTrue(usuario.getNome(), usuario.getSobrenome(),
                pageable);
        assertThat(pageUsuarios).isNotEmpty().hasSize(1);
        assertThat(pageUsuarios.getContent().get(0)).isEqualTo(usuario);
    }

    @Test
    void consultarUsuariosExistentesEAtivosPorParametroVazioRetornaUsuarios() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO);
        Usuario sut2 = new Usuario(null, "nome2", "sobrenome2", "12345678901", null, LOCAL_DATE, LOCAL_DATE, true,
                ENDERECO);
        em.persist(PAIS);
        em.persist(ENDERECO);
        em.persist(sut2);
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = repository.findByNomeAndAtivoTrue("", "", pageable);
        assertThat(pageUsuarios).isNotEmpty().hasSize(2);
        assertThat(pageUsuarios.getContent().get(0)).isEqualTo(sut);
        assertThat(pageUsuarios.getContent().get(1)).isEqualTo(sut2);
    }

    @Test
    void consultarUsuarioPorNomeExistenteEInativoRetornaEmpty() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO_INATIVO);
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = repository.findByNomeAndAtivoTrue(sut.getNome(), sut.getSobrenome(),
                pageable);
        assertThat(pageUsuarios).isEmpty();
    }

    @Test
    void consultarUsuarioPorNomeInexistenteRetornaEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = repository.findByNomeAndAtivoTrue("", "", pageable);
        assertThat(pageUsuarios).isEmpty();
    }

    @Test
    void desativarUsuarioPorIdExistenteRetornaUsuarioDesativado() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario usuario = em.persistFlushFind(USUARIO);
        usuario.setAtivo(false);
        repository.save(usuario);
        Usuario usuarioDesativado = em.find(Usuario.class, usuario.getId());
        assertThat(usuarioDesativado).isNotNull();
        assertThat(usuarioDesativado.isAtivo()).isFalse();
    }

    @Test
    void reativarUsuarioPorIdExistenteRetornaUsuarioAtivado() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario usuario = em.persistFlushFind(USUARIO_INATIVO);
        usuario.setAtivo(true);
        repository.save(usuario);
        Usuario usuarioDesativado = em.find(Usuario.class, usuario.getId());
        assertThat(usuarioDesativado).isNotNull();
        assertThat(usuarioDesativado.isAtivo()).isTrue();
    }

    @Test
    void removerUsuarioPorIdExistenteRetornaNulo() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO);
        repository.deleteById(sut.getId());
        Usuario usuarioRemovido = em.find(Usuario.class, sut.getId());
        assertThat(usuarioRemovido).isNull();
    }

}

