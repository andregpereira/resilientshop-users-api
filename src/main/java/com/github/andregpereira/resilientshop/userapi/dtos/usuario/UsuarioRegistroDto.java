package com.github.andregpereira.resilientshop.userapi.dtos.usuario;

import com.github.andregpereira.resilientshop.userapi.dtos.endereco.EnderecoRegistroDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioRegistroDto(
		@NotBlank(message = "Insira o nome") @Size(message = "O nome deve ter entre 2 e 45 caracteres", min = 2, max = 45) String nome,
		@NotBlank(message = "Insira o sobrenome") @Size(message = "O sobrenome deve ter entre 2 e 250 caracteres", min = 2, max = 250) String sobrenome,
		@NotBlank(message = "Insira o CPF") @Size(message = "O CPF deve ter 11 dígitos", min = 11, max = 14) @Pattern(message = "Formato de CPF inválido. Formatos aceitos: xxx.xxx.xxx-xx, xxxxxxxxx-xx ou xxxxxxxxxxx", regexp = "^(\\d{3}[.]\\d{3}[.]\\d{3}[-]\\d{2})|(\\d{9}[-]\\d{2})|(\\d{11})$") String cpf,
		@NotBlank(message = "Insira o telefone") @Size(message = "O telefone deve ter entre 8 e 15 dígitos", min = 8, max = 20) @Pattern(message = "Formato do telefone inválido. Por favor, informe um telefone no seguinte formato +xxx (xx) xxxxx-xxxx", regexp = "^[+]\\d{2,3} [(]\\d{2}[)] \\d{5}[-]\\d{4}$") String telefone
//		@NotNull(message = "Insira o endereço") @Valid EnderecoRegistroDto endereco
) {
}
