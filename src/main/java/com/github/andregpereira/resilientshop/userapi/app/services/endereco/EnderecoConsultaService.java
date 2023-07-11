package com.github.andregpereira.resilientshop.userapi.app.services.endereco;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnderecoConsultaService {

    EnderecoDto consultarPorId(Long id);

    Page<EnderecoDto> consultarPorIdUsuario(Long idUsuario, Pageable pageable);

    EnderecoDto consultarPorApelido(Long idUsuario, String apelido);

}
