package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Pais;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.cross.exception.UsuarioAlreadyExistsException;
import com.github.andregpereira.resilientshop.userapi.cross.exception.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.PaisRepository;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UsuarioManutencaoServiceImpl implements UsuarioManutencaoService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final EnderecoRepository enderecoRepository;
    private final PaisRepository paisRepository;

    public UsuarioDetalhesDto registrar(UsuarioRegistroDto usuarioRegistroDto) {
        if (usuarioRepository.existsByCpf(usuarioRegistroDto.cpf())) {
            log.info("Usuário já cadastrado com o CPF informado");
            throw new UsuarioAlreadyExistsException(
                    "Não foi possível cadastrar o usuário. Já existe um usuário cadastrado com este CPF. Verifique e tente novamente");
        }
        Usuario usuario = usuarioMapper.toUsuario(usuarioRegistroDto);
        Endereco endereco = usuario.getEndereco();
        Pais pais = endereco.getPais();
        usuario.setDataCriacao(LocalDate.now());
        usuario.setDataModificacao(LocalDate.now());
        usuario.setAtivo(true);
        Optional<Pais> optionalPais = paisRepository.findByNomeOrCodigo(pais.getNome(), pais.getCodigo());
        if (optionalPais.isEmpty()) {
            log.info("País não encontrado. Criando novo país");
            pais = paisRepository.save(pais);
        } else {
            log.info("Retornando país encontrado");
            pais = optionalPais.get();
        }
        endereco.setPais(pais);
        endereco = enderecoRepository.save(endereco);
        usuario.setEndereco(endereco);
        usuario = usuarioRepository.save(usuario);
        log.info("Usuário criado");
        return usuarioMapper.toUsuarioDetalhesDto(usuario);
    }

    public UsuarioDetalhesDto atualizar(Long id, UsuarioAtualizacaoDto usuarioAtualizacaoDto) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndAtivoTrue(id);
        if (usuarioOptional.isEmpty()) {
            log.info("Usuário com id {} não encontrado", id);
            throw new UsuarioNotFoundException(
                    "Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
        }
        Usuario usuarioAntigo = usuarioOptional.get();
        Usuario usuarioAtualizado = usuarioMapper.toUsuario(usuarioAtualizacaoDto);
        Endereco endereco = usuarioAtualizado.getEndereco();
        Pais pais = endereco.getPais();
        usuarioAtualizado.setId(id);
        usuarioAtualizado.setCpf(usuarioAntigo.getCpf());
        usuarioAtualizado.setDataCriacao(usuarioAntigo.getDataCriacao());
        usuarioAtualizado.setDataModificacao(LocalDate.now());
        usuarioAtualizado.setAtivo(true);
        Optional<Pais> optionalPais = paisRepository.findByNomeOrCodigo(pais.getNome(), pais.getCodigo());
        if (optionalPais.isEmpty()) {
            log.info("País não encontrado. Criando novo país");
            pais = paisRepository.save(pais);
        } else {
            log.info("Retornando país encontrado");
            pais = optionalPais.get();
        }
        endereco.setId(usuarioAntigo.getEndereco().getId());
        endereco.setPais(pais);
        endereco = enderecoRepository.save(endereco);
        usuarioAtualizado.setEndereco(endereco);
        Usuario usuario = usuarioRepository.save(usuarioAtualizado);
        log.info("Usuário com id {} atualizado", id);
        return usuarioMapper.toUsuarioDetalhesDto(usuario);
    }

    public String desativar(Long id) {
        usuarioRepository.findByIdAndAtivoTrue(id).ifPresentOrElse(u -> {
            u.setAtivo(false);
            usuarioRepository.save(u);
            log.info("Usuário com id {} desativado", id);
        }, () -> {
            log.info("Usuário ativo com id {} não encontrado", id);
            throw new UsuarioNotFoundException(
                    "Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
        });
        return "Usuário desativado";
    }

    public String reativar(Long id) {
        usuarioRepository.findByIdAndAtivoFalse(id).ifPresentOrElse(u -> {
            u.setAtivo(true);
            usuarioRepository.save(u);
            log.info("Usuário com id {} reativado", id);
        }, () -> {
            log.info("Usuário inativo com id {} não encontrado", id);
            throw new UsuarioNotFoundException(
                    "Não foi possível encontrar um usuário inativo com este id. Verifique e tente novamente");
        });
        return "Usuário reativado";
    }

}
