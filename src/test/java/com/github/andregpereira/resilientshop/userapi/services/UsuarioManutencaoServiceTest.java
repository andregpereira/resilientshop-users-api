package com.github.andregpereira.resilientshop.userapi.services;

import static com.github.andregpereira.resilientshop.userapi.UsuarioConstants.USUARIO;
import static com.github.andregpereira.resilientshop.userapi.UsuarioConstants.USUARIO_REGISTRO_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioManutencaoServiceTest {

	@InjectMocks
	private UsuarioManutencaoService manutencaoService;

	@Mock
	private UsuarioRepository usuarioRepository;

	@Test
	public void criarUsuarioComDadosValidosRetornaUsuarioDetalhesDto() {
		when(usuarioRepository.save(USUARIO)).thenReturn(USUARIO);
		UsuarioDetalhesDto sut = manutencaoService.registrar(USUARIO_REGISTRO_DTO);
		assertThat(sut).isEqualTo(USUARIO);
	}

}
