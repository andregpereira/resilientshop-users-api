package com.github.andregpereira.resilientshop.userapi.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.app.services.usuario.UsuarioConsultaService;
import com.github.andregpereira.resilientshop.userapi.app.services.usuario.UsuarioManutencaoService;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioAlreadyExistsException;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.UsuarioNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioDtoConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @MockBean
    private UsuarioManutencaoService manutencaoService;

    @MockBean
    private UsuarioConsultaService consultaService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criarUsuarioComDadosValidosRetornaCreated() throws Exception {
        given(manutencaoService.registrar(USUARIO_REGISTRO_DTO)).willReturn(USUARIO_DETALHES_DTO);
        mockMvc.perform(post("/usuarios").content(objectMapper.writeValueAsString(USUARIO_REGISTRO_DTO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpectAll(
                jsonPath("$.nome").value(USUARIO_DETALHES_DTO.nome()),
                jsonPath("$.apelido").value(USUARIO_DETALHES_DTO.nomeSocial()),
                jsonPath("$.celular").value(USUARIO_DETALHES_DTO.celular()), jsonPath("$.dataCriacao").value(
                        USUARIO_DETALHES_DTO.dataModificacao().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))),
                jsonPath("$.dataModificacao").value(
                        USUARIO_DETALHES_DTO.dataModificacao().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))));
    }

    @Test
    void criarUsuarioComDadosInvalidosRetornaUnprocessableEntity() throws Exception {
        mockMvc.perform(post("/usuarios").content(
                objectMapper.writeValueAsString(USUARIO_REGISTRO_DTO_INVALIDO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void criarUsuarioComCpfExistenteRetornaConflict() throws Exception {
        given(manutencaoService.registrar(any(UsuarioRegistroDto.class))).willThrow(
                UsuarioAlreadyExistsException.class);
        mockMvc.perform(post("/usuarios").content(objectMapper.writeValueAsString(USUARIO_REGISTRO_DTO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
    }

    @Test
    void atualizarUsuarioComDadosValidosRetornaUsuarioDetalhesDto() throws Exception {
        given(manutencaoService.atualizar(1L, USUARIO_ATUALIZACAO_DTO)).willReturn(USUARIO_DETALHES_DTO_ATUALIZADO);
        mockMvc.perform(put("/usuarios/1").content(
                objectMapper.writeValueAsString(USUARIO_ATUALIZACAO_DTO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpectAll(
                jsonPath("$.nome").value(USUARIO_DETALHES_DTO_ATUALIZADO.nome()),
                jsonPath("$.apelido").value(USUARIO_DETALHES_DTO_ATUALIZADO.nomeSocial()),
                jsonPath("$.celular").value(USUARIO_DETALHES_DTO_ATUALIZADO.celular()), jsonPath("$.dataCriacao").value(
                        USUARIO_DETALHES_DTO.dataModificacao().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))),
                jsonPath("$.dataModificacao").value(
                        USUARIO_DETALHES_DTO.dataModificacao().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))));
    }

    @Test
    void atualizarUsuarioComDadosInvalidosRetornaUnprocessableEntity() throws Exception {
        mockMvc.perform(put("/usuarios/1").content(
                objectMapper.writeValueAsString(USUARIO_ATUALIZACAO_DTO_INVALIDO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void atualizarUsuarioInexistenteRetornaNotFound() throws Exception {
        given(manutencaoService.atualizar(1L, USUARIO_ATUALIZACAO_DTO)).willThrow(UsuarioNotFoundException.class);
        mockMvc.perform(put("/usuarios/1").content(
                objectMapper.writeValueAsString(USUARIO_ATUALIZACAO_DTO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void desativarUsuarioAtivoPorIdExistenteRetornaOk() throws Exception {
        given(manutencaoService.desativar(10L)).willReturn("Usuário desativado");
        mockMvc.perform(delete("/usuarios/10")).andExpect(status().isOk()).andExpectAll(
                jsonPath("$").value("Usuário desativado"));
    }

    @Test
    void desativarUsuarioPorIdInexistenteOuInativoRetornaNotFound() throws Exception {
        given(manutencaoService.desativar(10L)).willThrow(UsuarioNotFoundException.class);
        mockMvc.perform(delete("/usuarios/10")).andExpectAll(status().isNotFound());
    }

    @Test
    void reativarUsuarioInativoPorIdExistenteRetornaOk() throws Exception {
        given(manutencaoService.reativar(10L)).willReturn("Usuário reativado");
        mockMvc.perform(patch("/usuarios/reativar/10")).andExpectAll(status().isOk());
    }

    @Test
    void reativarUsuarioAtivoOuPorIdInexistenteRetornaNotFound() throws Exception {
        given(manutencaoService.reativar(10L)).willThrow(UsuarioNotFoundException.class);
        mockMvc.perform(patch("/usuarios/reativar/10")).andExpect(status().isNotFound());
    }

    @Test
    void listarUsuariosExistentesRetornaPageUsuarioDto() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "id");
        List<UsuarioDto> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(USUARIO_DTO);
        listaUsuarios.add(USUARIO_DTO_ATUALIZADO);
        Page<UsuarioDto> pageUsuarios = new PageImpl<>(listaUsuarios, pageable, 10);
        given(consultaService.listar(pageable)).willReturn(pageUsuarios);
        mockMvc.perform(get("/usuarios")).andExpect(status().isOk()).andExpectAll(jsonPath("$.empty").value(false),
                jsonPath("$.numberOfElements").value(2));
    }

    @Test
    void listarUsuariosInexistentesThrowsException() throws Exception {
        given(consultaService.listar(any(Pageable.class))).willThrow(UsuarioNotFoundException.class);
        mockMvc.perform(get("/usuarios")).andExpect(status().isNotFound());
    }

    @Test
    void consultarUsuarioPorIdExistenteRetornaUsuarioDetalhesDto() throws Exception {
        given(consultaService.consultarPorId(1L)).willReturn(USUARIO_DETALHES_DTO);
        mockMvc.perform(get("/usuarios/1")).andExpect(status().isOk()).andExpectAll(
                jsonPath("$.nome").value(USUARIO_DETALHES_DTO.nome()),
                jsonPath("$.apelido").value(USUARIO_DETALHES_DTO.nomeSocial()),
                jsonPath("$.celular").value(USUARIO_DETALHES_DTO.celular()), jsonPath("$.dataCriacao").value(
                        USUARIO_DETALHES_DTO.dataModificacao().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))),
                jsonPath("$.dataModificacao").value(
                        USUARIO_DETALHES_DTO.dataModificacao().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))));
    }

    @Test
    void consultarUsuarioPorIdInexistenteRetornaNotFound() throws Exception {
        given(consultaService.consultarPorId(10L)).willThrow(UsuarioNotFoundException.class);
        mockMvc.perform(get("/usuarios/10")).andExpect(status().isNotFound());
    }

    @Test
    void consultarUsuarioPorIdInvalidoThrowsException() throws Exception {
        mockMvc.perform(get("/usuarios/a")).andExpect(status().isBadRequest()).andExpectAll(
                jsonPath("$").value("Parâmetro inválido. Verifique e tente novamente"));
    }

    @Test
    void consultarUsuarioPorCpfExistenteRetornaUsuarioDetalhesDto() throws Exception {
        given(consultaService.consultarPorCpf(USUARIO_DETALHES_DTO.cpf())).willReturn(USUARIO_DETALHES_DTO);
        mockMvc.perform(get("/usuarios/cpf").param("cpf", USUARIO_DETALHES_DTO.cpf())).andExpect(
                status().isOk()).andExpectAll(jsonPath("$.nome").value(USUARIO_DETALHES_DTO.nome()),
                jsonPath("$.apelido").value(USUARIO_DETALHES_DTO.nomeSocial()),
                jsonPath("$.celular").value(USUARIO_DETALHES_DTO.celular()), jsonPath("$.dataCriacao").value(
                        USUARIO_DETALHES_DTO.dataModificacao().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))),
                jsonPath("$.dataModificacao").value(
                        USUARIO_DETALHES_DTO.dataModificacao().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))));
    }

    @Test
    void consultarUsuarioPorCpfInexistenteThrowsException() throws Exception {
        given(consultaService.consultarPorCpf(USUARIO_DETALHES_DTO.cpf())).willThrow(UsuarioNotFoundException.class);
        mockMvc.perform(get("/usuarios/cpf").param("cpf", USUARIO_DETALHES_DTO.cpf())).andExpect(status().isNotFound());
    }

    @Test
    void consultarUsuarioPorCpfInvalidoThrowsException() throws Exception {
        given(consultaService.consultarPorCpf("")).willThrow(ConstraintViolationException.class);
        mockMvc.perform(get("/usuarios/cpf").param("cpf", "")).andExpect(status().isBadRequest());
    }

    @Test
    void consultarUsuarioPorNomeExistenteRetornaUsuarioDto() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        List<UsuarioDto> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(USUARIO_DTO_ATUALIZADO);
        Page<UsuarioDto> pageUsuarios = new PageImpl<>(listaUsuarios, pageable, 10);
        given(consultaService.consultarPorNome(USUARIO_DTO_ATUALIZADO.nome(), pageable)).willReturn(pageUsuarios);
        mockMvc.perform(get("/usuarios/nome").param("nome", USUARIO_DTO_ATUALIZADO.nome())).andExpect(
                status().isOk()).andExpectAll(jsonPath("$.empty").value(false), jsonPath("$.numberOfElements").value(1),
                jsonPath("$.content[0].nome").value(USUARIO_DTO_ATUALIZADO.nome()));
    }

    @Test
    void consultarUsuarioPorNomeInexistenteRetornaNotFound() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
        given(consultaService.consultarPorNome("nome", pageable)).willThrow(UsuarioNotFoundException.class);
        mockMvc.perform(get("/usuarios/nome").param("nome", "nome")).andExpect(status().isNotFound());
    }

    @Test
    void criarUsuarioComRequestBodyNuloRetornaBadRequest() throws Exception {
        mockMvc.perform(post("/usuarios").content(objectMapper.writeValueAsString(null)).contentType(
                MediaType.APPLICATION_JSON)).andExpectAll(status().isBadRequest(),
                jsonPath("$").value("Informação inválida. Verifique os dados e tente novamente"));
    }

    @Test
    void inserirParametroCpfNuloThrowsException() throws Exception {
        mockMvc.perform(get("/usuarios/cpf")).andExpect(status().isBadRequest()).andExpectAll(
                jsonPath("$.campo").value("cpf"), jsonPath("$.mensagem").value("O campo CPF é obrigatório"));
    }

}
