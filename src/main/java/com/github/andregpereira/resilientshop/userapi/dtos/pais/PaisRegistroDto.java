package com.github.andregpereira.resilientshop.userapi.dtos.pais;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PaisRegistroDto(
		@NotBlank(message = "Insira o nome do país.") @Size(message = "O nome do país deve ter no máximo 45 caracteres.", max = 45) String nome,
		@NotBlank(message = "Insira o código do país.") @Size(min = 3, max = 4, message = "O código do país deve ter entre 3 e 4 caracteres.") @Pattern(message = "Formato inválido. Formatos aceitos: (+xx) e (+xxx)", regexp = "[+]\\d{3}") String codigo) {
}
