package com.github.andregpereira.resilientshop.userapi.app.services.endereco;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.EnderecoNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.EnderecoMapper;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Classe de serviço de consulta de {@link Endereco}.
 *
 * @author André Garcia
 * @see EnderecoManutencaoService
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class EnderecoConsultaServiceImpl implements EnderecoConsultaService {

    /**
     * Injeção da dependência {@link EnderecoRepository} para realizar operações de
     * consulta na tabela de endereços no banco de dados.
     */
    private final EnderecoRepository enderecoRepository;

    /**
     * Injeção da dependência {@link UsuarioRepository} para realizar operações de
     * consulta na tabela de usuários no banco de dados.
     */
    private final UsuarioRepository usuarioRepository;
    private final EnderecoMapper mapper;


    @Override
    public EnderecoDto consultarPorId(Long id) {
        return enderecoRepository.findById(id).map(e -> {
            log.info("Retornando endereço com id {}", id);
            return mapper.toEnderecoDto(e);
        }).orElseThrow(() -> {
            log.info("Nào foi encontrado um endereço com id {}", id);
            return new EnderecoNotFoundException("endereço", id);
        });
    }

    @Override
    public Page<EnderecoDto> consultarPorIdUsuario(Long idUsuario, Pageable pageable) {
        return usuarioRepository.findById(idUsuario).map(u -> Optional.of(
                enderecoRepository.findAllByUsuarioId(idUsuario, pageable)).filter(Predicate.not(Page::isEmpty)).map(
                p -> p.map(mapper::toEnderecoDto)).orElseThrow(
                () -> new EnderecoNotFoundException("usuário", idUsuario))).orElseThrow(
                () -> new UsuarioNotFoundException(idUsuario));
    }

    @Override
    public EnderecoDto consultarPorApelido(Long idUsuario, String apelido) {
        return enderecoRepository.findByApelidoAndUsuarioIdAndUsuarioAtivoTrue(apelido, idUsuario).map(e -> {
            log.info("Retornando endereço com apelido {}", apelido);
            return mapper.toEnderecoDto(e);
        }).orElseThrow(() -> {
            log.info("Usuário com id {} não possui um endereço com o apelido {}", idUsuario, apelido);
            return new EnderecoNotFoundException(apelido, idUsuario);
        });
    }

}
