package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dtos.endereco.EnderecoDto;

public interface EnderecoConsultaService {

    EnderecoDto consultarPorId(Long id, Long idUsuario);

    EnderecoDto consultarPorApelido(Long idUsuario, String apelido);

}
