package com.github.andregpereira.resilientshop.userapi.app.dto.usuario;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroUsuarioNovoDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record UsuarioRegistroDto(@NotBlank(message = "Insira o nome") @Size(
        message = "O nome deve ter entre 2 e 255 caracteres", min = 2, max = 255) String nome,
        @NotBlank(message = "Insira o sobrenome") @Size(message = "O sobrenome deve ter entre 2 e 255 caracteres",
                min = 2, max = 255) String sobrenome,
        @NotBlank(message = "Insira o CPF") @CPF(
                message = "CPF inválido ou inexistente. Formatos aceitos: xxx.xxx.xxx-xx, xxxxxxxxx-xx ou xxxxxxxxxxx") String cpf,
        @Size(message = "O telefone deve ter no máximo 20 caracteres", max = 20) @Pattern(
                message = "Formato do telefone inválido. Por favor, informe um telefone no seguinte formato +xxx (xx) xxxxx-xxxx",
                regexp = "^[+]\\d{2,3} [(]\\d{2}[)] \\d{4,5}-\\d{4}$") String telefone,
        @Valid EnderecoRegistroUsuarioNovoDto endereco) {

}
