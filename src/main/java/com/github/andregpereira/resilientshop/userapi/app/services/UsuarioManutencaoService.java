package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioRegistroDto;

public interface UsuarioManutencaoService {

    UsuarioDetalhesDto registrar(UsuarioRegistroDto usuarioRegistroDto);

    UsuarioDetalhesDto atualizar(Long id, UsuarioAtualizacaoDto usuarioAtualizacaoDto);

    String desativar(Long id);

    String reativar(Long id);

}
