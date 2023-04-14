package com.github.andregpereira.resilientshop.userapi.services;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.infra.exception.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UsuarioConsultaServiceImpl implements UsuarioConsultaService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioDetalhesDto consultarPorId(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            log.info("Usuário com id {} não encontrado", id);
            throw new UsuarioNotFoundException(
                    "Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
        }
        log.info("Retornando usuário encontrado com id {}", id);
        return usuarioMapper.toUsuarioDetalhesDto(optionalUsuario.get());
    }

    public UsuarioDetalhesDto consultarPorCpf(String cpf) {
        if (cpf == null || cpf.isBlank() || !cpf.matches("\\d+") || cpf.length() < 11 || cpf.length() > 14) {
            log.info("CPF inválido");
            throw new InvalidParameterException(
                    "Não foi possível realizar a busca por CPF. O CPF não foi digitado corretamente. Verifique e tente novamente");
        }
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCpfAndAtivoTrue(cpf);
        if (optionalUsuario.isEmpty()) {
            log.info("Usuário não encontrado com o CPF informado");
            throw new UsuarioNotFoundException(
                    "Desculpe, não foi possível encontrar um usuário ativo com este CPF. Verifique e tente novamente");
        }
        log.info("Retornando usuário encontrado pelo CPF");
        return usuarioMapper.toUsuarioDetalhesDto(optionalUsuario.get());
    }

    public Page<UsuarioDto> consultarPorNome(String nome, String sobrenome, Pageable pageable) {
        if (nome != null)
            nome = !nome.isBlank() ? nome.trim() : nome;
        else
            nome = "";
        if (sobrenome != null)
            sobrenome = !sobrenome.isBlank() ? sobrenome.trim() : sobrenome;
        else
            sobrenome = "";
        Page<Usuario> usuarios = usuarioRepository.findByNomeAndAtivoTrue(nome, sobrenome, pageable);
        if (usuarios.isEmpty()) {
            log.info("Nenhum usuário foi encontrado");
            throw new UsuarioNotFoundException(
                    "Desculpe, não foi possível encontrar um usuário com este nome. Verifique e tente novamente");
        } else if (nome.isBlank() && sobrenome.isBlank()) {
            log.info("Retornando todos os usuários");
            return usuarioRepository.findByAtivoTrue(pageable).map(usuarioMapper::toUsuarioDto);
        }
        log.info("Retornando usuários encontrados pelo nome");
        return usuarios.map(usuarioMapper::toUsuarioDto);
    }

}
