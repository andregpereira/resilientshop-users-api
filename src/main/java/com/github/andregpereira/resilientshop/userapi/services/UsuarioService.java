package com.github.andregpereira.resilientshop.userapi.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andregpereira.resilientshop.userapi.dtos.*;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public UsuarioDto registrar(UsuarioRegistroDto usuarioRegistroDto) {
		Usuario usuarioRegistrado = new Usuario();
		BeanUtils.copyProperties(usuarioRegistroDto, usuarioRegistrado);
		usuarioRegistrado.setDataCriacao(LocalDate.now());
		return new UsuarioDto(usuarioRepository.save(usuarioRegistrado));
	}

	public Optional<UsuarioDto> consultarPorId(Long id) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException("Usuário não encontrado");
		}
		return usuarioOptional.map(UsuarioDto::new);
	}

	public Optional<UsuarioDto> consultarPorCpf(UsuarioDto usuarioDto) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByCpf(usuarioDto.cpf());
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException("Usuário não encontrado com o CPF informado");
		}
		return usuarioOptional.map(UsuarioDto::new);
	}

	public Optional<UsuarioDto> consultarPorNome(UsuarioDto usuarioDto) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByName(usuarioDto.nome(), usuarioDto.sobrenome());
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException("Usuário não encontrado com o nome informado");
		}
		return usuarioOptional.map(UsuarioDto::new);
	}

}
