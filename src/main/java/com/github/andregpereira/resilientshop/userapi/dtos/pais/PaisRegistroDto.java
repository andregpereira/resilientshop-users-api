package com.github.andregpereira.resilientshop.userapi.dtos.pais;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PaisRegistroDto(@NotBlank(message = "Insira o nome do país") String nome,
		@NotBlank(message = "Insira o código do país") @Size(min = 2, max = 3, message = "O código do país deve ter entre 2 e 3 caracteres") String codigo) {

}
