package com.github.andregpereira.resilientshop.userapi.services;

import java.security.InvalidParameterException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

//	public Page<UsuarioDto> listar(Pageable pageable) {
//		return UsuarioDto.criarPage(usuarioRepository.findAll(pageable));
//	}

	public UsuarioDetalhesDto consultarPorId(Long id) {
		if (!usuarioRepository.existsById(id)) {
			throw new EntityNotFoundException(
					"Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
		}
		return usuarioMapper.toUsuarioDetalhesDto(usuarioRepository.getReferenceById(id));
	}

	public UsuarioDetalhesDto consultarPorCpf(String cpf) {
		if (cpf.isBlank() || (cpf.length() < 11 && cpf.length() > 14)) {
			throw new InvalidParameterException(
					"Não foi possível realizar a busca por CPF. O CPF não foi digitado corretamente. Verifique e tente novamente");
		}
		Optional<Usuario> usuarioOptional = usuarioRepository.findByCpfAndAtivoTrue(cpf);
		if (!usuarioRepository.findByCpfAndAtivoTrue(cpf).isPresent()) {
			throw new EntityNotFoundException(
					"Desculpe, não foi possível encontrar um usuário com este CPF. Verifique e tente novamente");
		}
		return usuarioMapper.toUsuarioDetalhesDto(usuarioOptional.get());
	}

	public Page<UsuarioDto> consultarPorNome(String nome, String sobrenome, Pageable pageable) {
		nome = nome != null ? (!nome.isBlank() ? nome.trim() : nome) : "";
		sobrenome = sobrenome != null ? (!sobrenome.isBlank() ? sobrenome.trim() : sobrenome) : "";
		Page<Usuario> usuarios = usuarioRepository.findByNome(nome, sobrenome, pageable);
		if (usuarios.isEmpty()) {
			throw new EntityNotFoundException(
					"Desculpe, não foi possível encontrar um usuário com este nome. Verifique e tente novamente");
		} else if (nome.isBlank() && sobrenome.isBlank()) {
			return UsuarioDto.criarPage(usuarioRepository.findByAtivoTrue(pageable));
		}
		return UsuarioDto.criarPage(usuarios);
	}

}
