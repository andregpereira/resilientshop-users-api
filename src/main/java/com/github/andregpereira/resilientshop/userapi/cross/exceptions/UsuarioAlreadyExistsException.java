package com.github.andregpereira.resilientshop.userapi.cross.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsuarioAlreadyExistsException extends RuntimeException {

    public UsuarioAlreadyExistsException(String atributo) {
        super(MessageFormat.format("Opa! Já existe um usuário cadastrado com esse {0}", atributo));
    }

}
