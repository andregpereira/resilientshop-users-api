package com.github.andregpereira.resilientshop.userapi.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andregpereira.resilientshop.userapi.dtos.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.dtos.UsuarioRegistroDto;
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

	@Transactional
	public UsuarioDto atualizar(Long id, UsuarioRegistroDto usuarioRegistroDto) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException();
		}
		Usuario usuarioAtualizado = usuarioOptional.get();
		BeanUtils.copyProperties(usuarioRegistroDto, usuarioAtualizado);
		usuarioAtualizado.setDataModificacao(LocalDate.now());
		return new UsuarioDto(usuarioRepository.save(usuarioAtualizado));
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

//	public List<UsuarioDto> listar() {
//		List<Usuario> usuarios = usuarioRepository.findAll();
//		if (usuarios.isEmpty()) {
//			throw new EmptyResultDataAccessException(0);
//		}
//		return UsuarioDto.criarLista(usuarios);
//	}

	public UsuarioDto consultarPorId(Long id) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
		if (!usuarioOptional.isPresent()) {
			throw new EntityNotFoundException();
		}
		return usuarioOptional.map(UsuarioDto::new).get();
	}

	public Page<UsuarioDto> consultarPorCpf(String cpf, Pageable pageable) {
		Page<Usuario> usuariosPage = usuarioRepository.findByCpf(cpf, pageable);
		if (!usuariosPage.isEmpty()) {
			return UsuarioDto.criarLista(usuariosPage);
		}
		throw new EmptyResultDataAccessException(1);
	}

	public Page<UsuarioDto> consultarPorNome(String nome, String sobrenome, Pageable pageable) {
		if (nome != null || sobrenome != null) {
			nome = nome != null ? nome : "";
			sobrenome = sobrenome != null ? sobrenome : "";
			Page<Usuario> usuariosPage = usuarioRepository.findByName(nome, sobrenome, pageable);
			if (!usuariosPage.isEmpty()) {
				return UsuarioDto.criarLista(usuariosPage);
			}
			throw new EmptyResultDataAccessException(1);
		}
		Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);
		if (!usuariosPage.isEmpty()) {
			return UsuarioDto.criarLista(usuariosPage);
		}
		throw new EmptyResultDataAccessException(1);
	}

}
