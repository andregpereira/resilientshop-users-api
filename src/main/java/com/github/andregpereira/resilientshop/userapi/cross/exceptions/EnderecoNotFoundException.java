package com.github.andregpereira.resilientshop.userapi.cross.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EnderecoNotFoundException extends RuntimeException {

    public EnderecoNotFoundException(String tipo, Long id) {
        super(definirTipo(tipo, id));
    }

    public EnderecoNotFoundException(Long id, Long idUsuario) {
        super(MessageFormat.format("Opa! O usuário com id {0} não possui endereços cadastrados", idUsuario));
    }

    public EnderecoNotFoundException(String apelido) {
        super(MessageFormat.format(
                "Não foi possível encontrar um endereço com o apelido {0}. Verifique e tente novamente", apelido));
    }
//    public EnderecoNotFoundException(String apelido, Long idUsuario) {
//        super(MessageFormat.format(
//                "Não foi possível encontrar um endereço com o apelido {0}. O usuário com id {1} está inativo", apelido,
//                idUsuario));
//    }

    private static String definirTipo(String tipo, Object atributo) {
        if ("endereço".equals(tipo))
            return MessageFormat.format(
                    "Não foi possível encontrar um endereço com o id {0}. Verifique e tente novamente", atributo);
        else if ("usuário".equals(tipo))
            return MessageFormat.format("Opa! O usuário com id {0} não possui endereços cadastrados", atributo);
        else
            return MessageFormat.format("Não foi possível encontrar um endereço com o apelido {0}", tipo);
    }

}
