package com.github.andregpereira.resilientshop.userapi.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andregpereira.resilientshop.userapi.dtos.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.dtos.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;

import jakarta.validation.Valid;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public Usuario registrar(@Valid UsuarioRegistroDto dto) {
		Usuario usuario = new Usuario();
		BeanUtils.copyProperties(dto, usuario);
		usuario.setDataCriacao(LocalDate.now());
		return usuarioRepository.save(usuario);
	}

	public Optional<UsuarioDto> consultarPorId(Long id) {
		return usuarioRepository.findById(id).map(UsuarioDto::new);
	}

}
