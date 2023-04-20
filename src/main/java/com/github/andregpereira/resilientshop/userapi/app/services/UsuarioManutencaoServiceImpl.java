package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.cross.exception.UsuarioAlreadyExistsException;
import com.github.andregpereira.resilientshop.userapi.cross.exception.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;
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
import java.util.List;
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
        usuario.setDataCriacao(LocalDate.now());
        usuario.setDataModificacao(LocalDate.now());
        usuario.setAtivo(true);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        List<Endereco> enderecos = usuario.getEnderecos();
        enderecos.stream().forEach(e -> {
            Pais pais = e.getPais();
            Optional<Pais> optionalPais = paisRepository.findByNomeOrCodigo(pais.getNome(), pais.getCodigo());
            if (optionalPais.isEmpty()) {
                log.info("País não encontrado. Criando novo país");
                pais = paisRepository.save(pais);
                log.info("País criado com sucesso");
            } else {
                log.info("Retornando país encontrado");
                pais = optionalPais.get();
            }
            e.setUsuario(usuarioSalvo);
            e.setPais(pais);
        });
        enderecos = enderecoRepository.saveAll(enderecos);
        usuarioSalvo.setEnderecos(enderecos);
        return usuarioMapper.toUsuarioDetalhesDto(usuarioSalvo);
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
        usuarioAtualizado.setId(id);
        usuarioAtualizado.setCpf(usuarioAntigo.getCpf());
        usuarioAtualizado.setDataCriacao(usuarioAntigo.getDataCriacao());
        usuarioAtualizado.setDataModificacao(LocalDate.now());
        usuarioAtualizado.setAtivo(true);
        Usuario usuarioSalvo = usuarioRepository.save(usuarioAtualizado);
        List<Endereco> enderecos = usuarioAtualizado.getEnderecos();
        enderecos.stream().forEach(e -> {
            Pais pais = e.getPais();
            Optional<Pais> optionalPais = paisRepository.findByNomeOrCodigo(pais.getNome(), pais.getCodigo());
            if (optionalPais.isEmpty()) {
                log.info("País não encontrado. Criando novo país");
                pais = paisRepository.save(pais);
                log.info("País criado com sucesso");
            } else {
                log.info("Retornando país encontrado");
                pais = optionalPais.get();
            }
            e.setId(usuarioAntigo.getEnderecos().stream().mapToLong(Endereco::getId).reduce(0L, (a, b) -> b));
            e.setUsuario(usuarioSalvo);
            e.setPais(pais);
        });
        enderecos = enderecoRepository.saveAll(enderecos);
        usuarioSalvo.setEnderecos(enderecos);
        log.info("Usuário com id {} atualizado com sucesso", id);
        return usuarioMapper.toUsuarioDetalhesDto(usuarioSalvo);
    }

    public String desativar(Long id) {
        usuarioRepository.findByIdAndAtivoTrue(id).ifPresentOrElse(u -> {
            u.setAtivo(false);
            usuarioRepository.save(u);
        }, () -> {
            log.info("Usuário ativo com id {} não encontrado", id);
            throw new UsuarioNotFoundException(
                    "Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
        });
        log.info("Usuário com id {} desativado com sucesso", id);
        return "Usuário desativado";
    }

    public String reativar(Long id) {
        usuarioRepository.findByIdAndAtivoFalse(id).ifPresentOrElse(u -> {
            u.setAtivo(true);
            usuarioRepository.save(u);
        }, () -> {
            log.info("Usuário inativo com id {} não encontrado", id);
            throw new UsuarioNotFoundException(
                    "Não foi possível encontrar um usuário inativo com este id. Verifique e tente novamente");
        });
        log.info("Usuário com id {} reativado com sucesso", id);
        return "Usuário reativado";
    }

}
