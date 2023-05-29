package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioDto;
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

import static java.util.function.Predicate.not;

/**
 * Classe de serviço de consulta de {@link Usuario}.
 *
 * @author André Garcia
 * @see UsuarioConsultaService
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class UsuarioConsultaServiceImpl implements UsuarioConsultaService {

    /**
     * Injeção da dependência {@link UsuarioRepository} para realizar operações de
     * consulta na tabela de usuários no banco de dados.
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Injeção da dependência {@link UsuarioMapper} para realizar
     * conversões de entidade para DTO de usuário.
     */
    private final UsuarioMapper usuarioMapper;

    /**
     * Lista todos os {@linkplain Usuario usuários} cadastrados.
     * Retorna uma {@linkplain Page sublista} de {@linkplain UsuarioDto usuários}.
     *
     * @param pageable o pageable padrão.
     *
     * @return uma sublista de uma lista com todos os usuários cadastrados.
     *
     * @throws UsuarioNotFoundException caso nenhum usuário seja encontrado.
     */
    @Override
    public Page<UsuarioDto> listar(Pageable pageable) {
        return Optional.of(usuarioRepository.findAllByAtivoTrue(pageable)).filter(not(Page::isEmpty)).map(p -> {
            log.info("Retornando todos os usuários");
            return p.map(usuarioMapper::toUsuarioDto);
        }).orElseThrow(() -> {
            log.info("Nenhum usuário foi encontrado");
            return new UsuarioNotFoundException();
        });
    }

    /**
     * Pesquisa um {@linkplain Usuario usuário} por {@code id}.
     * Retorna um {@linkplain  UsuarioDetalhesDto usuário detalhado}.
     *
     * @param id o id do usuário.
     *
     * @return um usuário encontrado pelo {@code id}.
     *
     * @throws UsuarioNotFoundException caso o usuário não seja encontrado.
     */
    @Override
    public UsuarioDetalhesDto consultarPorId(Long id) {
        return usuarioRepository.findById(id).map(u -> {
            log.info("Retornando usuário encontrado com id {}", id);
            return usuarioMapper.toUsuarioDetalhesDto(u);
        }).orElseThrow(() -> {
            log.info("Usuário com id {} não encontrado", id);
            return new UsuarioNotFoundException(id);
        });
    }

    /**
     * Pesquisa um {@linkplain Usuario usuário} por {@code cpf}.
     * Retorna um {@linkplain  UsuarioDetalhesDto usuário detalhado}.
     *
     * @param cpf o CPF do usuário.
     *
     * @return um usuário encontrado pelo {@code cpf}.
     *
     * @throws UsuarioNotFoundException caso o usuário não seja encontrado.
     */
    @Override
    public UsuarioDetalhesDto consultarPorCpf(String cpf) {
        return usuarioRepository.findByCpfAndAtivoTrue(cpf).map(u -> {
            log.info("Retornando usuário encontrado com CPF");
            return usuarioMapper.toUsuarioDetalhesDto(u);
        }).orElseThrow(() -> {
            log.info("Usuário não encontrado com o CPF informado");
            return new UsuarioNotFoundException("CPF", cpf);
        });
    }

    /**
     * Pesquisa {@linkplain Usuario usuários} por {@code nome} e {@code sobrenome}.
     * Retorna uma {@linkplain Page sublista} de {@linkplain UsuarioDto usuários}.
     *
     * @param nome      o nome do usuário.
     * @param sobrenome (opcional) o sobrenome do usuário.
     * @param pageable  o pageable padrão.
     *
     * @return uma sublista de uma lista de usuários encontrados pelo {@code nome} e {@code sobrenome}.
     *
     * @throws UsuarioNotFoundException caso nenhum usuário seja encontrado.
     */
    @Override
    public Page<UsuarioDto> consultarPorNome(String nome, String sobrenome, Pageable pageable) {
        return Optional.of(usuarioRepository.findAllByNomeAndSobrenomeAndAtivoTrue(nome, sobrenome, pageable)).filter(
                not(Page::isEmpty)).map(p -> {
            log.info("Retornando usuários encontrados com o nome {}", String.join(" ", nome, sobrenome).trim());
            return p.map(usuarioMapper::toUsuarioDto);
        }).orElseThrow(() -> {
            log.info("Nenhum usuário foi encontrado com o nome {}", String.join(" ", nome, sobrenome));
            return new UsuarioNotFoundException("nome", String.join(" ", nome, sobrenome).trim());
        });
    }

}
