package com.github.andregpereira.resilientshop.userapi.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroDto;
import com.github.andregpereira.resilientshop.userapi.app.services.endereco.EnderecoConsultaService;
import com.github.andregpereira.resilientshop.userapi.app.services.endereco.EnderecoManutencaoService;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.EnderecoAlreadyExistsException;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.EnderecoNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoDtoConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

    @MockBean
    private EnderecoManutencaoService manutencaoService;

    @MockBean
    private EnderecoConsultaService consultaService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criarEnderecoComDadosValidosRetornaCreated() throws Exception {
        given(manutencaoService.criar(ENDERECO_REGISTRO_DTO)).willReturn(ENDERECO_DTO);
        mockMvc.perform(post("/enderecos").content(objectMapper.writeValueAsString(ENDERECO_REGISTRO_DTO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpectAll(
                jsonPath("$.apelido").value(ENDERECO_DTO.apelido()), jsonPath("$.cep").value(ENDERECO_DTO.cep()));
    }

    @Test
    void criarEnderecoComApelidoRepetidoRetornaConflict() throws Exception {
        given(manutencaoService.criar(ENDERECO_REGISTRO_DTO)).willThrow(EnderecoAlreadyExistsException.class);
        mockMvc.perform(post("/enderecos").content(objectMapper.writeValueAsString(ENDERECO_REGISTRO_DTO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
    }

    @Test
    void criarEnderecoComDadosInvalidosRetornaUnprocessableEntity() throws Exception {
        mockMvc.perform(post("/enderecos").content(
                objectMapper.writeValueAsString(ENDERECO_REGISTRO_DTO_INVALIDO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void atualizarEnderecoComDadosValidosRetornaCreated() throws Exception {
        given(manutencaoService.atualizar(1L, ENDERECO_REGISTRO_DTO)).willReturn(ENDERECO_DTO_ATUALIZADO);
        mockMvc.perform(put("/enderecos/1").content(objectMapper.writeValueAsString(ENDERECO_REGISTRO_DTO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpectAll(
                jsonPath("$.apelido").value(ENDERECO_DTO_ATUALIZADO.apelido()),
                jsonPath("$.cep").value(ENDERECO_DTO_ATUALIZADO.cep()));
    }

    @Test
    void atualizarEnderecoComApelidoRepetidoRetornaConflict() throws Exception {
        given(manutencaoService.atualizar(anyLong(), any(EnderecoRegistroDto.class))).willThrow(
                EnderecoAlreadyExistsException.class);
        mockMvc.perform(put("/enderecos/1").content(objectMapper.writeValueAsString(ENDERECO_REGISTRO_DTO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
    }

    @Test
    void atualizarEnderecoComDadosInvalidosRetornaUnprocessableEntity() throws Exception {
        mockMvc.perform(put("/enderecos/1").content(
                objectMapper.writeValueAsString(ENDERECO_REGISTRO_DTO_INVALIDO)).contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void removerEnderecoPorIdExistenteRetornaOk() throws Exception {
        given(manutencaoService.remover(anyLong())).willReturn("Endereço removido com sucesso");
        mockMvc.perform(delete("/enderecos/10")).andExpect(status().isOk()).andExpectAll(
                jsonPath("$").value("Endereço removido com sucesso"));
    }

    @Test
    void removerEnderecoPorIdInexistenteRetornaNotFound() throws Exception {
        given(manutencaoService.remover(anyLong())).willThrow(EnderecoNotFoundException.class);
        mockMvc.perform(delete("/enderecos/10")).andExpectAll(status().isNotFound());
    }

    @Test
    void consultarEnderecoPorIdExistenteRetornaEnderecoDto() throws Exception {
        given(consultaService.consultarPorId(1L)).willReturn(ENDERECO_DTO);
        mockMvc.perform(get("/enderecos/1")).andExpect(status().isOk()).andExpectAll(
                jsonPath("$.apelido").value(ENDERECO_DTO.apelido()));
    }

    @Test
    void consultarEnderecoPorIdInexistenteRetornaNotFound() throws Exception {
        given(consultaService.consultarPorId(10L)).willThrow(EnderecoNotFoundException.class);
        mockMvc.perform(get("/enderecos/10/usuario/10")).andExpect(status().isNotFound());
    }

    @Test
    void consultarEnderecoPorIdInvalidoThrowsException() throws Exception {
        mockMvc.perform(get("/enderecos/a")).andExpect(status().isBadRequest()).andExpectAll(
                jsonPath("$").value("Parâmetro inválido. Verifique e tente novamente"));
    }

    @Test
    void consultarEnderecoPorIdUsuarioExistenteRetornaEnderecoDto() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        List<EnderecoDto> listaEnderecos = new ArrayList<>();
        listaEnderecos.add(ENDERECO_DTO);
        listaEnderecos.add(ENDERECO_DTO_ATUALIZADO);
        Page<EnderecoDto> pageEnderecos = new PageImpl<>(listaEnderecos, pageable, 10);
        given(consultaService.consultarPorIdUsuario(1L, pageable)).willReturn(pageEnderecos);
        mockMvc.perform(get("/enderecos/usuario/1")).andExpect(status().isOk()).andExpectAll(
                jsonPath("$.empty").value(false), jsonPath("$.numberOfElements").value(2));
    }

    @Test
    void consultarEnderecoPorIdUsuarioInexistenteRetornaNotFound() throws Exception {
        given(consultaService.consultarPorIdUsuario(anyLong(), any(Pageable.class))).willThrow(
                EnderecoNotFoundException.class);
        mockMvc.perform(get("/enderecos/usuario/10")).andExpect(status().isNotFound());
    }

    @Test
    void consultarEnderecoPorIdUsuarioInvalidoThrowsException() throws Exception {
        mockMvc.perform(get("/enderecos/usuario/a")).andExpect(status().isBadRequest()).andExpectAll(
                jsonPath("$").value("Parâmetro inválido. Verifique e tente novamente"));
    }

    @Test
    void consultarEnderecoPorNomeExistenteRetornaEnderecoDto() throws Exception {
        given(consultaService.consultarPorApelido(1L, ENDERECO_DTO.apelido())).willReturn(ENDERECO_DTO);
        mockMvc.perform(get("/enderecos/usuario/1/apelido").param("apelido", ENDERECO_DTO.apelido())).andExpect(
                status().isOk()).andExpectAll(jsonPath("$.apelido").value(ENDERECO_DTO.apelido()));
    }

    @Test
    void consultarEnderecoPorNomeInexistenteRetornaNotFound() throws Exception {
        given(consultaService.consultarPorApelido(10L, "")).willThrow(EnderecoNotFoundException.class);
        mockMvc.perform(get("/enderecos/usuario/10/apelido").param("apelido", "")).andExpect(status().isNotFound());
    }

    @Test
    void inserirParametroApelidoNuloThrowsException() throws Exception {
        mockMvc.perform(get("/enderecos/usuario/1/apelido")).andExpect(status().isBadRequest()).andExpectAll(
                jsonPath("$.campo").value("apelido"), jsonPath("$.mensagem").value("O campo apelido é obrigatório"));
    }

}
