package com.github.andregpereira.resilientshop.userapi.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.entities.Endereco;
import com.github.andregpereira.resilientshop.userapi.entities.Pais;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.repositories.PaisRepository;
import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class UsuarioManutencaoService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioMapper usuarioMapper;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PaisRepository paisRepository;

	public UsuarioDetalhesDto registrar(UsuarioRegistroDto usuarioRegistroDto) {
		if (usuarioRepository.findByCpf(usuarioRegistroDto.cpf()).isPresent()) {
			throw new EntityExistsException("CPF já cadastrado no nosso banco de dados");
		}
		Usuario usuario = usuarioMapper.toUsuario(usuarioRegistroDto);
		Endereco endereco = usuario.getEndereco();
		Pais pais = endereco.getPais();
		usuario.setDataCriacao(LocalDate.now());
		usuario.setDataModificacao(LocalDate.now());
		usuario.setAtivo(true);
		Optional<Pais> optionalPais = paisRepository.findByNomeOrCodigo(pais.getNome(), pais.getCodigo());
		if (!optionalPais.isPresent()) {
			pais = paisRepository.save(pais);
		} else {
			pais = optionalPais.get();
		}
		endereco.setPais(pais);
		endereco = enderecoRepository.save(endereco);
		usuario.setEndereco(endereco);
		return usuarioMapper.toUsuarioDetalhesDto(usuarioRepository.save(usuario));
	}

	public UsuarioDetalhesDto atualizar(Long id, UsuarioAtualizacaoDto usuarioAtualizacaoDto) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndAtivoTrue(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException(
					"Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
		}
		Usuario usuarioAtualizado = usuarioMapper.toUsuario(usuarioAtualizacaoDto);
		Endereco endereco = usuarioAtualizado.getEndereco();
		Pais pais = endereco.getPais();
		Usuario usuarioAntigo = usuarioOptional.get();
		usuarioAtualizado.setId(id);
		usuarioAtualizado.setCpf(usuarioAntigo.getCpf());
		usuarioAtualizado.setDataCriacao(usuarioAntigo.getDataCriacao());
		usuarioAtualizado.setDataModificacao(LocalDate.now());
		usuarioAtualizado.setAtivo(true);
		Optional<Pais> optionalPais = paisRepository.findByNomeOrCodigo(pais.getNome(), pais.getCodigo());
		if (!optionalPais.isPresent()) {
			pais = paisRepository.save(pais);
		} else {
			pais = optionalPais.get();
		}
		endereco.setId(usuarioAntigo.getEndereco().getId());
		endereco.setPais(pais);
		endereco = enderecoRepository.save(endereco);
		usuarioAtualizado.setEndereco(endereco);
		return usuarioMapper.toUsuarioDetalhesDto(usuarioRepository.save(usuarioAtualizado));
	}

	public String remover(Long id) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndAtivoTrue(id);
		usuarioOptional.ifPresentOrElse(u -> {
			if (!u.isAtivo())
				throw new DataIntegrityViolationException("Este usuário já está com a conta desativada");
			u.setAtivo(false);
			usuarioRepository.save(u);
		}, () -> {
			throw new EntityNotFoundException(
					"Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
		});
		return "Usuário desativado";
	}

	public String reativar(Long id) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndAtivoFalse(id);
		usuarioOptional.ifPresentOrElse(u -> {
			if (u.isAtivo())
				throw new DataIntegrityViolationException("Este usuário já está com a conta ativada");
			u.setAtivo(true);
			usuarioRepository.save(u);
		}, () -> {
			throw new EntityNotFoundException(
					"Não foi possível encontrar um usuário inativo com este id. Verifique e tente novamente");
		});
		return "Usuário reativado";
	}

}
