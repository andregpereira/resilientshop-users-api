package com.github.andregpereira.resilientshop.userapi.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ConversionService conversionService;

	@Transactional
	public UsuarioDto registrar(UsuarioRegistroDto usuarioRegistroDto) {
		Usuario usuarioRegistrado = new Usuario();
		BeanUtils.copyProperties(usuarioRegistroDto, usuarioRegistrado);
		usuarioRegistrado.setDataCriacao(LocalDate.now());
		return conversionService.convert(usuarioRepository.save(usuarioRegistrado), UsuarioDto.class);
	}

	@Transactional
	public UsuarioDto atualizar(Long id, UsuarioRegistroDto usuarioRegistroDto) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException();
		} else if (!usuarioRegistroDto.cpf().equals(usuarioOptional.get().getCpf())) {
			throw new DataIntegrityViolationException("alterar_cpf");
		}
		Usuario usuarioAtualizado = usuarioOptional.get();
		BeanUtils.copyProperties(usuarioRegistroDto, usuarioAtualizado);
		usuarioAtualizado.setDataModificacao(LocalDate.now());
		return conversionService.convert(usuarioRepository.save(usuarioAtualizado), UsuarioDto.class);
	}

	@Transactional
	public String deletar(Long id) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException();
		}
		usuarioRepository.deleteById(id);
		return "Usuário deletado";
	}

	public UsuarioDto consultarPorId(Long id) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException();
		}
		return usuarioOptional.map(UsuarioDto::new).get();
	}

	public Page<UsuarioDto> consultarPorCpf(String cpf, Pageable pageable) {
		Page<Usuario> usuariosPage = usuarioRepository.findByCpf(cpf, pageable);
		if (usuariosPage.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return UsuarioDto.criarLista(usuariosPage);
	}

	public Page<UsuarioDto> consultarPorNome(String nome, String sobrenome, Pageable pageable) {
		if (nome != null || sobrenome != null) {
			nome = nome != null ? nome : "";
			sobrenome = sobrenome != null ? sobrenome : "";
			Page<Usuario> usuariosPage = usuarioRepository.findByName(nome, sobrenome, pageable);
			if (usuariosPage.isEmpty()) {
				throw new EmptyResultDataAccessException(1);
			}
			return UsuarioDto.criarLista(usuariosPage);
		}
		Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);
		if (usuariosPage.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return UsuarioDto.criarLista(usuariosPage);
	}

}
