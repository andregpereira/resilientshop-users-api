package com.github.andregpereira.resilientshop.userapi.cross.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioNotFoundException extends RuntimeException {

    public UsuarioNotFoundException(String message) {
        super(message);
    }

    public UsuarioNotFoundException(Long id) {
        super(MessageFormat.format(
                "Não foi possível encontrar um usuário ativo com o id {0}. Verifique e tente novamente", id));
    }

}
