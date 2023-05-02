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

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UsuarioConsultaServiceImpl implements UsuarioConsultaService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public Page<UsuarioDto> listar(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAllByAtivoTrue(pageable);
        if (usuarios.isEmpty()) {
            log.info("Nenhum usuário foi encontrado");
            throw new UsuarioNotFoundException();
        } else {
            log.info("Retornando todos os usuários");
            return usuarios.map(usuarioMapper::toUsuarioDto);
        }
    }

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
        return usuarioRepository.findByCpfAndAtivoTrue(cpf).map(u -> {
            log.info("Retornando usuário encontrado com CPF");
            return usuarioMapper.toUsuarioDetalhesDto(u);
        }).orElseThrow(() -> {
            log.info("Usuário não encontrado com o CPF informado");
            return new UsuarioNotFoundException("CPF", cpf);
        });
    }

    public Page<UsuarioDto> consultarPorNome(String nome, String sobrenome, Pageable pageable) {
        nome = nome.trim();
        sobrenome = Optional.ofNullable(sobrenome).map(String::trim).orElse("");
        Page<Usuario> usuarios = usuarioRepository.findAllByNomeAndSobrenomeAndAtivoTrue(nome, sobrenome, pageable);
        if (usuarios.isEmpty()) {
            log.info("Nenhum usuário foi encontrado pelo nome {}", String.join(" ", nome, sobrenome).trim());
            throw new UsuarioNotFoundException("nome", String.join(" ", nome, sobrenome).trim());
        }
        log.info("Retornando usuários encontrados com o nome {}", String.join(" ", nome, sobrenome).trim());
        return usuarios.map(usuarioMapper::toUsuarioDto);
    }

}
