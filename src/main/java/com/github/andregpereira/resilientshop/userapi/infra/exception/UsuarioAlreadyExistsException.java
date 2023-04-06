package com.github.andregpereira.resilientshop.userapi.infra.exception;

public class UsuarioAlreadyExistsException extends RuntimeException {

    public UsuarioAlreadyExistsException(String message) {
        super(message);
    }

}
