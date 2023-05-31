package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroDtoNovo;

public interface EnderecoManutencaoService {

    EnderecoDto criar(EnderecoRegistroDtoNovo dto);

    EnderecoDto atualizar(Long id, EnderecoRegistroDtoNovo dto);

    String remover(Long id);

}
