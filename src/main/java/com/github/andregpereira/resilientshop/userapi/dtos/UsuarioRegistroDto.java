package com.github.andregpereira.resilientshop.userapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRegistroDto(@NotBlank(message = "Insira o nome") String nome,
		@NotBlank(message = "Insira o sobrenome") String sobrenome,
		@NotBlank(message = "Insira o CPF") @Size(message = "O CPF deve ter 11 dígitos", min = 11, max = 11) String cpf) {
}
