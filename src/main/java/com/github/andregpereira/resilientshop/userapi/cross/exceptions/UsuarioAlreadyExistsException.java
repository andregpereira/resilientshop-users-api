package com.github.andregpereira.resilientshop.userapi.cross.exceptions;

public class UsuarioAlreadyExistsException extends RuntimeException {

    public UsuarioAlreadyExistsException(String message) {
        super(message);
    }

}
