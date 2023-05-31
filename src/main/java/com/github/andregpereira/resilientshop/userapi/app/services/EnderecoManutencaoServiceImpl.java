package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroDtoNovo;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.EnderecoNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.EnderecoMapper;
import com.github.andregpereira.resilientshop.userapi.cross.validations.PaisValidation;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

/**
 * Classe de serviço de manutenção de {@link Endereco}.
 *
 * @author André Garcia
 * @see EnderecoManutencaoService
 */
@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class EnderecoManutencaoServiceImpl implements EnderecoManutencaoService {

    /**
     * Injeção da dependência {@link EnderecoRepository} para realizar operações de
     * manutenção na tabela de endereços no banco de dados.
     */
    private final EnderecoRepository enderecoRepository;

    /**
     * Injeção da dependência {@link EnderecoMapper} para realizar
     * conversões de DTO e entidade de endereço.
     */
    private final EnderecoMapper mapper;

    /**
     * Injeção da dependência {@link UsuarioRepository} para realizar operações de
     * consulta na tabela de usuários no banco de dados.
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Injeção da dependência {@link PaisValidation} para validar o país.
     */
    private final PaisValidation paisValidation;

    @Override
    public EnderecoDto criar(EnderecoRegistroDtoNovo dto) {
        return usuarioRepository.findById(dto.idUsuario()).map(u -> {
            Endereco endereco = mapper.toEndereco(dto);
            paisValidation.validarPais(endereco.getPais());
            return endereco;
        }).map(enderecoRepository::save).map(mapper::toEnderecoDto).orElseThrow(
                () -> new UsuarioNotFoundException(dto.idUsuario()));
    }

    @Override
    public EnderecoDto atualizar(Long id, EnderecoRegistroDtoNovo dto) {
        return enderecoRepository.findById(id).map(e -> usuarioRepository.findById(dto.idUsuario()).map(u -> {
            Endereco endereco = mapper.toEndereco(dto);
            paisValidation.validarPais(endereco.getPais());
            return endereco;
        }).map(mapper::toEnderecoDto).orElseThrow(() -> new UsuarioNotFoundException(dto.idUsuario()))).orElseThrow(
                () -> new EnderecoNotFoundException(id));
    }

    @Override
    public String remover(Long id) {
        return enderecoRepository.findById(id).map(u -> {
            enderecoRepository.deleteById(id);
            log.info("Endereço com id {} removido com sucesso", id);
            return MessageFormat.format("Endereço com id {0} removido com sucesso", id);
        }).orElseThrow(() -> {
            log.info("Endereço não encontrado com id {}", id);
            return new EnderecoNotFoundException(id);
        });
    }

}
