package com.github.andregpereira.resilientshop.userapi.app.dto.usuario.decorator.validator;

import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.decorator.SenhasIguais;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenhaValidator implements ConstraintValidator<SenhasIguais, UsuarioRegistroDto> {

    @Override
    public boolean isValid(UsuarioRegistroDto dto, ConstraintValidatorContext context) {
        if (!dto.senha().equals(dto.confirmarSenha())) {
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(
                    "confirmarSenha").addConstraintViolation().disableDefaultConstraintViolation();
            return false;
        }
        return true;
    }

}
