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
		if (usuarioRepository.findByCpfAndAtivoTrue(usuarioRegistroDto.cpf()).isPresent()) {
			throw new DataIntegrityViolationException("usuario_existente");
		}
		Usuario usuario = usuarioMapper.toUsuario(usuarioRegistroDto);
		Endereco endereco = usuario.getEndereco();
		Pais pais = endereco.getPais();
		Optional<Pais> paisNomeOptional = paisRepository.findByNome(pais.getNome());
		Optional<Pais> paisCodigoOptional = paisRepository.findByCodigo(pais.getCodigo());
		if (!paisNomeOptional.isPresent() && !paisCodigoOptional.isPresent()) {
			paisRepository.save(pais);
		} else {
			pais = paisNomeOptional.isPresent() ? paisNomeOptional.get() : paisCodigoOptional.get();
		}
		usuario.setDataCriacao(LocalDate.now());
		usuario.setDataModificacao(LocalDate.now());
		usuario.setAtivo(true);
		enderecoRepository.save(endereco);
		return usuarioMapper.toUsuarioDetalhesDto(usuarioRepository.save(usuario));
	}

	@Transactional
	public UsuarioDetalhesDto atualizar(Long id, UsuarioRegistroDto usuarioRegistroDto) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndAtivoTrue(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException("usuario_nao_encontrado_id");
		}
//		else if (!usuarioOptional.get().isAtivo()) {
//			throw new DataIntegrityViolationException("alterar_usuario_inativo");
//		}
		else if (!usuarioRegistroDto.cpf().equals(usuarioOptional.get().getCpf())) {
			throw new DataIntegrityViolationException("alterar_cpf");
		}
		Usuario usuarioAtualizado = usuarioMapper.toUsuario(usuarioRegistroDto);
		Endereco endereco = usuarioAtualizado.getEndereco();
		Pais pais = endereco.getPais();
		Usuario usuarioAntigo = usuarioOptional.get();
		usuarioAtualizado.setId(id);
		usuarioAtualizado.setDataCriacao(usuarioAntigo.getDataCriacao());
		usuarioAtualizado.setDataModificacao(LocalDate.now());
		usuarioAtualizado.setAtivo(true);
		usuarioAtualizado.setEndereco(endereco);
		Optional<Pais> paisNomeOptional = paisRepository.findByNome(pais.getNome());
		Optional<Pais> paisCodigoOptional = paisRepository.findByCodigo(pais.getCodigo());
		if (paisNomeOptional.isEmpty() && paisCodigoOptional.isEmpty()) {
			paisRepository.save(pais);
		} else {
			pais = paisNomeOptional.isPresent() ? paisNomeOptional.get() : paisCodigoOptional.get();
		}
		endereco.setId(usuarioAntigo.getEndereco().getId());
		endereco.setPais(pais);
		return usuarioMapper.toUsuarioDetalhesDto(usuarioRepository.save(usuarioAtualizado));
	}

	@Transactional
	public String remover(Long id) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndAtivoTrue(id);
		usuarioOptional.ifPresentOrElse(u -> {
			if (!u.isAtivo()) {
				throw new DataIntegrityViolationException("usuario_ja_inativo");
			}
			u.setAtivo(false);
			usuarioRepository.save(u);
		}, () -> {
			throw new EntityNotFoundException("usuario_nao_encontrado_id");
		});
		return "Usuário desativado.";
	}

	public UsuarioDetalhesDto reativar(Long id) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndAtivoFalse(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException("usuario_nao_encontrado_id");
		}
		Usuario usuario = usuarioOptional.get();
		usuario.setAtivo(true);
		return usuarioMapper.toUsuarioDetalhesDto(usuarioRepository.save(usuario));
	}

}
