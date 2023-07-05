package com.github.andregpereira.resilientshop.userapi.infra.feignclient;

import com.github.andregpereira.resilientshop.commons.dto.UsuarioCredentialRegistroDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "resilientshop-authentication-api", path = "/auth")
public interface AuthFeignClient {

    @PostMapping("/usuario")
    void cadastrarUsuario(@RequestBody UsuarioCredentialRegistroDto dto);

    @DeleteMapping("/{id}")
    Void desativar(@PathVariable Long id);

    @PutMapping("/reativar/{id}")
    Void reativar(@PathVariable Long id);

}
