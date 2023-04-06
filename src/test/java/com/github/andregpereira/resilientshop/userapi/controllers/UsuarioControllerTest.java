package com.github.andregpereira.resilientshop.userapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.infra.exception.UsuarioAlreadyExistsException;
import com.github.andregpereira.resilientshop.userapi.infra.exception.UsuarioNotFoundException;
import com.github.andregpereira.resilientshop.userapi.services.UsuarioConsultaService;
import com.github.andregpereira.resilientshop.userapi.services.UsuarioManutencaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioDtoConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @MockBean
    private UsuarioManutencaoService manutencaoService;

    @MockBean
    private UsuarioConsultaService consultaService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void criarUsuarioComDadosValidosRetornaCreated() throws Exception {
        given(manutencaoService.registrar(USUARIO_REGISTRO_DTO)).willReturn(USUARIO_DETALHES_DTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios").content(
                objectMapper.writeValueAsString(USUARIO_REGISTRO_DTO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpectAll(jsonPath("$").exists(),
                jsonPath("$.nome").value(USUARIO_DETALHES_DTO.nome()),
                jsonPath("$.sobrenome").value(USUARIO_DETALHES_DTO.sobrenome()),
                jsonPath("$.telefone").value(USUARIO_DETALHES_DTO.telefone()),
                jsonPath("$.dataCriacao").value(USUARIO_DETALHES_DTO.dataCriacao().toString()),
                jsonPath("$.dataModificacao").value(USUARIO_DETALHES_DTO.dataModificacao().toString()));
    }

    @Test
    public void criarUsuarioComDadosInvalidosRetornaUnprocessableEntity() throws Exception {
        mockMvc.perform(post("/usuarios").content(
                objectMapper.writeValueAsString(USUARIO_REGISTRO_DTO_INVALIDO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void criarUsuarioComCpfExistenteRetornaConflict() throws Exception {
        given(manutencaoService.registrar(any())).willThrow(UsuarioAlreadyExistsException.class);
        mockMvc.perform(post("/usuarios").content(objectMapper.writeValueAsString(USUARIO_REGISTRO_DTO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
    }

    @Test
    public void atualizarUsuarioComDadosValidosRetornaUsuarioDetalhesDto() throws Exception {
        given(manutencaoService.atualizar(1L, USUARIO_ATUALIZACAO_DTO)).willReturn(USUARIO_DETALHES_DTO_ATUALIZADO);
        mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/1").content(
                objectMapper.writeValueAsString(USUARIO_ATUALIZACAO_DTO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpectAll(jsonPath("$").exists(),
                jsonPath("$.nome").value(USUARIO_DETALHES_DTO_ATUALIZADO.nome()),
                jsonPath("$.sobrenome").value(USUARIO_DETALHES_DTO_ATUALIZADO.sobrenome()),
                jsonPath("$.telefone").value(USUARIO_DETALHES_DTO_ATUALIZADO.telefone()),
                jsonPath("$.dataCriacao").value(USUARIO_DETALHES_DTO_ATUALIZADO.dataCriacao().toString()),
                jsonPath("$.dataModificacao").value(USUARIO_DETALHES_DTO_ATUALIZADO.dataModificacao().toString()));
    }

    @Test
    public void atualizarUsuarioComDadosInvalidosRetornaUnprocessableEntity() throws Exception {
        mockMvc.perform(put("/usuarios/1").content(
                objectMapper.writeValueAsString(USUARIO_ATUALIZACAO_DTO_INVALIDO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void atualizarUsuarioInexistenteRetornaNotFound() throws Exception {
        given(manutencaoService.atualizar(1L, USUARIO_ATUALIZACAO_DTO)).willThrow(UsuarioNotFoundException.class);
        mockMvc.perform(put("/usuarios/1").content(
                objectMapper.writeValueAsString(USUARIO_ATUALIZACAO_DTO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void desativarUsuarioAtivoPorIdExistenteRetornaOk() throws Exception {
        given(manutencaoService.desativar(10L)).willReturn("Usuário desativado");
        mockMvc.perform(delete("/usuarios/10")).andExpect(status().isOk()).andExpectAll(
                jsonPath("$").value("Usuário desativado"));
    }

    @Test
    public void desativarUsuarioPorIdInexistenteOuInativoRetornaNotFound() throws Exception {
        given(manutencaoService.desativar(10L)).willThrow(UsuarioNotFoundException.class);
        mockMvc.perform(delete("/usuarios/10")).andExpectAll(status().isNotFound());
    }

    @Test
    public void reativarUsuarioInativoPorIdExistenteRetornaOk() throws Exception {
        given(manutencaoService.reativar(10L)).willReturn("Usuário reativado");
        mockMvc.perform(patch("/usuarios/reativar/10")).andExpectAll(status().isOk());
    }

    @Test
    public void reativarUsuarioAtivoOuPorIdInexistenteRetornaNotFound() throws Exception {
        given(manutencaoService.reativar(10L)).willThrow(UsuarioNotFoundException.class);
        mockMvc.perform(patch("/usuarios/reativar/10")).andExpect(status().isNotFound());
    }

    @Test
    public void consultarUsuarioPorIdExistenteRetornaUsuarioDetalhesDto() throws Exception {
        given(consultaService.consultarPorId(1L)).willReturn(USUARIO_DETALHES_DTO);
        mockMvc.perform(get("/usuarios/1")).andExpect(status().isOk()).andExpectAll(jsonPath("$").exists(),
                jsonPath("$.nome").value(USUARIO_DETALHES_DTO.nome()),
                jsonPath("$.sobrenome").value(USUARIO_DETALHES_DTO.sobrenome()),
                jsonPath("$.telefone").value(USUARIO_DETALHES_DTO.telefone()),
                jsonPath("$.dataCriacao").value(USUARIO_DETALHES_DTO.dataCriacao().toString()),
                jsonPath("$.dataModificacao").value(USUARIO_DETALHES_DTO.dataModificacao().toString()));
        ;
    }

    @Test
    public void consultarUsuarioPorIdInexistenteRetornaNotFound() throws Exception {
        given(consultaService.consultarPorId(10L)).willThrow(UsuarioNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/10")).andExpect(status().isNotFound());
    }

    @Test
    public void consultarUsuarioPorIdInvalidoThrowsException() throws Exception {
//        given(consultaService.consultarPorId(10L)).willThrow(MethodArgumentTypeMismatchException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/a")).andExpectAll(status().isBadRequest(),
                jsonPath("$").value("Parâmetro inválido. Verifique e tente novamente"));
    }

    @Test
    public void consultarUsuarioPorCpfExistenteRetornaUsuarioDetalhesDto() throws Exception {
        given(consultaService.consultarPorCpf(USUARIO_DETALHES_DTO.cpf())).willReturn(USUARIO_DETALHES_DTO);
        mockMvc.perform(get("/usuarios/cpf").param("cpf", USUARIO_DETALHES_DTO.cpf())).andDo(print()).andExpect(
                status().isOk()).andExpectAll(jsonPath("$").exists(),
                jsonPath("$.nome").value(USUARIO_DETALHES_DTO.nome()),
                jsonPath("$.sobrenome").value(USUARIO_DETALHES_DTO.sobrenome()),
                jsonPath("$.telefone").value(USUARIO_DETALHES_DTO.telefone()),
                jsonPath("$.dataCriacao").value(USUARIO_DETALHES_DTO.dataCriacao().toString()),
                jsonPath("$.dataModificacao").value(USUARIO_DETALHES_DTO.dataModificacao().toString()));
    }

    @Test
    public void consultarUsuarioPorCpfInexistenteThrowsException() throws Exception {
        given(consultaService.consultarPorCpf(USUARIO_DETALHES_DTO.cpf())).willThrow(UsuarioNotFoundException.class);
        mockMvc.perform(get("/usuarios/cpf").param("cpf", USUARIO_DETALHES_DTO.cpf())).andExpectAll(
                status().isNotFound(), jsonPath("$").doesNotExist());
    }

    @Test
    public void consultarUsuarioPorCpfInvalidoThrowsException() throws Exception {
        given(consultaService.consultarPorCpf("")).willThrow(InvalidParameterException.class);
        mockMvc.perform(get("/usuarios/cpf").param("cpf", "")).andExpect(status().isBadRequest());
    }

    @Test
    public void consultarUsuariosExistentesRetornaPageUsuarioDto() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        List<UsuarioDto> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(USUARIO_DTO);
        listaUsuarios.add(USUARIO_DTO_ATUALIZADO);
        Page<UsuarioDto> pageUsuarios = new PageImpl<>(listaUsuarios, pageable, 10);
        given(consultaService.consultarPorNome(null, null, pageable)).willReturn(pageUsuarios);
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/nome")).andExpect(status().isOk()).andExpectAll(
                jsonPath("$").exists(), jsonPath("$.empty").value(false), jsonPath("$.numberOfElements").value(2));
    }

    @Test
    public void consultarUsuarioPorNomeExistenteRetornaUsuarioDto() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        List<UsuarioDto> listaUsuarios = new ArrayList<>();
        Page<UsuarioDto> pageUsuarios = new PageImpl<>(listaUsuarios, pageable, 10);
        given(consultaService.consultarPorNome("", "", pageable)).willReturn(pageUsuarios);
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/nome").param("nome", "")).andExpect(status().isOk());
    }

    @Test
    public void consultarUsuarioPorNomeInexistenteRetornaNotFound() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        given(consultaService.consultarPorNome("nome", null, pageable)).willThrow(UsuarioNotFoundException.class);
        mockMvc.perform(get("/usuarios/nome").param("nome", "nome")).andExpect(status().isNotFound());
    }

    @Test
    public void criarUsuarioComRequestBodyNuloRetornaBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios").content(
                objectMapper.writeValueAsString(null)).contentType(MediaType.APPLICATION_JSON)).andExpectAll(
                status().isBadRequest(),
                jsonPath("$").value("Informação inválida. Verifique os dados e tente novamente"));
    }

    @Test
    public void inserirParametroCpfInvalidoThrowsException() throws Exception {
        mockMvc.perform(get("/usuarios/cpf")).andExpect(status().isBadRequest()).andExpectAll(
                jsonPath("$.campo").value("cpf"), jsonPath("$.mensagem").value("O campo cpf é obrigatório"));
    }

}
