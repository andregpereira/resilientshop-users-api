package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.UsuarioRepository;
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
        return usuarioRepository.findById(id).map(u -> {
            log.info("Retornando usuário encontrado com id {}", id);
            return usuarioMapper.toUsuarioDetalhesDto(u);
        }).orElseThrow(() -> {
            log.info("Usuário com id {} não encontrado", id);
            return new UsuarioNotFoundException(id);
        });
    }

    public UsuarioDetalhesDto consultarPorCpf(String cpf) {
        if (cpf == null || cpf.isBlank() || !cpf.matches("\\d+") || cpf.length() < 11 || cpf.length() > 14) {
            log.info("CPF inválido");
            throw new InvalidParameterException(
                    "Não foi possível realizar a busca por CPF. O CPF não foi digitado corretamente. Verifique e tente novamente");
        }
        return usuarioRepository.findByCpfAndAtivoTrue(cpf).map(u -> {
            log.info("Retornando usuário encontrado com CPF");
            return usuarioMapper.toUsuarioDetalhesDto(u);
        }).orElseThrow(() -> {
            log.info("Usuário não encontrado com o CPF informado");
            return new UsuarioNotFoundException(
                    "Desculpe, não foi possível encontrar um usuário ativo com este CPF. Verifique e tente novamente");
        });
    }

    public Page<UsuarioDto> consultarPorNome(String nome, String sobrenome, Pageable pageable) {
        nome = Optional.ofNullable(nome).map(String::trim).orElse("");
        sobrenome = Optional.ofNullable(sobrenome).map(String::trim).orElse("");
        Page<Usuario> usuarios = usuarioRepository.findAllByNomeAndAtivoTrue(nome, sobrenome, pageable);
        if (usuarios.isEmpty()) {
            log.info("Nenhum usuário foi encontrado");
            throw new UsuarioNotFoundException(
                    "Desculpe, não foi possível encontrar um usuário com este nome. Verifique e tente novamente");
        } else if (nome.isBlank() && sobrenome.isBlank()) {
            log.info("Retornando todos os usuários");
            return usuarioRepository.findAllByAtivoTrue(pageable).map(usuarioMapper::toUsuarioDto);
        }
        log.info("Retornando usuários encontrados pelo nome");
        return usuarios.map(usuarioMapper::toUsuarioDto);
    }

}
