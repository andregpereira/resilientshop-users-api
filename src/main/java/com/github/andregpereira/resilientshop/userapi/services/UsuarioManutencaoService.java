package com.github.andregpereira.resilientshop.userapi.services;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioRegistroDto;

public interface UsuarioManutencaoService {

    UsuarioDetalhesDto registrar(UsuarioRegistroDto usuarioRegistroDto);

    UsuarioDetalhesDto atualizar(Long id, UsuarioAtualizacaoDto usuarioAtualizacaoDto);

    String desativar(Long id);

    String reativar(Long id);

}
