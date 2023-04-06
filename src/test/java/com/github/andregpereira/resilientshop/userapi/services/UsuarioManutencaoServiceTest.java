package com.github.andregpereira.resilientshop.userapi.services;

import com.github.andregpereira.resilientshop.userapi.infra.exception.UsuarioAlreadyExistsException;
import com.github.andregpereira.resilientshop.userapi.infra.exception.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.mappers.UsuarioMapper;
import com.github.andregpereira.resilientshop.userapi.repositories.EnderecoRepository;
import com.github.andregpereira.resilientshop.userapi.repositories.PaisRepository;
import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;
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
public class UsuarioManutencaoServiceTest {

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

    @Test
    public void criarUsuarioComDadosValidosRetornaUsuarioDetalhesDto() {
        given(paisRepository.save(PAIS)).willReturn(PAIS);
        given(enderecoRepository.save(ENDERECO)).willReturn(ENDERECO);
        given(usuarioRepository.save(USUARIO)).willReturn(USUARIO);
        assertThat(manutencaoService.registrar(USUARIO_REGISTRO_DTO)).isEqualTo(USUARIO_DETALHES_DTO);
        then(usuarioRepository).should().save(USUARIO);
    }

    @Test
    public void criarUsuarioComDadosValidosEPaisExistenteRetornaUsuarioDetalhesDto() {
        given(paisRepository.findByNomeOrCodigo(PAIS.getNome(), PAIS.getCodigo())).willReturn(Optional.of(PAIS));
        given(enderecoRepository.save(ENDERECO)).willReturn(ENDERECO);
        given(usuarioRepository.save(USUARIO)).willReturn(USUARIO);
        assertThat(manutencaoService.registrar(USUARIO_REGISTRO_DTO)).isEqualTo(USUARIO_DETALHES_DTO);
        then(usuarioRepository).should().save(USUARIO);
    }

    @Test
    public void criarUsuarioComDadosInvalidosThrowsRuntimeException() {
        given(usuarioRepository.save(USUARIO_INVALIDO)).willThrow(RuntimeException.class);
        assertThatThrownBy(() -> manutencaoService.registrar(USUARIO_REGISTRO_DTO_INVALIDO)).isInstanceOf(
                RuntimeException.class);
        then(usuarioRepository).should(never()).save(USUARIO);
    }

    @Test
    public void criarUsuarioComCpfRepetidoThrowsException() {
        given(usuarioRepository.existsByCpf(anyString())).willReturn(true);
        assertThatThrownBy(() -> manutencaoService.registrar(USUARIO_REGISTRO_DTO)).isInstanceOf(
                UsuarioAlreadyExistsException.class).hasMessage(
                "Não foi possível cadastrar o usuário. Já existe um usuário cadastrado com este CPF. Verifique e tente novamente");
        then(usuarioRepository).should(never()).save(USUARIO);
    }

    @Test
    public void atualizarUsuarioComDadosValidosEPaisNovoRetornaUsuarioDetalhesDto() {
        given(usuarioRepository.findByIdAndAtivoTrue(USUARIO.getId())).willReturn(Optional.of(USUARIO));
        given(paisRepository.findByNomeOrCodigo(PAIS_NOVO.getNome(), PAIS_NOVO.getCodigo())).willReturn(
                Optional.empty());
        given(paisRepository.save(PAIS_NOVO)).willReturn(PAIS_NOVO);
        given(enderecoRepository.save(ENDERECO_ATUALIZADO_PAIS_NOVO)).willReturn(ENDERECO_ATUALIZADO_PAIS_NOVO);
        given(usuarioRepository.save(USUARIO_ATUALIZADO_PAIS_NOVO)).willReturn(USUARIO_ATUALIZADO_PAIS_NOVO);
        assertThat(
                manutencaoService.atualizar(USUARIO.getId(), USUARIO_ATUALIZACAO_DTO_PAIS_NOVO)).isNotNull().isEqualTo(
                USUARIO_DETALHES_DTO_ATUALIZADO_PAIS_NOVO);
        then(usuarioRepository).should().save(USUARIO_ATUALIZADO_PAIS_NOVO);
    }

    @Test
    public void atualizarUsuarioComDadosValidosEPaisExistenteRetornaUsuarioDetalhesDto() {
        given(usuarioRepository.findByIdAndAtivoTrue(USUARIO.getId())).willReturn(Optional.of(USUARIO));
        given(paisRepository.findByNomeOrCodigo(PAIS.getNome(), PAIS.getCodigo())).willReturn(Optional.of(PAIS));
        when(enderecoRepository.save(ENDERECO_ATUALIZADO)).thenReturn(ENDERECO_ATUALIZADO);
        when(usuarioRepository.save(USUARIO_ATUALIZADO)).thenReturn(USUARIO_ATUALIZADO);
        assertThat(manutencaoService.atualizar(USUARIO.getId(), USUARIO_ATUALIZACAO_DTO)).isNotNull().isEqualTo(
                USUARIO_DETALHES_DTO_ATUALIZADO);
        then(usuarioRepository).should().save(USUARIO_ATUALIZADO);
    }

    @Test
    public void atualizarUsuarioComDadosInvalidosThrowsRuntimeException() {
        assertThatThrownBy(() -> manutencaoService.atualizar(anyLong(), USUARIO_ATUALIZACAO_DTO_INVALIDO)).isInstanceOf(
                RuntimeException.class);
        then(usuarioRepository).should(never()).save(USUARIO);
    }

    @Test
    public void atualizarUsuarioComIdInexistenteThrowsException() {
        given(usuarioRepository.findByIdAndAtivoTrue(USUARIO.getId())).willReturn(Optional.empty());
        assertThatThrownBy(() -> manutencaoService.atualizar(USUARIO.getId(), USUARIO_ATUALIZACAO_DTO)).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
        then(usuarioRepository).should(never()).save(USUARIO);
    }

    @Test
    public void desativarUsuarioAtivoComIdExistenteRetornaString() {
        given(usuarioRepository.findByIdAndAtivoTrue(10L)).willReturn(Optional.of(USUARIO));
        doNothing().when(usuarioRepository).deactivateById(10L);
        assertThat(manutencaoService.desativar(10L)).isEqualTo("Usuário desativado");
        then(usuarioRepository).should().findByIdAndAtivoTrue(10L);
        then(usuarioRepository).should().deactivateById(10L);
    }

    @Test
    public void desativarUsuarioInativoOuComIdInexistenteThrowsException() {
        given(usuarioRepository.findByIdAndAtivoTrue(10L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> manutencaoService.desativar(10L)).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Não foi possível encontrar um usuário ativo com este id. Verifique e tente novamente");
        then(usuarioRepository).should(never()).deactivateById(10L);
    }

    @Test
    public void reativarUsuarioInativoComIdExistenteRetornaString() {
//        given(usuarioRepository.findByIdAndAtivoFalse(10L)).willReturn(Optional.of(USUARIO));
//        assertThat(manutencaoService.reativar(10L)).isEqualTo("Usuário reativado");
        given(usuarioRepository.findByIdAndAtivoFalse(10L)).willReturn(Optional.of(USUARIO));
        doNothing().when(usuarioRepository).activateById(10L);
        assertThat(manutencaoService.reativar(10L)).isEqualTo("Usuário reativado");
        then(usuarioRepository).should().findByIdAndAtivoFalse(10L);
        then(usuarioRepository).should().activateById(10L);
    }

    @Test
    public void reativarUsuarioAtivoOuComIdInexistenteThrowsExecption() {
        given(usuarioRepository.findByIdAndAtivoFalse(10L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> manutencaoService.reativar(10L)).isInstanceOf(
                UsuarioNotFoundException.class).hasMessage(
                "Não foi possível encontrar um usuário inativo com este id. Verifique e tente novamente");
        then(usuarioRepository).should(never()).activateById(10L);
    }

}
