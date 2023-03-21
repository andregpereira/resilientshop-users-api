package com.github.andregpereira.resilientshop.userapi.services;

import java.security.InvalidParameterException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioConsultaService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioMapper usuarioMapper;

	public UsuarioDetalhesDto consultarPorId(Long id) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndAtivoTrue(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException("usuario_nao_encontrado_id");
		}
		return usuarioMapper.toUsuarioDetalhesDto(usuarioOptional.get());
	}

	public UsuarioDetalhesDto consultarPorCpf(String cpf) {
		if (cpf.isBlank()) {
			throw new InvalidParameterException("usuario_consulta_cpf_em_branco");
		} else if (cpf.length() < 11 || cpf.length() > 14) {
			throw new InvalidParameterException("usuario_consulta_cpf_invalido");
		}
		Optional<Usuario> usuarioOptional = usuarioRepository.findByCpfAndAtivoTrue(cpf);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException("usuario_nao_encontrado_cpf");
		}
		return usuarioMapper.toUsuarioDetalhesDto(usuarioOptional.get());
	}

	public Page<UsuarioDto> consultarPorNome(String nome, String sobrenome, Pageable pageable) {
		nome = nome != null ? nome : "";
		sobrenome = sobrenome != null ? sobrenome : "";
		if (nome.isBlank() && sobrenome.isBlank()) {
			return UsuarioDto.criarPage(usuarioRepository.findByAtivoTrue(pageable));
		}
		Page<Usuario> usuarios = usuarioRepository.findByAtivoTrue(pageable);
		if (usuarios.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return UsuarioDto.criarPage(usuarios);
	}

}
