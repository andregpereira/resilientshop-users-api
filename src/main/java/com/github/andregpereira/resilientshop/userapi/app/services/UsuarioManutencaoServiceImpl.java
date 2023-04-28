package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioAlreadyExistsException;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Pais;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.PaisRepository;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class UsuarioManutencaoServiceImpl implements UsuarioManutencaoService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final EnderecoRepository enderecoRepository;
    private final PaisRepository paisRepository;

    public UsuarioDetalhesDto registrar(UsuarioRegistroDto dto) {
        if (usuarioRepository.existsByCpf(dto.cpf())) {
            log.info("Usuário já cadastrado com o CPF informado");
            throw new UsuarioAlreadyExistsException();
        }
        Usuario usuario = usuarioMapper.toUsuario(dto);
        usuario.getEnderecos().stream().forEach(e -> {
            Pais pais = e.getPais();
            paisRepository.findByNomeOrCodigo(pais.getNome(), pais.getCodigo()).ifPresentOrElse(p -> {
                log.info("Retornando país encontrado");
                e.setPais(p);
            }, () -> {
                log.info("País não encontrado. Criando novo país");
                paisRepository.save(pais);
                log.info("País criado com sucesso");
            });
            e.setUsuario(usuario);
        });
        LocalDate agora = LocalDate.now();
        usuario.setDataCriacao(agora);
        usuario.setDataModificacao(agora);
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
        enderecoRepository.saveAll(usuario.getEnderecos());
        return usuarioMapper.toUsuarioDetalhesDto(usuario);
    }

    public UsuarioDetalhesDto atualizar(Long id, UsuarioAtualizacaoDto dto) {
        return usuarioRepository.findByIdAndAtivoTrue(id).map(usuarioAntigo -> {
            Usuario usuarioAtualizado = usuarioMapper.toUsuario(dto);
            enderecoRepository.deleteByUsuario(usuarioAntigo);
            usuarioAtualizado.getEnderecos().stream().forEach(e -> {
                Pais pais = e.getPais();
                log.info("Procurando país...");
                paisRepository.findByNomeOrCodigo(pais.getNome(), pais.getCodigo()).ifPresentOrElse(p -> {
                    log.info("Retornando país encontrado");
                    e.setPais(p);
                }, () -> {
                    log.info("País não encontrado. Criando novo país");
                    paisRepository.save(pais);
                    log.info("País criado com sucesso");
                });
                e.setUsuario(usuarioAtualizado);
            });
            usuarioAtualizado.setId(id);
            usuarioAtualizado.setCpf(usuarioAntigo.getCpf());
            usuarioAtualizado.setDataCriacao(usuarioAntigo.getDataCriacao());
            usuarioAtualizado.setDataModificacao(LocalDate.now());
            usuarioAtualizado.setAtivo(true);
            usuarioRepository.save(usuarioAtualizado);
            enderecoRepository.saveAll(usuarioAtualizado.getEnderecos());
            log.info("Usuário com id {} atualizado com sucesso", id);
            return usuarioMapper.toUsuarioDetalhesDto(usuarioAtualizado);
        }).orElseThrow(() -> {
            log.info("Usuário com id {} não encontrado", id);
            return new UsuarioNotFoundException(id);
        });
    }

    public String desativar(Long id) {
        return usuarioRepository.findByIdAndAtivoTrue(id).map(u -> {
            u.setAtivo(false);
            usuarioRepository.save(u);
            log.info("Usuário com id {} desativado com sucesso", id);
            return "Usuário desativado";
        }).orElseThrow(() -> {
            log.info("Usuário ativo com id {} não encontrado", id);
            return new UsuarioNotFoundException(
                    "Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
        });
    }

    public String reativar(Long id) {
        return usuarioRepository.findByIdAndAtivoFalse(id).map(u -> {
            u.setAtivo(true);
            usuarioRepository.save(u);
            log.info("Usuário com id {} reativado com sucesso", id);
            return "Usuário reativado";
        }).orElseThrow(() -> {
            log.info("Usuário inativo com id {} não encontrado", id);
            throw new UsuarioNotFoundException(
                    "Não foi possível encontrar um usuário inativo com este id. Verifique e tente novamente");
        });
    }

}
