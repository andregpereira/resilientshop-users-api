package com.github.andregpereira.resilientshop.userapi.cross.exceptions;

import java.text.MessageFormat;

public class EnderecoNotFoundException extends RuntimeException {

    public EnderecoNotFoundException() {
        super("Ops! O usuário não possui endereços cadastrados");
    }

    public EnderecoNotFoundException(Long id) {
        super(MessageFormat.format("Não foi possível encontrar um endereço com o id {0}. Verifique e tente novamente",
                id));
    }

    public EnderecoNotFoundException(String apelido) {
        super(MessageFormat.format(
                "Não foi possível encontrar um endereço com o apelido {0}. Verifique e tente novamente", apelido));
    }

}
