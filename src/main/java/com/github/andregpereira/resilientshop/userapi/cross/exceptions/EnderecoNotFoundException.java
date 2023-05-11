package com.github.andregpereira.resilientshop.userapi.cross.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EnderecoNotFoundException extends RuntimeException {

    public EnderecoNotFoundException(Long id) {
        super(MessageFormat.format("Não foi possível encontrar um endereço com o id {0}. Verifique e tente novamente",
                id));
    }

    public EnderecoNotFoundException(String apelido) {
        super(MessageFormat.format(
                "Não foi possível encontrar um endereço com o apelido {0}. Verifique e tente novamente", apelido));
    }

    public EnderecoNotFoundException(String apelido, Long idUsuario) {
        super(MessageFormat.format(
                "Não foi possível encontrar um endereço com o apelido {0}. O usuário com id {1} está inativo", apelido,
                idUsuario));
    }

}
