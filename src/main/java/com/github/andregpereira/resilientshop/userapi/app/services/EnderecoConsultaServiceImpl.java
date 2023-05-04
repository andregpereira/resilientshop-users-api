package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dtos.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.EnderecoNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.EnderecoMapper;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class EnderecoConsultaServiceImpl implements EnderecoConsultaService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoMapper mapper;

    public EnderecoDto consultarPorId(Long id, Long idUsuario) {
//        log.info("Procurando usuário ativo com id {}...", idUsuario);
//        if (!usuarioRepository.existsByIdAndAtivoTrue(idUsuario)) {
//            log.info("Usuário ativo com id {} não encontrado", idUsuario);
//            throw new UsuarioNotFoundException(idUsuario, true);
//        } else
        return enderecoRepository.findById(id).map(e -> {
            log.info("Retornando endereço com id {}", id);
            return mapper.toEnderecoDto(e);
        }).orElseThrow(() -> {
            log.info("Nào foi encontrado um endereço com id {}", id);
            return new EnderecoNotFoundException(id);
        });
    }

    public EnderecoDto consultarPorApelido(Long idUsuario, String apelido) {
//        log.info("Procurando usuário ativo com id {}...", idUsuario);
//        if (!usuarioRepository.existsByIdAndAtivoTrue(idUsuario)) {
//            log.info("Usuário ativo com id {} não encontrado", idUsuario);
//            throw new UsuarioNotFoundException(idUsuario, true);
//        } else
        return enderecoRepository.findByApelidoAndUsuarioIdAndUsuarioAtivoTrue(apelido, idUsuario).map(e -> {
            log.info("Retornando endereço com apelido {}", apelido);
            return mapper.toEnderecoDto(e);
        }).orElseThrow(() -> {
            log.info("Usuário com id {} não possui um endereço com o apelido {}", idUsuario, apelido);
            return new EnderecoNotFoundException(apelido, idUsuario);
        });
    }

}
