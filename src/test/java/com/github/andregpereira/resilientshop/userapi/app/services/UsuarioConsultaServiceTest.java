package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.app.services.usuario.UsuarioConsultaServiceImpl;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

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

@ExtendWith(MockitoExtension.class)
class UsuarioConsultaServiceTest {

    @InjectMocks
    private UsuarioConsultaServiceImpl consultaService;

    @Mock
    private UsuarioMapper mapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void listarUsuariosExistentesRetornaPageUsuarioDto() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "nome");
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(USUARIO);
        Page<Usuario> pageUsuarios = new PageImpl<>(listaUsuarios, pageable, 10);
        given(usuarioRepository.findAllByAtivoTrue(pageable)).willReturn(pageUsuarios);
        given(mapper.toUsuarioDto(USUARIO)).willReturn(USUARIO_DTO);
        Page<UsuarioDto> sut = consultaService.listar(pageable);
        assertThat(sut).isNotEmpty().hasSize(1);
        assertThat(sut.getContent().get(0)).isEqualTo(USUARIO_DTO);
    }

    @Test
    void listarUsuariosInexistentesThrowsException() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        given(usuarioRepository.findAllByAtivoTrue(any(Pageable.class))).willReturn(Page.empty());
        assertThatThrownBy(() -> consultaService.listar(pageable)).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage("Opa! Não há usuários cadastrados");
    }

    @Test
    void consultarUsuarioPorIdExistenteRetornaUsuarioDetalhesDto() {
        given(usuarioRepository.findById(1L)).willReturn(Optional.of(USUARIO));
        given(mapper.toUsuarioDetalhesDto(USUARIO)).willReturn(USUARIO_DETALHES_DTO);
        UsuarioDetalhesDto sut = consultaService.consultarPorId(1L);
        assertThat(sut).isNotNull().isEqualTo(USUARIO_DETALHES_DTO);
    }

    @Test
    void consultarUsuarioPorIdInexistenteThrowsEntityNotFoundException() {
        assertThatThrownBy(() -> consultaService.consultarPorId(10L)).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Não foi possível encontrar um usuário com o id 10. Verifique e tente novamente");
    }

    @Test
    void consultarUsuarioPorCpfExistenteEAtivoRetornaUsuarioDetalhesDto() {
        given(usuarioRepository.findByCpfAndAtivoTrue(USUARIO.getCpf())).willReturn(Optional.of(USUARIO));
        given(mapper.toUsuarioDetalhesDto(USUARIO)).willReturn(USUARIO_DETALHES_DTO);
        UsuarioDetalhesDto sut = consultaService.consultarPorCpf(USUARIO.getCpf());
        assertThat(sut).isNotNull().isEqualTo(USUARIO_DETALHES_DTO);
    }

    @Test
    void consultarUsuarioPorCpfInexistenteThrowsException() {
        given(usuarioRepository.findByCpfAndAtivoTrue("12345678901")).willReturn(Optional.empty());
        assertThatThrownBy(() -> consultaService.consultarPorCpf("12345678901")).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Não foi possível encontrar um usuário ativo com o CPF 12345678901. Verifique e tente novamente");
    }

    @Test
    void consultarUsuarioPorCpfNuloThrowsException() {
        assertThatThrownBy(() -> consultaService.consultarPorCpf(null)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void consultarUsuarioPorNomeExistenteRetornaUsuarioDto() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "nome");
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(USUARIO);
        Page<Usuario> pageUsuarios = new PageImpl<>(listaUsuarios, pageable, 10);
        given(usuarioRepository.findAllByNomeAndSobrenomeAndAtivoTrue("nome", "", pageable)).willReturn(pageUsuarios);
        given(mapper.toUsuarioDto(USUARIO)).willReturn(USUARIO_DTO);
        Page<UsuarioDto> sut = consultaService.consultarPorNome("nome", "", pageable);
        assertThat(sut).isNotEmpty().hasSize(1);
        assertThat(sut.getContent().get(0)).isEqualTo(USUARIO_DTO);
    }

    @Test
    void consultarUsuarioPorNomeInexistenteThrowsException() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "nome");
        given(usuarioRepository.findAllByNomeAndSobrenomeAndAtivoTrue(anyString(), anyString(),
                any(Pageable.class))).willReturn(Page.empty());
        assertThatThrownBy(() -> consultaService.consultarPorNome("Fulano", "de Tal", pageable)).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Não foi possível encontrar um usuário ativo com o nome Fulano de Tal. Verifique e tente novamente");
    }

}
