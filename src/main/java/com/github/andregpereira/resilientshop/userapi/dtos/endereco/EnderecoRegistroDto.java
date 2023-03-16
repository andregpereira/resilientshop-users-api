package com.github.andregpereira.resilientshop.userapi.dtos.endereco;

import com.github.andregpereira.resilientshop.userapi.dtos.pais.PaisRegistroDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EnderecoRegistroDto(
		@NotBlank(message = "Insira o CEP") @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido. Formato: xxxxx-xxx") String cep,
		@NotBlank(message = "Insira o estado") String estado, @NotBlank(message = "Insira a cidade") String cidade,
		@NotBlank(message = "Insira a rua") String rua, @NotNull(message = "Insira o número") Integer numero,
		String complemento,
		@NotNull(message = "Insira o país") @NotNull(message = "Insira o país") @Valid PaisRegistroDto pais) {

}
