package com.github.andregpereira.resilientshop.userapi.repositories;

import com.github.andregpereira.resilientshop.userapi.config.PostgreSQLContainerConfig;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.AtivoProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS_NOVO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("it")
@ContextConfiguration(initializers = PostgreSQLContainerConfig.PostgreSQLContainerInitializer.class)
public class UsuarioRepositoryIT extends PostgreSQLContainerConfig {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager em;

    @AfterEach
    public void afterEach() {
        USUARIO.setId(null);
        USUARIO_INATIVO.setId(null);
        USUARIO_ATUALIZADO.setId(null);
        USUARIO_ATUALIZADO_PAIS_NOVO.setId(null);
        ENDERECO.setId(null);
        ENDERECO_ATUALIZADO.setId(null);
        ENDERECO_ATUALIZADO_PAIS_NOVO.setId(null);
        PAIS.setId(null);
        PAIS_NOVO.setId(null);
    }

    @Test
    public void criarUsuarioComDadosValidosRetornaUsuario() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario usuario = usuarioRepository.save(USUARIO);
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
    public void criarUsuarioComDadosInvalidosThrowsRuntimeException() {
        assertThatThrownBy(() -> usuarioRepository.saveAndFlush(USUARIO_VAZIO)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> usuarioRepository.save(USUARIO_INVALIDO)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void criarUsuarioComCpfExistenteThrowsRuntimeException() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO);
        sut.setId(null);
        assertThatThrownBy(() -> usuarioRepository.saveAndFlush(sut)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void atualizarUsuarioComDadosValidosRetornaUsuario() {
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
        Usuario sut = usuarioRepository.save(usuarioAtualizado);
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
    public void atualizarUsuarioComDadosInvalidosThrowsRuntimeException() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario usuarioAntigo = em.persistFlushFind(USUARIO);
        Usuario sutVazio = USUARIO_VAZIO;
        Usuario sutInvalido = USUARIO_INVALIDO;
        sutVazio.setId(usuarioAntigo.getId());
        sutInvalido.setId(usuarioAntigo.getId());
        assertThatThrownBy(() -> usuarioRepository.saveAndFlush(sutVazio)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> usuarioRepository.saveAndFlush(sutInvalido)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void consultarUsuarioPorIdExistenteEAtivoRetornaTrueEUsuario() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO);
        Optional<Usuario> optionalUsuario = usuarioRepository.findByIdAndAtivoTrue(sut.getId());
        assertThat(usuarioRepository.existsById(sut.getId())).isTrue();
        assertThat(optionalUsuario).isNotEmpty();
        assertThat(optionalUsuario.get()).isEqualTo(sut);
    }

    @Test
    public void consultarUsuarioPorIdExistenteEInativoRetornaTrueEUsuario() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO_INATIVO);
        Optional<Usuario> optionalUsuario = usuarioRepository.findByIdAndAtivoFalse(sut.getId());
        assertThat(usuarioRepository.existsById(sut.getId())).isTrue();
        assertThat(optionalUsuario).isNotEmpty();
        assertThat(optionalUsuario.get()).isEqualTo(sut);
    }

    @Test
    public void consultarUsuarioPorIdInexistenteRetornaFalseEEmpty() {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(1L);
        Optional<Usuario> optionalUsuarioAtivoTrue = usuarioRepository.findByIdAndAtivoTrue(10L);
        Optional<Usuario> optionalUsuarioAtivoFalse = usuarioRepository.findByIdAndAtivoFalse(100L);
        assertThat(usuarioRepository.existsById(1L)).isFalse();
        assertThat(usuarioRepository.existsById(10L)).isFalse();
        assertThat(usuarioRepository.existsById(100L)).isFalse();
        assertThat(optionalUsuario).isEmpty();
        assertThat(optionalUsuarioAtivoTrue).isEmpty();
        assertThat(optionalUsuarioAtivoFalse).isEmpty();
    }

    @Test
    public void consultarUsuarioPorCpfExistenteEAtivoRetornaTrueEUsuario() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO);
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCpfAndAtivoTrue(sut.getCpf());
        assertThat(usuarioRepository.existsByCpf(USUARIO.getCpf())).isTrue();
        assertThat(optionalUsuario).isNotEmpty();
    }

    @Test
    public void consultarUsuarioPorCpfExistenteEInativoRetornaTrueEEmpty() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO_INATIVO);
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCpfAndAtivoTrue(sut.getCpf());
        assertThat(usuarioRepository.existsByCpf(USUARIO_INATIVO.getCpf())).isTrue();
        assertThat(optionalUsuario).isEmpty();
    }

    @Test
    public void consultarUsuarioPorCpfInexistenteRetornaFalseEEmpty() {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCpfAndAtivoTrue("99999999999");
        assertThat(usuarioRepository.existsByCpf("99999999999")).isFalse();
        assertThat(optionalUsuario).isEmpty();
    }

    @Test
    public void consultarUsuarioPorNomeExistenteEAtivoRetornaUsuario() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO);
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = usuarioRepository.findByNomeAndAtivoTrue(sut.getNome(), sut.getSobrenome(),
                pageable);
        assertThat(pageUsuarios).isNotEmpty().hasSize(1);
        assertThat(pageUsuarios.getContent().get(0)).isEqualTo(sut);
    }

    @Test
    public void consultarUsuariosExistentesEAtivosPorParametroVazioRetornaUsuarios() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO);
        Usuario sut2 = new Usuario(null, "nome2", "sobrenome2", "12345678901", null, LOCAL_DATE, LOCAL_DATE, true,
                ENDERECO);
        em.persist(PAIS);
        em.persist(ENDERECO);
        em.persist(sut2);
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = usuarioRepository.findByNomeAndAtivoTrue("", "", pageable);
        assertThat(pageUsuarios).isNotEmpty().hasSize(2);
        assertThat(pageUsuarios.getContent().get(0)).isEqualTo(sut);
        assertThat(pageUsuarios.getContent().get(1)).isEqualTo(sut2);
    }

    @Test
    public void consultarUsuarioPorNomeExistenteEInativoRetornaEmpty() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO_INATIVO);
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = usuarioRepository.findByNomeAndAtivoTrue(sut.getNome(), sut.getSobrenome(),
                pageable);
        assertThat(pageUsuarios).isEmpty();
    }

    @Test
    public void consultarUsuarioPorNomeInexistenteRetornaEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        Page<Usuario> pageUsuarios = usuarioRepository.findByNomeAndAtivoTrue("", "", pageable);
        assertThat(pageUsuarios).isEmpty();
    }

    @Test
    public void desativarUsuarioPorIdExistenteRetornaUsuarioDesativado() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO);
        usuarioRepository.deactivateById(sut.getId());
        Usuario usuarioDesativado = em.find(Usuario.class, sut.getId());
        assertThat(usuarioDesativado).isNotNull();
        assertThat(usuarioDesativado.isAtivo()).isFalse();
    }

    @Test
    public void reativarUsuarioPorIdExistenteRetornaUsuarioAtivado() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO_INATIVO);
        usuarioRepository.activateById(sut.getId());
        Usuario usuarioDesativado = em.find(Usuario.class, sut.getId());
        assertThat(usuarioDesativado).isNotNull();
        assertThat(usuarioDesativado.isAtivo()).isTrue();
    }

    @Test
    public void removerUsuarioPorIdExistenteRetornaNulo() {
        em.persist(PAIS);
        em.persist(ENDERECO);
        Usuario sut = em.persistFlushFind(USUARIO);
        usuarioRepository.deleteById(sut.getId());
        Usuario usuarioRemovido = em.find(Usuario.class, sut.getId());
        assertThat(usuarioRemovido).isNull();
    }

}

