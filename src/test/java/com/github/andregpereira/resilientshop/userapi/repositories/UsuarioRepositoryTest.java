package com.github.andregpereira.resilientshop.userapi.repositories;

import static com.github.andregpereira.resilientshop.userapi.UsuarioConstants.ENDERECO;
import static com.github.andregpereira.resilientshop.userapi.UsuarioConstants.PAIS;
import static com.github.andregpereira.resilientshop.userapi.UsuarioConstants.USUARIO;
import static com.github.andregpereira.resilientshop.userapi.UsuarioConstants.USUARIO_INVALIDO;
import static com.github.andregpereira.resilientshop.userapi.UsuarioConstants.USUARIO_VAZIO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;

import com.github.andregpereira.resilientshop.userapi.entities.Usuario;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private TestEntityManager em;

	@BeforeEach
	public void beforeEach() {
		em.persist(PAIS);
		em.persist(ENDERECO);
	}

	@AfterEach
	public void afterEach() {
		USUARIO.setId(null);
		ENDERECO.setId(null);
		PAIS.setId(null);
	}

	@Test
	public void criarUsuarioComDadosValidos() {
		Usuario usuario = USUARIO;
		repository.save(USUARIO);
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
		assertThatThrownBy(() -> repository.save(USUARIO_VAZIO)).isInstanceOf(RuntimeException.class);
		assertThatThrownBy(() -> repository.save(USUARIO_INVALIDO)).isInstanceOf(RuntimeException.class);
	}

	@Test
	public void criarUsuarioComCpfExistenteThrowsRuntimeException() {
		Usuario usuario = em.persist(USUARIO);
		em.detach(usuario);
		usuario.setId(null);
		assertThatThrownBy(() -> repository.save(usuario)).isInstanceOf(RuntimeException.class);
	}

	@Test
	public void consultarUsuarioComIdExistenteRetornaUsuario() {
		Usuario usuario = em.persistFlushFind(USUARIO);
		Optional<Usuario> optionalUsuario = repository.findById(usuario.getId());
		assertThat(optionalUsuario).isNotEmpty();
		assertThat(optionalUsuario.get()).isEqualTo(usuario);
	}

	@Test
	public void consultarUsuarioComIdInexistenteRetornaEmpty() {
		Optional<Usuario> optionalUsuario = repository.findById(1L);
		assertThat(optionalUsuario).isEmpty();
	}

	@Test
	public void consultarUsuarioPorNomeExistenteRetornaUsuario() {
		Usuario usuario = em.persistFlushFind(USUARIO);
		Page<Usuario> usuarios = repository.findByNome(usuario.getNome(), usuario.getSobrenome(), null);
		assertThat(usuarios).isNotEmpty();
		assertThat(usuarios.get().findFirst().get()).isEqualTo(usuario);
	}

	@Test
	public void removerUsuarioComIdExistente() {
		Usuario usuario = em.persistFlushFind(USUARIO);
		repository.deleteById(usuario.getId());
		Usuario usuarioRemovido = em.find(Usuario.class, usuario.getId());
		assertThat(usuarioRemovido).isNull();
	}

	@Test
	public void removerUsuarioComIdInexistenteThrowsException() {
		assertThatThrownBy(() -> repository.deleteById(1L)).isInstanceOf(EmptyResultDataAccessException.class);
	}

}
