package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioAlreadyExistsException;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.cross.validations.PaisValidation;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDate;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class UsuarioManutencaoServiceImpl implements UsuarioManutencaoService {

    /**
     * Injeção da dependência {@link UsuarioRepository} para realizar operações de
     * manutenção na tabela de usuários no banco de dados.
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Injeção da dependência {@link UsuarioMapper} para realizar
     * conversões de DTO e entidade de usuário.
     */
    private final UsuarioMapper usuarioMapper;

    /**
     * Injeção da dependência {@link EnderecoRepository} para realizar operações de
     * manutenção na tabela de endereços no banco de dados.
     */
    private final EnderecoRepository enderecoRepository;

    /**
     * Injeção da dependência {@link PaisValidation} para validar o país.
     */
    private final PaisValidation paisValidation;

    /**
     * @param dto o usuário a ser cadastrado.
     *
     * @return o usuário salvo no banco de dados com {@code id}.
     */
    public UsuarioDetalhesDto registrar(UsuarioRegistroDto dto) {
        Usuario usuario = usuarioMapper.toUsuario(dto);
        usuario.setCpf(usuario.getCpf().replace(".", "").replace("-", ""));
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            log.info("Usuário já cadastrado com o CPF informado");
            throw new UsuarioAlreadyExistsException();
        }
        usuario.getEnderecos().stream().forEach(e -> {
            e.setPais(paisValidation.validarPais(e.getPais()));
            e.setUsuario(usuario);
        });
        LocalDate agora = LocalDate.now();
        usuario.setDataCriacao(agora);
        usuario.setDataModificacao(agora);
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
        return usuarioMapper.toUsuarioDetalhesDto(usuario);
    }

    /**
     * @param id  o id do usuário a ser atualizado.
     * @param dto o usuário a ser atualizado.
     *
     * @return
     *
     * @throws UsuarioNotFoundException caso o usuário não seja encontrado.
     */
    public UsuarioDetalhesDto atualizar(Long id, UsuarioAtualizacaoDto dto) {
        return usuarioRepository.findByIdAndAtivoTrue(id).map(usuarioAntigo -> {
            Usuario usuarioAtualizado = usuarioMapper.toUsuario(dto);
            enderecoRepository.deleteByUsuarioId(usuarioAntigo.getId());
            usuarioAtualizado.getEnderecos().stream().forEach(e -> {
                e.setPais(paisValidation.validarPais(e.getPais()));
                e.setUsuario(usuarioAtualizado);
            });
            usuarioAtualizado.setId(id);
            usuarioAtualizado.setCpf(usuarioAntigo.getCpf());
            usuarioAtualizado.setDataCriacao(usuarioAntigo.getDataCriacao());
            usuarioAtualizado.setDataModificacao(LocalDate.now());
            usuarioAtualizado.setAtivo(true);
            usuarioAtualizado.setEnderecos(usuarioRepository.save(usuarioAtualizado).getEnderecos());
            log.info("Usuário com id {} atualizado com sucesso", id);
            return usuarioMapper.toUsuarioDetalhesDto(usuarioAtualizado);
        }).orElseThrow(() -> {
            log.info("Usuário ativo com id {} não encontrado", id);
            return new UsuarioNotFoundException(id, true);
        });
    }

    /**
     * @param id o id do usuário a ser desativado.
     *
     * @return uma mensagem de confirmação de desativação.
     *
     * @throws UsuarioNotFoundException caso o usuário não seja encontrado.
     */
    public String desativar(Long id) {
        return usuarioRepository.findByIdAndAtivoTrue(id).map(u -> {
            u.setAtivo(false);
            usuarioRepository.save(u);
            log.info("Usuário com id {} desativado com sucesso", id);
            return MessageFormat.format("Usuário com id {0} desativado com sucesso", id);
        }).orElseThrow(() -> {
            log.info("Usuário ativo com id {} não encontrado", id);
            return new UsuarioNotFoundException(id, true);
        });
    }

    /**
     * @param id o id do usuário a ser reativado.
     *
     * @return uma mensagem de confirmação de reativação.
     *
     * @throws UsuarioNotFoundException caso o usuário não seja encontrado.
     */
    public String reativar(Long id) {
        return usuarioRepository.findByIdAndAtivoFalse(id).map(u -> {
            u.setAtivo(true);
            usuarioRepository.save(u);
            log.info("Usuário com id {} reativado com sucesso", id);
            return MessageFormat.format("Usuário com id {0} reativado com sucesso", id);
        }).orElseThrow(() -> {
            log.info("Usuário inativo com id {} não encontrado", id);
            return new UsuarioNotFoundException(id, false);
        });
    }

}
