package com.github.andregpereira.resilientshop.userapi.services;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioDtoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.repositories.PaisRepository;
import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioManutencaoServiceTest {

	@InjectMocks
	private UsuarioManutencaoService manutencaoService;

	@Mock
	private UsuarioRepository usuarioRepository;

	@Mock
	private EnderecoRepository enderecoRepository;

	@Mock
	private PaisRepository paisRepository;

	@Test
	public void criarUsuarioComDadosValidosRetornaUsuarioDetalhesDto() {
		when(paisRepository.save(PAIS)).thenReturn(PAIS);
		when(enderecoRepository.save(ENDERECO)).thenReturn(ENDERECO);
		when(usuarioRepository.save(USUARIO)).thenReturn(USUARIO);
		UsuarioDetalhesDto sut = manutencaoService.registrar(USUARIO_REGISTRO_DTO);
		System.out.println(sut.id());
		assertThat(sut).isEqualTo(USUARIO_DETALHES_DTO);
	}

	@Test
	public void criarUsuarioComDadosInvalidosThrowsRuntimeException() {
//		when(paisRepository.save(PAIS_INVALIDO)).thenReturn(PAIS_INVALIDO).thenThrow(RuntimeException.class);
//		when(enderecoRepository.save(ENDERECO_INVALIDO)).thenReturn(ENDERECO_INVALIDO).thenThrow(RuntimeException.class);
		when(usuarioRepository.save(USUARIO_VAZIO)).thenReturn(USUARIO_VAZIO).thenThrow(RuntimeException.class);
	}

}
