package com.github.andregpereira.resilientshop.userapi.infra.exception;

public class UsuarioNotFoundException extends RuntimeException {

    public UsuarioNotFoundException(String message) {
        super(message);
    }

}
