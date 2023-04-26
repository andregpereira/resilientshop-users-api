package com.github.andregpereira.resilientshop.userapi.cross.exceptions;

import java.text.MessageFormat;

public class UsuarioNotFoundException extends RuntimeException {

    public UsuarioNotFoundException(String message) {
        super(message);
    }

    public UsuarioNotFoundException(Long id) {
        super(MessageFormat.format(
                "Não foi possível encontrar um usuário ativo com o id {0}. Verifique e tente novamente", id));
    }

}
