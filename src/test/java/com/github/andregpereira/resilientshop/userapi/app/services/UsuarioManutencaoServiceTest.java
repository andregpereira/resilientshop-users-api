package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioAlreadyExistsException;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.cross.validations.PaisValidation;
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
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS;
import static com.github.andregpereira.resilientshop.userapi.constants.PaisConstants.PAIS_NOVO;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioDtoConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioManutencaoServiceTest {

    @InjectMocks
    private UsuarioManutencaoServiceImpl manutencaoService;

    @Mock
    private UsuarioMapper mapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PaisValidation paisValidation;

    @AfterEach
    public void afterEach() {
        USUARIO.setAtivo(true);
        USUARIO_INATIVO.setAtivo(false);
        USUARIO_MAPEADO.setAtivo(false);
        USUARIO_MAPEADO.setDataCriacao(null);
        USUARIO_MAPEADO.setDataModificacao(null);
        USUARIO_ATUALIZADO_MAPEADO.setCpf(null);
        USUARIO_ATUALIZADO_MAPEADO.setAtivo(false);
        USUARIO_ATUALIZADO_MAPEADO.setDataCriacao(null);
        USUARIO_ATUALIZADO_MAPEADO.setDataModificacao(null);
    }

    @BeforeEach
    void beforeEach() {
        USUARIO.setEnderecos(LISTA_ENDERECOS);
        USUARIO_MAPEADO.setEnderecos(LISTA_ENDERECOS_MAPEADO);
        USUARIO_MAPEADO.setId(null);
        USUARIO_ATUALIZADO_MAPEADO.setEnderecos(LISTA_ENDERECOS_ATUALIZADO_MAPEADO);
        USUARIO_PAIS_NOVO.setEnderecos(LISTA_ENDERECOS_PAIS_NOVO);
        USUARIO_ATUALIZADO.setEnderecos(LISTA_ENDERECOS_ATUALIZADO);
        USUARIO_ATUALIZADO_PAIS_NOVO.setEnderecos(LISTA_ENDERECOS_ATUALIZADO_PAIS_NOVO);
    }

    @Test
    void criarUsuarioComDadosValidosEPaisNovoRetornaUsuarioDetalhesDto() {
        given(mapper.toUsuario(USUARIO_REGISTRO_DTO_PAIS_NOVO)).willReturn(USUARIO_PAIS_NOVO);
        given(paisValidation.validarPais(PAIS_NOVO)).willReturn(PAIS_NOVO);
        given(usuarioRepository.save(USUARIO_PAIS_NOVO)).willReturn(USUARIO_PAIS_NOVO);
        given(mapper.toUsuarioDetalhesDto(USUARIO_PAIS_NOVO)).willReturn(USUARIO_DETALHES_DTO_PAIS_NOVO);
        assertThat(manutencaoService.registrar(USUARIO_REGISTRO_DTO_PAIS_NOVO)).isEqualTo(
                USUARIO_DETALHES_DTO_PAIS_NOVO);
        then(usuarioRepository).should().save(USUARIO_PAIS_NOVO);
    }

    @Test
    void criarUsuarioComDadosValidosEPaisExistenteRetornaUsuarioDetalhesDto() {
        given(mapper.toUsuario(USUARIO_REGISTRO_DTO)).willReturn(USUARIO);
        given(paisValidation.validarPais(PAIS)).willReturn(PAIS);
        given(usuarioRepository.save(USUARIO)).willReturn(USUARIO);
        given(mapper.toUsuarioDetalhesDto(USUARIO)).willReturn(USUARIO_DETALHES_DTO);
        assertThat(manutencaoService.registrar(USUARIO_REGISTRO_DTO)).isEqualTo(USUARIO_DETALHES_DTO);
        then(usuarioRepository).should().save(USUARIO);
    }

    @Test
    void criarUsuarioComDadosInvalidosThrowsRuntimeException() {
        assertThatThrownBy(() -> manutencaoService.registrar(USUARIO_REGISTRO_DTO_INVALIDO)).isInstanceOf(
                RuntimeException.class);
        then(usuarioRepository).should(never()).save(USUARIO);
    }

    @Test
    void criarUsuarioComCpfRepetidoThrowsException() {
        given(mapper.toUsuario(USUARIO_REGISTRO_DTO)).willReturn(USUARIO);
        given(usuarioRepository.existsByCpf(anyString())).willReturn(true);
        assertThatThrownBy(() -> manutencaoService.registrar(USUARIO_REGISTRO_DTO)).isInstanceOf(
                UsuarioAlreadyExistsException.class).hasMessage("Opa! Já existe um usuário cadastrado com esse CPF");
        then(usuarioRepository).should(never()).save(USUARIO);
    }

    @Test
    void atualizarUsuarioComDadosValidosEPaisNovoRetornaUsuarioDetalhesDto() {
        given(usuarioRepository.findByIdAndAtivoTrue(anyLong())).willReturn(Optional.of(USUARIO));
        willDoNothing().given(enderecoRepository).deleteByUsuarioId(anyLong());
        given(mapper.toUsuario(USUARIO_ATUALIZACAO_DTO_PAIS_NOVO)).willReturn(USUARIO_ATUALIZADO_PAIS_NOVO);
        given(paisValidation.validarPais(PAIS_NOVO)).willReturn(PAIS_NOVO);
        given(usuarioRepository.saveAndFlush(USUARIO_ATUALIZADO_PAIS_NOVO)).willReturn(USUARIO_ATUALIZADO_PAIS_NOVO);
        given(mapper.toUsuarioDetalhesDto(USUARIO_ATUALIZADO_PAIS_NOVO)).willReturn(
                USUARIO_DETALHES_DTO_ATUALIZADO_PAIS_NOVO);
        assertThat(manutencaoService.atualizar(5L, USUARIO_ATUALIZACAO_DTO_PAIS_NOVO)).isEqualTo(
                USUARIO_DETALHES_DTO_ATUALIZADO_PAIS_NOVO);
        then(usuarioRepository).should().saveAndFlush(USUARIO_ATUALIZADO_PAIS_NOVO);
    }

    @Test
    void atualizarUsuarioComDadosValidosEPaisExistenteRetornaUsuarioDetalhesDto() {
        given(usuarioRepository.findByIdAndAtivoTrue(anyLong())).willReturn(Optional.of(USUARIO));
        willDoNothing().given(enderecoRepository).deleteByUsuarioId(anyLong());
        given(mapper.toUsuario(USUARIO_ATUALIZACAO_DTO)).willReturn(USUARIO_ATUALIZADO);
        given(paisValidation.validarPais(PAIS)).willReturn(PAIS);
        given(usuarioRepository.saveAndFlush(USUARIO_ATUALIZADO)).willReturn(USUARIO_ATUALIZADO);
        given(mapper.toUsuarioDetalhesDto(USUARIO_ATUALIZADO)).willReturn(USUARIO_DETALHES_DTO_ATUALIZADO);
        assertThat(manutencaoService.atualizar(5L, USUARIO_ATUALIZACAO_DTO)).isEqualTo(USUARIO_DETALHES_DTO_ATUALIZADO);
        then(usuarioRepository).should().saveAndFlush(USUARIO_ATUALIZADO);
    }

    @Test
    void atualizarUsuarioComDadosInvalidosThrowsRuntimeException() {
        assertThatThrownBy(() -> manutencaoService.atualizar(10L, USUARIO_ATUALIZACAO_DTO_INVALIDO)).isInstanceOf(
                RuntimeException.class);
        then(usuarioRepository).should(never()).save(USUARIO);
    }

    @Test
    void atualizarUsuarioComIdInexistenteThrowsException() {
        given(usuarioRepository.findByIdAndAtivoTrue(anyLong())).willReturn(Optional.empty());
        assertThatThrownBy(() -> manutencaoService.atualizar(100L, USUARIO_ATUALIZACAO_DTO)).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Não foi possível encontrar um usuário ativo com o id 100. Verifique e tente novamente");
        then(usuarioRepository).should(never()).save(USUARIO);
    }

    @Test
    void desativarUsuarioAtivoComIdExistenteRetornaString() {
        given(usuarioRepository.findByIdAndAtivoTrue(anyLong())).willReturn(Optional.of(USUARIO));
        given(usuarioRepository.save(USUARIO)).willReturn(USUARIO_INATIVO);
        assertThat(manutencaoService.desativar(10L)).isEqualTo("Usuário com id 10 desativado com sucesso");
        then(usuarioRepository).should().save(USUARIO);
    }

    @Test
    void desativarUsuarioInativoOuComIdInexistenteThrowsException() {
        given(usuarioRepository.findByIdAndAtivoTrue(anyLong())).willReturn(Optional.empty());
        assertThatThrownBy(() -> manutencaoService.desativar(10L)).isInstanceOf(UsuarioNotFoundException.class)
                .hasMessage("Não foi possível encontrar um usuário ativo com o id 10. Verifique e tente novamente");
        then(usuarioRepository).should(never()).save(USUARIO_INATIVO);
    }

    @Test
    void reativarUsuarioInativoComIdExistenteRetornaString() {
        given(usuarioRepository.findByIdAndAtivoFalse(anyLong())).willReturn(Optional.of(USUARIO_INATIVO));
        given(usuarioRepository.save(USUARIO_INATIVO)).willReturn(USUARIO);
        assertThat(manutencaoService.reativar(15L)).isEqualTo("Usuário com id 15 reativado com sucesso");
        then(usuarioRepository).should().save(USUARIO_INATIVO);
    }

    @Test
    void reativarUsuarioAtivoOuComIdInexistenteThrowsExecption() {
        given(usuarioRepository.findByIdAndAtivoFalse(anyLong())).willReturn(Optional.empty());
        assertThatThrownBy(() -> manutencaoService.reativar(20L)).isInstanceOf(UsuarioNotFoundException.class)
                .hasMessage("Não foi possível encontrar um usuário inativo com o id 20. Verifique e tente novamente");
        then(usuarioRepository).should(never()).save(USUARIO);
    }

}
