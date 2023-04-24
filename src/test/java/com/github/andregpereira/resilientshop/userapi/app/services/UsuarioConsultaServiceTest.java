package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.cross.exception.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.USUARIO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioDtoConstants.USUARIO_DETALHES_DTO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioDtoConstants.USUARIO_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioConsultaServiceTest {

    @InjectMocks
    private UsuarioConsultaServiceImpl consultaService;

    @Spy
    private UsuarioMapper mapper = Mappers.getMapper(UsuarioMapper.class);

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void consultarUsuarioPorIdExistenteRetornaUsuarioDetalhesDto() {
        given(usuarioRepository.findById(1L)).willReturn(Optional.of(USUARIO));
        UsuarioDetalhesDto sut = consultaService.consultarPorId(1L);
        assertThat(sut).isNotNull().isEqualTo(USUARIO_DETALHES_DTO);
    }

    @Test
    void consultarUsuarioPorIdInexistenteThrowsEntityNotFoundException() {
        assertThatThrownBy(() -> consultaService.consultarPorId(10L)).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
    }

    @Test
    void consultarUsuarioPorCpfExistenteEAtivoRetornaUsuarioDetalhesDto() {
        given(usuarioRepository.findByCpfAndAtivoTrue(USUARIO.getCpf())).willReturn(Optional.of(USUARIO));
        UsuarioDetalhesDto sut = consultaService.consultarPorCpf(USUARIO.getCpf());
        assertThat(sut).isNotNull().isEqualTo(USUARIO_DETALHES_DTO);
    }

    @Test
    void consultarUsuarioPorCpfInexistenteThrowsException() {
        given(usuarioRepository.findByCpfAndAtivoTrue("12345678901")).willReturn(Optional.empty());
        assertThatThrownBy(() -> consultaService.consultarPorCpf("12345678901")).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Desculpe, não foi possível encontrar um usuário ativo com este CPF. Verifique e tente novamente");
    }

    @Test
    void consultarUsuarioPorCpfInvalidoThrowsException() {
        assertThatThrownBy(() -> consultaService.consultarPorCpf(null)).isInstanceOf(
                InvalidParameterException.class).hasMessage(
                "Não foi possível realizar a busca por CPF. O CPF não foi digitado corretamente. Verifique e tente novamente");
        assertThatThrownBy(() -> consultaService.consultarPorCpf("")).isInstanceOf(
                InvalidParameterException.class).hasMessage(
                "Não foi possível realizar a busca por CPF. O CPF não foi digitado corretamente. Verifique e tente novamente");
        assertThatThrownBy(() -> consultaService.consultarPorCpf("732498")).isInstanceOf(
                InvalidParameterException.class).hasMessage(
                "Não foi possível realizar a busca por CPF. O CPF não foi digitado corretamente. Verifique e tente novamente");
        assertThatThrownBy(() -> consultaService.consultarPorCpf("11223344556677889900")).isInstanceOf(
                InvalidParameterException.class).hasMessage(
                "Não foi possível realizar a busca por CPF. O CPF não foi digitado corretamente. Verifique e tente novamente");
        assertThatThrownBy(() -> consultaService.consultarPorCpf("abc")).isInstanceOf(
                InvalidParameterException.class).hasMessage(
                "Não foi possível realizar a busca por CPF. O CPF não foi digitado corretamente. Verifique e tente novamente");
    }

    @Test
    void consultarUsuarioPorNomeExistenteRetornaUsuarioDto() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "nome");
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(USUARIO);
        Page<Usuario> pageUsuarios = new PageImpl<>(listaUsuarios, pageable, 10);
        given(usuarioRepository.findAllByNomeAndAtivoTrue("nome", "", pageable)).willReturn(pageUsuarios);
        Page<UsuarioDto> sut = consultaService.consultarPorNome("nome", null, pageable);
        assertThat(sut).isNotEmpty().hasSize(1);
        assertThat(sut.getContent().get(0)).isEqualTo(USUARIO_DTO);
    }

    @Test
    void consultarUsuarioPorSobrenomeExistenteRetornaUsuarioDto() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "nome");
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(USUARIO);
        Page<Usuario> pageUsuarios = new PageImpl<>(listaUsuarios, pageable, 10);
        given(usuarioRepository.findAllByNomeAndAtivoTrue("", "sobrenome", pageable)).willReturn(pageUsuarios);
        Page<UsuarioDto> sut = consultaService.consultarPorNome(null, "sobrenome", pageable);
        assertThat(sut).isNotEmpty().hasSize(1);
        assertThat(sut.getContent().get(0)).isEqualTo(USUARIO_DTO);
    }

    @Test
    void consultarUsuarioPorNomeOuSobrenomeVazioRetornaUsuarioDto() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "nome");
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(USUARIO);
        Page<Usuario> pageUsuarios = new PageImpl<>(listaUsuarios, pageable, 10);
        given(usuarioRepository.findAllByNomeAndAtivoTrue(anyString(), anyString(), any(Pageable.class))).willReturn(
                pageUsuarios);
        when(usuarioRepository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(pageUsuarios);
        Page<UsuarioDto> sut = consultaService.consultarPorNome("", "", pageable);
        assertThat(sut).isNotEmpty().hasSize(1);
        assertThat(sut.getContent().get(0)).isEqualTo(USUARIO_DTO);
    }

    @Test
    void consultarUsuarioPorNomeInexistenteThrowsException() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "nome");
        given(usuarioRepository.findAllByNomeAndAtivoTrue(anyString(), anyString(), any(Pageable.class))).willReturn(
                Page.empty());
        assertThatThrownBy(() -> consultaService.consultarPorNome("Fulano", "de Tal", pageable)).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Desculpe, não foi possível encontrar um usuário com este nome. Verifique e tente novamente");
    }

}
