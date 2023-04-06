package com.github.andregpereira.resilientshop.userapi.services;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioConsultaService {

    UsuarioDetalhesDto consultarPorId(Long id);

    UsuarioDetalhesDto consultarPorCpf(String cpf);

    Page<UsuarioDto> consultarPorNome(String nome, String sobrenome, Pageable pageable);

}
