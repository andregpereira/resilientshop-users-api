package com.github.andregpereira.resilientshop.userapi.app.dto.usuario;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroUsuarioNovoDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.decorator.SenhasIguais;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@SenhasIguais
public record UsuarioRegistroDto(@NotBlank(message = "Insira o nome") @Size(
        message = "O nome deve ter entre 2 e 255 caracteres", min = 2, max = 255) String nome,
        @Size(message = "O apelido deve ter no máximo 30 caracteres", max = 30) String nomeSocial,
        @NotBlank(message = "Insira o CPF") @CPF(
                message = "CPF inválido ou inexistente. Formatos aceitos: xxx.xxx.xxx-xx, xxxxxxxxx-xx ou xxxxxxxxxxx") String cpf,
        @NotNull(message = "Insira a data de nascimento") LocalDate dataNascimento,
        @NotBlank(message = "O e-mail é obrigatório") @Email(message = "Insira um e-mail válido") String email,
        @Size(message = "O celular deve ter no máximo 20 caracteres", max = 20) @Pattern(
                message = "Formato do celular inválido. Por favor, informe um celular no seguinte formato +xxx (xx) xxxxx-xxxx",
                regexp = "^[+]\\d{2,3} [(]\\d{2}[)] \\d{4,5}-\\d{4}$") String celular,
        @NotBlank(message = "A senha é obrigatória") @Size(message = "A senha deve ter entre 8 e 255 caracteres",
                min = 8, max = 255) String senha,
        @NotBlank(message = "A confirmação de senha é obrigatória") String confirmarSenha,
        @Valid EnderecoRegistroUsuarioNovoDto endereco) {

}
