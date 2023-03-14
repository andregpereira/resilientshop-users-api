package com.github.andregpereira.resilientshop.userapi.dtos.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record EnderecoRegistroDto(
		@NotBlank(message = "Insira o CEP") @Pattern(regexp = "(\\d{5})-?(\\d{3})", message = "CEP inválido. Formato: xxxxx-xxx") String cep,
		@NotBlank(message = "Insira o estado") String estado, @NotBlank(message = "Insira a cidade") String cidade,
		@NotBlank(message = "Insira a rua") String rua, @NotNull(message = "Insira o número") Integer numero,
		String complemento, @NotNull(message = "Insira o país") @Positive(message = "ID inválido") Long paisId) {

}
