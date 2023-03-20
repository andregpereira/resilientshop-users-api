package com.github.andregpereira.resilientshop.userapi.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
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
public class UsuarioManutencaoService {

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
		usuarioRegistrado.setAtivo(true);
		return usuarioMapper.toUsuarioDetalhesDto(usuarioRepository.save(usuarioRegistrado));
	}

	@Transactional
	public UsuarioDetalhesDto atualizar(Long id, UsuarioRegistroDto usuarioRegistroDto) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException();
		} else if (!usuarioOptional.get().isAtivo()) {
			throw new DataIntegrityViolationException("usuario_inativo");
		} else if (!usuarioRegistroDto.cpf().equals(usuarioOptional.get().getCpf())) {
			throw new DataIntegrityViolationException("alterar_cpf");
		}
		Usuario usuarioAtualizado = salvar(usuarioRegistroDto, usuarioOptional.get());
		usuarioAtualizado.setId(id);
		usuarioAtualizado.setDataModificacao(LocalDate.now());
		return usuarioMapper.toUsuarioDetalhesDto(usuarioRepository.save(usuarioAtualizado));
	}

	public String desativar(Long id) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
		usuarioOptional.ifPresentOrElse(u -> {
			u.setAtivo(false);
			enderecoRepository.deleteById(u.getEndereco().getId());
			usuarioRepository.save(u);
		}, () -> {
			throw new EntityNotFoundException();
		});
		return "Usuário desativado.";
	}

	private Usuario salvar(UsuarioRegistroDto usuarioRegistroDto, Usuario usuarioAntigo) {
		Usuario usuario = usuarioMapper.toUsuario(usuarioRegistroDto);
		Endereco endereco = usuario.getEndereco();
		Pais pais = endereco.getPais();
		if (usuarioAntigo != null) {
			usuario.setDataCriacao(usuarioAntigo.getDataCriacao());
			endereco.setId(usuarioAntigo.getEndereco().getId());
		}
		Optional<Pais> paisNomeOptional = paisRepository.findByNome(pais.getNome());
		Optional<Pais> paisCodigoOptional = paisRepository.findByCodigo(pais.getCodigo());
		if (!paisNomeOptional.isPresent() && !paisCodigoOptional.isPresent()) {
			paisRepository.save(pais);
		} else {
			pais = paisNomeOptional.isPresent() ? paisNomeOptional.get() : paisCodigoOptional.get();
		}
		endereco.setPais(pais);
		usuario.setEndereco(enderecoRepository.save(endereco));
		return usuario;
	}

}
