package com.github.andregpereira.resilientshop.userapi.infra.feignclient;

import com.github.andregpereira.resilientshop.commons.dto.UsuarioCredentialRegistroDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "resilientshop-authentication-api", path = "/auth")
public interface AuthFeignClient {

    @PostMapping("/usuario")
    void cadastrarUsuario(@RequestBody UsuarioCredentialRegistroDto dto);

}
