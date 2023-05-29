package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioConsultaService {

    Page<UsuarioDto> listar(Pageable pageable);

    UsuarioDetalhesDto consultarPorId(Long id);

    UsuarioDetalhesDto consultarPorCpf(String cpf);

    Page<UsuarioDto> consultarPorNome(String nome, String sobrenome, Pageable pageable);

}
