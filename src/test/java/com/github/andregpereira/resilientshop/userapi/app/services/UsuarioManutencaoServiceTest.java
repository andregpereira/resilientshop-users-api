package com.github.andregpereira.resilientshop.userapi.app.services;

import com.github.andregpereira.resilientshop.userapi.cross.exception.UsuarioAlreadyExistsException;
import com.github.andregpereira.resilientshop.userapi.cross.exception.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.cross.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.PaisRepository;
import com.github.andregpereira.resilientshop.userapi.infra.repositories.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

    @Spy
    private UsuarioMapper mapper = Mappers.getMapper(UsuarioMapper.class);

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PaisRepository paisRepository;

    @AfterEach
    public void afterEach() {
        USUARIO.setAtivo(true);
        USUARIO_INATIVO.setAtivo(false);
    }

    @Test
    void criarUsuarioComDadosValidosEPaisNovoRetornaUsuarioDetalhesDto() {
        given(paisRepository.save(PAIS_NOVO)).willReturn(PAIS_NOVO);
        given(enderecoRepository.saveAll(LISTA_ENDERECO_PAIS_NOVO)).willReturn(LISTA_ENDERECO_PAIS_NOVO);
        given(usuarioRepository.save(USUARIO_PAIS_NOVO)).willReturn(USUARIO_PAIS_NOVO);
        assertThat(manutencaoService.registrar(USUARIO_REGISTRO_DTO_PAIS_NOVO)).isEqualTo(
                USUARIO_DETALHES_DTO_PAIS_NOVO);
        then(usuarioRepository).should().save(USUARIO_PAIS_NOVO);
    }

    @Test
    void criarUsuarioComDadosValidosEPaisExistenteRetornaUsuarioDetalhesDto() {
        given(paisRepository.findByNomeOrCodigo(PAIS.getNome(), PAIS.getCodigo())).willReturn(Optional.of(PAIS));
        given(enderecoRepository.saveAll(LISTA_ENDERECO)).willReturn(LISTA_ENDERECO);
        given(usuarioRepository.save(USUARIO)).willReturn(USUARIO);
        assertThat(manutencaoService.registrar(USUARIO_REGISTRO_DTO)).isEqualTo(USUARIO_DETALHES_DTO);
        then(usuarioRepository).should().save(USUARIO);
    }

    @Test
    void criarUsuarioComDadosInvalidosThrowsRuntimeException() {
        given(usuarioRepository.save(USUARIO_INVALIDO)).willThrow(RuntimeException.class);
        assertThatThrownBy(() -> manutencaoService.registrar(USUARIO_REGISTRO_DTO_INVALIDO)).isInstanceOf(
                RuntimeException.class);
        then(usuarioRepository).should(never()).save(USUARIO);
    }

    @Test
    void criarUsuarioComCpfRepetidoThrowsException() {
        given(usuarioRepository.existsByCpf(anyString())).willReturn(true);
        assertThatThrownBy(() -> manutencaoService.registrar(USUARIO_REGISTRO_DTO)).isInstanceOf(
                UsuarioAlreadyExistsException.class).hasMessage(
                "Não foi possível cadastrar o usuário. Já existe um usuário cadastrado com este CPF. Verifique e tente novamente");
        then(usuarioRepository).should(never()).save(USUARIO);
    }

    @Test
    void atualizarUsuarioComDadosValidosEPaisNovoRetornaUsuarioDetalhesDto() {
        given(usuarioRepository.findByIdAndAtivoTrue(USUARIO.getId())).willReturn(Optional.of(USUARIO));
        given(paisRepository.findByNomeOrCodigo(PAIS_NOVO.getNome(), PAIS_NOVO.getCodigo())).willReturn(
                Optional.empty());
        given(paisRepository.save(PAIS_NOVO)).willReturn(PAIS_NOVO);
        given(enderecoRepository.saveAll(LISTA_ENDERECO_ATUALIZADO_PAIS_NOVO)).willReturn(
                LISTA_ENDERECO_ATUALIZADO_PAIS_NOVO);
        given(usuarioRepository.save(USUARIO_ATUALIZADO_PAIS_NOVO)).willReturn(USUARIO_ATUALIZADO_PAIS_NOVO);
        assertThat(
                manutencaoService.atualizar(USUARIO.getId(), USUARIO_ATUALIZACAO_DTO_PAIS_NOVO)).isNotNull().isEqualTo(
                USUARIO_DETALHES_DTO_ATUALIZADO_PAIS_NOVO);
        then(usuarioRepository).should().save(USUARIO_ATUALIZADO_PAIS_NOVO);
    }

    @Test
    void atualizarUsuarioComDadosValidosEPaisExistenteRetornaUsuarioDetalhesDto() {
        given(usuarioRepository.findByIdAndAtivoTrue(USUARIO.getId())).willReturn(Optional.of(USUARIO));
        given(paisRepository.findByNomeOrCodigo(PAIS.getNome(), PAIS.getCodigo())).willReturn(Optional.of(PAIS));
        when(enderecoRepository.saveAll(LISTA_ENDERECO_ATUALIZADO)).thenReturn(LISTA_ENDERECO_ATUALIZADO);
        when(usuarioRepository.save(USUARIO_ATUALIZADO)).thenReturn(USUARIO_ATUALIZADO);
        assertThat(manutencaoService.atualizar(USUARIO.getId(), USUARIO_ATUALIZACAO_DTO)).isNotNull().isEqualTo(
                USUARIO_DETALHES_DTO_ATUALIZADO);
        then(usuarioRepository).should().save(USUARIO_ATUALIZADO);
    }

    @Test
    void atualizarUsuarioComDadosInvalidosThrowsRuntimeException() {
        assertThatThrownBy(() -> manutencaoService.atualizar(10L, USUARIO_ATUALIZACAO_DTO_INVALIDO)).isInstanceOf(
                RuntimeException.class);
        then(usuarioRepository).should(never()).save(USUARIO);
    }

    @Test
    void atualizarUsuarioComIdInexistenteThrowsException() {
        given(usuarioRepository.findByIdAndAtivoTrue(100L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> manutencaoService.atualizar(100L, USUARIO_ATUALIZACAO_DTO)).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
        then(usuarioRepository).should(never()).save(USUARIO);
    }

    @Test
    void desativarUsuarioAtivoComIdExistenteRetornaString() {
        given(usuarioRepository.findByIdAndAtivoTrue(10L)).willReturn(Optional.of(USUARIO));
        assertThat(manutencaoService.desativar(10L)).isEqualTo("Usuário desativado");
        then(usuarioRepository).should().findByIdAndAtivoTrue(10L);
        then(usuarioRepository).should().save(USUARIO_INATIVO);
    }

    @Test
    void desativarUsuarioInativoOuComIdInexistenteThrowsException() {
        given(usuarioRepository.findByIdAndAtivoTrue(10L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> manutencaoService.desativar(10L)).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
        then(usuarioRepository).should(never()).save(USUARIO_INATIVO);
    }

    @Test
    void reativarUsuarioInativoComIdExistenteRetornaString() {
        given(usuarioRepository.findByIdAndAtivoFalse(10L)).willReturn(Optional.of(USUARIO_INATIVO));
        assertThat(manutencaoService.reativar(10L)).isEqualTo("Usuário reativado");
        then(usuarioRepository).should().findByIdAndAtivoFalse(10L);
        then(usuarioRepository).should().save(USUARIO);
    }

    @Test
    void reativarUsuarioAtivoOuComIdInexistenteThrowsExecption() {
        given(usuarioRepository.findByIdAndAtivoFalse(10L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> manutencaoService.reativar(10L)).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Não foi possível encontrar um usuário inativo com este id. Verifique e tente novamente");
        then(usuarioRepository).should(never()).save(USUARIO);
    }

}
