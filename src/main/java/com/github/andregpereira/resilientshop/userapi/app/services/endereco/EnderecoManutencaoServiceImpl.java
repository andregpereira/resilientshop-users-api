package com.github.andregpereira.resilientshop.userapi.app.services.endereco;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroDto;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.EnderecoAlreadyExistsException;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.EnderecoNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.EnderecoMapper;
import com.github.andregpereira.resilientshop.userapi.cross.validations.PaisValidation;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
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
 * @see com.github.andregpereira.resilientshop.userapi.app.services.endereco.EnderecoManutencaoService
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
    public EnderecoDto criar(EnderecoRegistroDto dto) {
        return usuarioRepository.findById(dto.idUsuario()).map(u -> {
            u.getEnderecos().stream().filter(e -> dto.apelido().equalsIgnoreCase(e.getApelido())).findAny().ifPresent(
                    enderecoRepetido -> {
                        log.info(
                                "Falha ao tentar cadastrar um novo endereço. O usuário com id {} já possui um endereço com o apelido {}",
                                dto.idUsuario(), enderecoRepetido.getApelido());
                        throw new EnderecoAlreadyExistsException(enderecoRepetido.getApelido());
                    });
            return salvarEndereco(dto, u);
        }).orElseThrow(() -> {
            log.info("Falha ao tentar cadastrar um novo endereço. Usuário com id {} não encontrado", dto.idUsuario());
            return new UsuarioNotFoundException(dto.idUsuario());
        });
    }

    @Override
    public EnderecoDto atualizar(Long id, EnderecoRegistroDto dto) {
        return enderecoRepository.findById(id).map(e -> usuarioRepository.findById(dto.idUsuario()).map(u -> {
            u.getEnderecos().stream().filter(
                    filterEndereco -> dto.apelido().equalsIgnoreCase(filterEndereco.getApelido()) && !id.equals(
                            filterEndereco.getId())).findAny().ifPresent(enderecoRepetido -> {
                log.info("O usuário com id {} já possui um endereço com o apelido {}", dto.idUsuario(),
                        enderecoRepetido.getApelido());
                throw new EnderecoAlreadyExistsException(enderecoRepetido.getApelido());
            });
            return salvarEndereco(dto, u);
        }).orElseThrow(() -> {
            log.info("Usuário com id {} não encontrado", dto.idUsuario());
            return new UsuarioNotFoundException(dto.idUsuario());
        })).orElseThrow(() -> {
            log.info("Não foi encontrado um endereço com id {}", id);
            return new EnderecoNotFoundException(id);
        });
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

    private EnderecoDto salvarEndereco(EnderecoRegistroDto dto, Usuario u) {
        Endereco endereco = mapper.toEndereco(dto);
        endereco.setPais(paisValidation.validarPais(endereco.getPais()));
        configurarPadrao(endereco, u);
        endereco.setUsuario(u);
        return mapper.toEnderecoDto(enderecoRepository.save(endereco));
    }

    private void configurarPadrao(Endereco endereco, Usuario usuario) {
        if (endereco.isPadrao()) {
            usuario.getEnderecos().forEach(e -> e.setPadrao(false));
            enderecoRepository.saveAll(usuario.getEnderecos());
        } else if (usuario.getEnderecos().isEmpty()) {
            endereco.setPadrao(true);
        }
    }

}
