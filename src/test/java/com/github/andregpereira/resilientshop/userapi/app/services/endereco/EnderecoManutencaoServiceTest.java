package com.github.andregpereira.resilientshop.userapi.app.services.endereco;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroDto;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.EnderecoAlreadyExistsException;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.EnderecoNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.EnderecoMapper;
import com.github.andregpereira.resilientshop.userapi.cross.validations.PaisValidation;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Pais;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoDtoConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS_NOVO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.USUARIO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.USUARIO_SEM_ENDERECO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoManutencaoServiceTest {

    @InjectMocks
    private EnderecoManutencaoServiceImpl manutencaoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EnderecoMapper mapper;

    @Mock
    private PaisValidation paisValidation;

    @AfterEach
    void afterEach() {
        ENDERECO.setPadrao(true);
    }

    @BeforeEach
    void beforeEach() {
        USUARIO.setEnderecos(LISTA_ENDERECOS);
    }

    @Test
    void criarEnderecoComDadosValidosEPaisNovoRetornaEnderecoDto() {
        given(usuarioRepository.findById(anyLong())).willReturn(Optional.of(USUARIO_SEM_ENDERECO));
        given(mapper.toEndereco(any(EnderecoRegistroDto.class))).willReturn(ENDERECO_PAIS_NOVO_MAPEADO);
        given(paisValidation.validarPais(any(Pais.class))).willReturn(PAIS_NOVO);
        given(enderecoRepository.save(any(Endereco.class))).willReturn(ENDERECO_PAIS_NOVO);
        given(mapper.toEnderecoDto(any(Endereco.class))).willReturn(ENDERECO_DTO_PAIS_NOVO);
        assertThat(manutencaoService.criar(ENDERECO_REGISTRO_DTO_PAIS_NOVO)).isEqualTo(ENDERECO_DTO_PAIS_NOVO);
        then(enderecoRepository).should().save(ENDERECO_PAIS_NOVO_MAPEADO);
    }

    @Test
    void criarEnderecoComDadosValidosEPaisExistenteRetornaEnderecoDetalhesDto() {
        given(usuarioRepository.findById(anyLong())).willReturn(Optional.of(USUARIO_SEM_ENDERECO));
        given(mapper.toEndereco(any(EnderecoRegistroDto.class))).willReturn(ENDERECO_PADRAO_FALSE_MAPEADO);
        given(paisValidation.validarPais(any(Pais.class))).willReturn(PAIS);
        given(enderecoRepository.save(any(Endereco.class))).willReturn(ENDERECO);
        given(mapper.toEnderecoDto(any(Endereco.class))).willReturn(ENDERECO_DTO);
        assertThat(manutencaoService.criar(ENDERECO_REGISTRO_DTO)).isEqualTo(ENDERECO_DTO);
        then(enderecoRepository).should().save(ENDERECO_PADRAO_FALSE_MAPEADO);
    }

    @Test
    void criarEnderecoComIdUsuarioInexistenteThrowsException() {
        given(usuarioRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThatThrownBy(() -> manutencaoService.criar(ENDERECO_REGISTRO_DTO)).isInstanceOf(
                UsuarioNotFoundException.class);
        then(enderecoRepository).should(never()).save(ENDERECO_MAPEADO);
    }

    @Test
    void criarEnderecoComApelidoExistenteThrowsException() {
        given(usuarioRepository.findById(anyLong())).willReturn(Optional.of(USUARIO));
        assertThatThrownBy(() -> manutencaoService.criar(ENDERECO_REGISTRO_DTO)).isInstanceOf(
                EnderecoAlreadyExistsException.class);
        then(enderecoRepository).should(never()).save(ENDERECO_MAPEADO);
    }

    @Test
    void criarEnderecoComDadosInvalidosThrowsRuntimeException() {
        given(usuarioRepository.findById(anyLong())).willReturn(Optional.of(USUARIO));
        assertThatThrownBy(() -> manutencaoService.criar(ENDERECO_REGISTRO_DTO_INVALIDO)).isInstanceOf(
                RuntimeException.class);
        then(enderecoRepository).should(never()).save(ENDERECO_MAPEADO);
    }

    @Test
    void atualizarEnderecoComDadosValidosEPaisNovoRetornaEnderecoDto() {
        given(enderecoRepository.findById(anyLong())).willReturn(Optional.of(ENDERECO));
        given(usuarioRepository.findById(anyLong())).willReturn(Optional.of(USUARIO));
        given(mapper.toEndereco(any(EnderecoRegistroDto.class))).willReturn(ENDERECO_ATUALIZADO_PAIS_NOVO_MAPEADO);
        given(paisValidation.validarPais(any(Pais.class))).willReturn(PAIS_NOVO);
        given(enderecoRepository.save(any(Endereco.class))).willReturn(ENDERECO_ATUALIZADO_PAIS_NOVO);
        given(mapper.toEnderecoDto(any(Endereco.class))).willReturn(ENDERECO_DTO_ATUALIZADO_PAIS_NOVO);
        assertThat(manutencaoService.atualizar(1L, ENDERECO_REGISTRO_DTO_ATUALIZADO_PAIS_NOVO)).isEqualTo(
                ENDERECO_DTO_ATUALIZADO_PAIS_NOVO);
        then(enderecoRepository).should().save(ENDERECO_ATUALIZADO_PAIS_NOVO_MAPEADO);
    }

    @Test
    void atualizarEnderecoComDadosValidosEPaisExistenteRetornaEnderecoDetalhesDto() {
        given(enderecoRepository.findById(anyLong())).willReturn(Optional.of(ENDERECO));
        given(usuarioRepository.findById(anyLong())).willReturn(Optional.of(USUARIO));
        given(mapper.toEndereco(any(EnderecoRegistroDto.class))).willReturn(ENDERECO_ATUALIZADO_PADRAO_FALSE_MAPEADO);
        given(paisValidation.validarPais(any(Pais.class))).willReturn(PAIS);
        given(enderecoRepository.save(any(Endereco.class))).willReturn(ENDERECO_ATUALIZADO);
        given(mapper.toEnderecoDto(any(Endereco.class))).willReturn(ENDERECO_DTO_ATUALIZADO);
        assertThat(manutencaoService.atualizar(1L, ENDERECO_REGISTRO_DTO_ATUALIZADO)).isEqualTo(
                ENDERECO_DTO_ATUALIZADO);
        then(enderecoRepository).should().save(ENDERECO_ATUALIZADO_PADRAO_FALSE_MAPEADO);
    }

    @Test
    void atualizarEnderecoPorIdInexistenteThrowsException() {
        given(enderecoRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThatThrownBy(() -> manutencaoService.atualizar(10L, ENDERECO_REGISTRO_DTO_ATUALIZADO)).isInstanceOf(
                EnderecoNotFoundException.class);
        then(enderecoRepository).should(never()).save(ENDERECO_MAPEADO);
    }

    @Test
    void atualizarEnderecoComIdUsuarioInexistenteThrowsException() {
        given(enderecoRepository.findById(anyLong())).willReturn(Optional.of(ENDERECO));
        given(usuarioRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThatThrownBy(() -> manutencaoService.atualizar(1L, ENDERECO_REGISTRO_DTO_ATUALIZADO)).isInstanceOf(
                UsuarioNotFoundException.class);
        then(enderecoRepository).should(never()).save(ENDERECO_MAPEADO);
    }

    @Test
    void atualizarEnderecoComApelidoExistenteThrowsException() {
        given(enderecoRepository.findById(anyLong())).willReturn(Optional.of(ENDERECO));
        given(usuarioRepository.findById(anyLong())).willReturn(Optional.of(USUARIO));
        assertThatThrownBy(() -> manutencaoService.atualizar(10L, ENDERECO_REGISTRO_DTO)).isInstanceOf(
                EnderecoAlreadyExistsException.class);
        then(enderecoRepository).should(never()).save(ENDERECO_MAPEADO);
    }

    @Test
    void atualizarEnderecoComDadosInvalidosThrowsRuntimeException() {
        given(enderecoRepository.findById(anyLong())).willReturn(Optional.of(ENDERECO_ATUALIZADO));
        given(usuarioRepository.findById(anyLong())).willReturn(Optional.of(USUARIO));
        assertThatThrownBy(() -> manutencaoService.atualizar(1L, ENDERECO_REGISTRO_DTO_INVALIDO)).isInstanceOf(
                RuntimeException.class);
        then(enderecoRepository).should(never()).save(ENDERECO_MAPEADO);
    }

    @Test
    void removerEnderecoPorIdExistenteRetornaString() {
        given(enderecoRepository.findById(anyLong())).willReturn(Optional.of(ENDERECO));
        assertThat(manutencaoService.remover(1L)).isEqualTo("Endereço com id 1 removido com sucesso");
        then(enderecoRepository).should().deleteById(1L);
    }

    @Test
    void removerEnderecoPorIdInexistenteThrowsException() {
        given(enderecoRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThatThrownBy(() -> manutencaoService.remover(10L)).isInstanceOf(EnderecoNotFoundException.class);
        then(enderecoRepository).should(never()).deleteById(10L);
    }

}
