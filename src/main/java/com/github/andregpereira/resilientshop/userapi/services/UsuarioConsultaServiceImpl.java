package com.github.andregpereira.resilientshop.userapi.services;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.infra.exception.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioConsultaServiceImpl implements UsuarioConsultaService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;
//	public Page<UsuarioDto> listar(Pageable pageable) {
//		return UsuarioDto.criarPage(usuarioRepository.findAll(pageable));
//	}

    public UsuarioDetalhesDto consultarPorId(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNotFoundException(
                    "Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
        }
        return usuarioMapper.toUsuarioDetalhesDto(usuarioRepository.findById(id).get());
    }

    public UsuarioDetalhesDto consultarPorCpf(String cpf) {
        if (cpf == null || cpf.isBlank() || cpf.length() < 11 || cpf.length() > 14) {
            throw new InvalidParameterException(
                    "Não foi possível realizar a busca por CPF. O CPF não foi digitado corretamente. Verifique e tente novamente");
        }
        Optional<Usuario> usuarioOptional = usuarioRepository.findByCpfAndAtivoTrue(cpf);
        if (usuarioRepository.findByCpfAndAtivoTrue(cpf).isEmpty()) {
            throw new UsuarioNotFoundException(
                    "Desculpe, não foi possível encontrar um usuário com este CPF. Verifique e tente novamente");
        }
        return usuarioMapper.toUsuarioDetalhesDto(usuarioOptional.get());
    }

    public Page<UsuarioDto> consultarPorNome(String nome, String sobrenome, Pageable pageable) {
        nome = nome != null ? (!nome.isBlank() ? nome.trim() : nome) : "";
        sobrenome = sobrenome != null ? (!sobrenome.isBlank() ? sobrenome.trim() : sobrenome) : "";
        Page<Usuario> usuarios = usuarioRepository.findByNomeAndAtivoTrue(nome, sobrenome, pageable);
        if (usuarios.isEmpty()) {
            throw new UsuarioNotFoundException(
                    "Desculpe, não foi possível encontrar um usuário com este nome. Verifique e tente novamente");
        } else if (nome.isBlank() && sobrenome.isBlank()) {
            return usuarioRepository.findByAtivoTrue(pageable).map(usuarioMapper::toUsuarioDto);
        }
        return usuarios.map(usuarioMapper::toUsuarioDto);
    }

}
