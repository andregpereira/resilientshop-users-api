package com.github.andregpereira.resilientshop.userapi.cross.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioNotFoundException extends RuntimeException {

    public UsuarioNotFoundException() {
        super("Opa! Não há usuários cadastrados");
    }

    public UsuarioNotFoundException(Long id) {
        super(MessageFormat.format("Não foi possível encontrar um usuário com o id {0}. Verifique e tente novamente",
                id));
    }

    public UsuarioNotFoundException(String atributo, String valor) {
        super(MessageFormat.format(
                "Não foi possível encontrar um usuário ativo com o {0} {1}. Verifique e tente novamente", atributo,
                valor));
    }

    public UsuarioNotFoundException(Long id, boolean ativo) {
        super(MessageFormat.format(
                "Não foi possível encontrar um usuário {1} com o id {0}. Verifique e tente novamente", id,
                ativo ? "ativo" : "inativo"));
    }

}
