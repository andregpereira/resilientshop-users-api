package com.github.andregpereira.resilientshop.userapi.dtos.endereco;

import com.github.andregpereira.resilientshop.userapi.dtos.pais.PaisRegistroDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRegistroDto(
		@NotBlank(message = "Insira o CEP.") @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido. Formato: xxxxx-xxx") String cep,
		@NotBlank(message = "Insira o estado.") String estado, @NotBlank(message = "Insira a cidade.") String cidade,
		@NotBlank(message = "Insira o bairro.") @Size(message = "O bairro deve ter no máximo 45 caracteres.", max = 45) String bairro,
		@NotBlank(message = "Insira a rua.") @Size(message = "A rua deve ter no máximo 45 caracteres.", max = 45) String rua,
		@NotNull(message = "Insira o número.") @Size(message = "O número deve ter no máximo 10 carecteres.", max = 10) String numero,
		@Size(message = "O complemento deve ter no máximo 45 caracteres.", max = 45) String complemento,
		@NotNull(message = "Insira o país.") @Valid PaisRegistroDto pais) {
}
