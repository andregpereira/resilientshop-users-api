package com.github.andregpereira.resilientshop.userapi.cross.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsuarioAlreadyExistsException extends RuntimeException {

    public UsuarioAlreadyExistsException() {
        super("Opa! Já existe um usuário cadastrado com esse CPF");
    }

}
