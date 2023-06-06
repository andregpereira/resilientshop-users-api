package com.github.andregpereira.resilientshop.userapi.app.dto.endereco;

import com.github.andregpereira.resilientshop.userapi.app.dto.pais.PaisRegistroDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRegistroDto(@NotBlank(message = "O apelido é obrigatório") String apelido,
        @NotBlank(message = "Insira o CEP.") @Pattern(regexp = "\\d{5}-\\d{3}",
                message = "CEP inválido. Formato: xxxxx-xxx") String cep,
        @NotBlank(message = "Insira o estado.") String estado,
        @NotBlank(message = "Insira a cidade.") String cidade,
        @NotBlank(message = "Insira o bairro.") @Size(message = "O bairro deve ter no máximo 45 caracteres.",
                max = 45) String bairro,
        @NotBlank(message = "Insira a rua.") @Size(message = "A rua deve ter no máximo 45 caracteres.",
                max = 45) String rua,
        @NotBlank(message = "Insira o número.") @Size(message = "O número deve ter no máximo 10 carecteres.",
                max = 10) String numero,
        @Size(message = "O complemento deve ter no máximo 45 caracteres.", max = 45) String complemento,
        boolean padrao,
        @NotNull(message = "O id do usuário é obrigatório") Long idUsuario,
        @NotNull(message = "Insira o país.") @Valid PaisRegistroDto pais) {

}
