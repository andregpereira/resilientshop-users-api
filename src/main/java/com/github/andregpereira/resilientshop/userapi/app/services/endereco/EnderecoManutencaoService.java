package com.github.andregpereira.resilientshop.userapi.app.services.endereco;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroDto;

public interface EnderecoManutencaoService {

    EnderecoDto criar(EnderecoRegistroDto dto);

    EnderecoDto atualizar(Long id, EnderecoRegistroDto dto);

    String remover(Long id);

}
