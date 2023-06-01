package com.github.andregpereira.resilientshop.userapi.cross.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(HttpStatus.CONFLICT)
public class EnderecoAlreadyExistsException extends RuntimeException {

    public EnderecoAlreadyExistsException(String apelido) {
        super(MessageFormat.format("Ops! Já existe um endereço com o apelido {0}", apelido));
    }

}
