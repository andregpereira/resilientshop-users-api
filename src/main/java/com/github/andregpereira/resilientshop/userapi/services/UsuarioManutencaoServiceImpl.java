package com.github.andregpereira.resilientshop.userapi.services;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.entities.Endereco;
import com.github.andregpereira.resilientshop.userapi.entities.Pais;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.infra.exception.UsuarioAlreadyExistsException;
import com.github.andregpereira.resilientshop.userapi.infra.exception.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.repositories.PaisRepository;
import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

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
            pais = paisRepository.save(pais);
        } else {
            pais = optionalPais.get();
        }
        endereco.setPais(pais);
        endereco = enderecoRepository.save(endereco);
        usuario.setEndereco(endereco);
        return usuarioMapper.toUsuarioDetalhesDto(usuarioRepository.save(usuario));
    }

    public UsuarioDetalhesDto atualizar(Long id, UsuarioAtualizacaoDto usuarioAtualizacaoDto) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAndAtivoTrue(id);
        if (usuarioOptional.isEmpty()) {
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
            pais = paisRepository.save(pais);
        } else {
            pais = optionalPais.get();
        }
        endereco.setId(usuarioAntigo.getEndereco().getId());
        endereco.setPais(pais);
        endereco = enderecoRepository.save(endereco);
        usuarioAtualizado.setEndereco(endereco);
        return usuarioMapper.toUsuarioDetalhesDto(usuarioRepository.save(usuarioAtualizado));
    }

    public String desativar(Long id) {
        usuarioRepository.findByIdAndAtivoTrue(id).ifPresentOrElse(u -> {
            usuarioRepository.deactivateById(id);
        }, () -> {
            throw new UsuarioNotFoundException(
                    "Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
        });
        return "Usuário desativado";
    }

    public String reativar(Long id) {
        usuarioRepository.findByIdAndAtivoFalse(id).ifPresentOrElse(u -> {
            usuarioRepository.activateById(id);
        }, () -> {
            throw new UsuarioNotFoundException(
                    "Não foi possível encontrar um usuário inativo com este id. Verifique e tente novamente");
        });
        return "Usuário reativado";
    }

}
