package com.github.andregpereira.resilientshop.userapi.app.dto.usuario.decorator;

import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.decorator.validator.SenhaValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SenhaValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SenhasIguais {

    String message() default "Opa! As senhas não estão iguais. Verifique e tente novamente";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
