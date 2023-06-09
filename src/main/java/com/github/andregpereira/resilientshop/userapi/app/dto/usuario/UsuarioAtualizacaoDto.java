package com.github.andregpereira.resilientshop.userapi.app.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioAtualizacaoDto(@NotBlank(message = "Insira o nome") @Size(
        message = "O nome deve ter entre 2 e 255 caracteres", min = 2, max = 255) String nome,
        @NotBlank(message = "Insira o sobrenome") @Size(message = "O sobrenome deve ter entre 2 e 255 caracteres",
                min = 2, max = 255) String sobrenome,
        @NotBlank(message = "O e-mail é obrigatório") @Email(message = "Insira um e-mail válido") String email,
        @Size(message = "O telefone deve ter no máximo 20 caracteres", max = 20) @Pattern(
                message = "Formato do " + "telefone inválido. " + "Por favor, informe um telefone no seguinte formato +xxx (xx) xxxxx-xxxx",
                regexp = "^[+]\\d{2,3} [(]\\d{2}[)] \\d{4," + "5}-\\d{4}$") String telefone,
        @NotBlank(message = "A senha é obrigatória") @Size(message = "A senha deve ter entre 8 e 255 caracteres",
                min = 8, max = 255) String senha) {

}
