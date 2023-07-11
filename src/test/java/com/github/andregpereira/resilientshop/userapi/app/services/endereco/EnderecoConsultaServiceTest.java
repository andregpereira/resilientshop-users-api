package com.github.andregpereira.resilientshop.userapi.app.services.endereco;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.EnderecoNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.EnderecoMapper;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoDtoConstants.ENDERECO_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EnderecoConsultaServiceTest {

    @InjectMocks
    private EnderecoConsultaServiceImpl consultaService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private EnderecoMapper mapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void consultarEnderecoPorIdExistenteRetornaEnderecoDto() {
        given(enderecoRepository.findById(1L)).willReturn(Optional.of(ENDERECO));
        given(mapper.toEnderecoDto(ENDERECO)).willReturn(ENDERECO_DTO);
        EnderecoDto sut = consultaService.consultarPorId(1L);
        assertThat(sut).isEqualTo(ENDERECO_DTO);
    }

    @Test
    void consultarEnderecoPorIdInexistenteThrowsException() {
        assertThatThrownBy(() -> consultaService.consultarPorId(10L)).isInstanceOf(
                EnderecoNotFoundException.class).hasMessage(
                "Não foi possível encontrar um endereço com o id 10. Verifique e tente novamente");
    }

    @Test
    void consultarEnderecosPorIdUsuarioExistenteRetornaPageEnderecoDto() {
        given(usuarioRepository.existsById(1L)).willReturn(true);
        given(enderecoRepository.findAllByUsuarioId(anyLong(), any(Pageable.class))).willReturn(PAGE_ENDERECOS);
        given(mapper.toEnderecoDto(ENDERECO)).willReturn(ENDERECO_DTO);
        Page<EnderecoDto> sut = consultaService.consultarPorIdUsuario(1L, PAGEABLE_ENDERECOS);
        assertThat(sut).hasSize(1);
        assertThat(sut.getContent().get(0)).isEqualTo(ENDERECO_DTO);
    }

    @Test
    void consultarEnderecosPorIdUsuarioInexistenteThrowsException() {
        given(usuarioRepository.existsById(10L)).willReturn(false);
        assertThatThrownBy(() -> consultaService.consultarPorIdUsuario(10L, PAGEABLE_ENDERECOS)).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Não foi possível encontrar um usuário com o id 10. Verifique e tente novamente");
    }

    @Test
    void consultarEnderecosInxistentesThrowsException() {
        given(usuarioRepository.existsById(1L)).willReturn(true);
        given(enderecoRepository.findAllByUsuarioId(anyLong(), any(Pageable.class))).willReturn(Page.empty());
        assertThatThrownBy(() -> consultaService.consultarPorIdUsuario(1L, PAGEABLE_ENDERECOS)).isInstanceOf(
                EnderecoNotFoundException.class).hasMessage("Ops! O usuário não possui endereços cadastrados");
    }

    @Test
    void consultarEnderecoPorApelidoExistenteRetornaEnderecoDto() {
        given(usuarioRepository.existsById(1L)).willReturn(true);
        given(enderecoRepository.findByApelidoAndUsuarioId("apelido", 1L)).willReturn(Optional.of(ENDERECO));
        given(mapper.toEnderecoDto(ENDERECO)).willReturn(ENDERECO_DTO);
        EnderecoDto sut = consultaService.consultarPorApelido(1L, "apelido");
        assertThat(sut).isEqualTo(ENDERECO_DTO);
    }

    @Test
    void consultarEnderecoPorApelidoPorIdUsuarioInexistenteThrowsException() {
        given(usuarioRepository.existsById(10L)).willReturn(false);
        assertThatThrownBy(() -> consultaService.consultarPorApelido(10L, "casa")).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Não foi possível encontrar um usuário com o id 10. Verifique e tente novamente");
    }

    @Test
    void consultarEnderecoPorApelidoInexistenteThrowsException() {
        given(usuarioRepository.existsById(1L)).willReturn(true);
        given(enderecoRepository.findByApelidoAndUsuarioId("casa", 1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> consultaService.consultarPorApelido(1L, "casa")).isInstanceOf(
                EnderecoNotFoundException.class).hasMessage(
                "Não foi possível encontrar um endereço com o apelido casa. Verifique e tente novamente");
    }

    @Test
    void consultarEnderecoPorApelidoNuloThrowsException() {
        assertThatThrownBy(() -> consultaService.consultarPorApelido(anyLong(), anyString())).isInstanceOf(
                RuntimeException.class);
    }

}
