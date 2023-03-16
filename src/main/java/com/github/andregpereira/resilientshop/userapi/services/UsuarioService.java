package com.github.andregpereira.resilientshop.userapi.services;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.entities.Endereco;
import com.github.andregpereira.resilientshop.userapi.entities.Pais;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.repositories.PaisRepository;
import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioMapper usuarioMapper;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PaisRepository paisRepository;

	@Transactional
	public UsuarioDetalhesDto registrar(UsuarioRegistroDto usuarioRegistroDto) {
		if (usuarioRepository.findByCpf(usuarioRegistroDto.cpf()).isPresent()) {
			throw new DataIntegrityViolationException("usuario_existente");
		}
		Usuario usuarioRegistrado = salvar(usuarioRegistroDto, null);
		usuarioRegistrado.setDataCriacao(LocalDate.now());
		usuarioRegistrado.setDataModificacao(LocalDate.now());
		return usuarioMapper.toUsuarioDetalhesDto(usuarioRepository.save(usuarioRegistrado));
	}

	@Transactional
	public UsuarioDetalhesDto atualizar(Long id, UsuarioRegistroDto usuarioRegistroDto) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException("usuario_nao_encontrado");
		} else if (!usuarioRegistroDto.cpf().equals(usuarioOptional.get().getCpf())) {
			throw new DataIntegrityViolationException("alterar_cpf");
		}
		Usuario usuarioAtualizado = salvar(usuarioRegistroDto, usuarioOptional.get());
		usuarioAtualizado.setId(id);
		usuarioAtualizado.setDataModificacao(LocalDate.now());
		return usuarioMapper.toUsuarioDetalhesDto(usuarioRepository.save(usuarioAtualizado));
	}

	@Transactional
	public String deletar(Long id) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException("usuario_nao_encontrado");
		}
		usuarioRepository.deleteById(id);
		return "Usuário deletado.";
	}

	private Usuario salvar(UsuarioRegistroDto usuarioRegistroDto, Usuario usuarioAtualizado) {
		Usuario usuario = usuarioMapper.toUsuario(usuarioRegistroDto);
		Endereco endereco = usuario.getEndereco();
		Pais pais = endereco.getPais();
		if (usuarioAtualizado != null) {
			usuario.setDataCriacao(usuarioAtualizado.getDataCriacao());
			endereco.setId(usuarioAtualizado.getEndereco().getId());
		}
		Optional<Pais> paisNomeOptional = paisRepository.findByNome(pais.getNome());
		Optional<Pais> paisCodigoOptional = paisRepository.findByCodigo(pais.getCodigo());
		if (!paisNomeOptional.isPresent() && !paisCodigoOptional.isPresent()) {
			paisRepository.save(pais);
		} else {
			pais = paisNomeOptional.isPresent() ? paisNomeOptional.get() : paisCodigoOptional.get();
		}
		endereco.setPais(pais);
//		List<Endereco> paisEnderecos = Stream
//				.of(paisRepository.findByNomeOuCodigo(pais.getNome(), pais.getCodigo()).get().getEnderecos())
//				.flatMap(Collection::stream).collect(Collectors.toList());
//		pais.setEnderecos(paisEnderecos);
		usuario.setEndereco(enderecoRepository.save(endereco));
		return usuario;
	}

	public UsuarioDto consultarPorId(Long id) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException("usuario");
		}
		return usuarioOptional.map(UsuarioDto::new).get();
	}

	public UsuarioDto consultarPorCpf(String cpf) {
		if (cpf == null || cpf.length() < 11 || cpf.length() > 14) {
			throw new InvalidParameterException("usuario_consulta_cpf_invalido");
		}
		Optional<Usuario> usuarioOptional = usuarioRepository.findByCpf(cpf);
		if (usuarioOptional.isEmpty()) {
			throw new EntityNotFoundException("usuario_nao_encontrado_cpf");
		}
		return usuarioOptional.map(UsuarioDto::new).get();
	}

	public Page<UsuarioDto> consultarPorNome(String nome, String sobrenome, Pageable pageable) {
		sobrenome = sobrenome != null ? sobrenome : "";
		System.out.println(nome);
		if (nome == null) {
			throw new InvalidParameterException("usuario_consulta_nome_invalido");
		} else if (nome.length() < 2) {
			throw new InvalidParameterException("usuario_consulta_nome_tamanho_invalido");
		}
		Page<Usuario> usuariosPage = usuarioRepository.findByNome(nome, sobrenome, pageable);
		if (usuariosPage.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return UsuarioDto.criarLista(usuariosPage);
	}

}
