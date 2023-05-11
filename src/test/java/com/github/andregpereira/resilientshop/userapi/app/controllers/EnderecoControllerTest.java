package com.github.andregpereira.resilientshop.userapi.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andregpereira.resilientshop.userapi.app.services.EnderecoConsultaService;
import com.github.andregpereira.resilientshop.userapi.cross.exceptions.EnderecoNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.andregpereira.resilientshop.userapi.constants.EnderecoDtoConstants.ENDERECO_DTO;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

    @MockBean
    private EnderecoConsultaService consultaService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void consultarEnderecoPorIdExistenteRetornaEnderecoDto() throws Exception {
        given(consultaService.consultarPorId(1L, 1L)).willReturn(ENDERECO_DTO);
        mockMvc.perform(get("/enderecos/1/usuario/1")).andExpect(status().isOk()).andExpectAll(
                jsonPath("$.apelido").value(ENDERECO_DTO.apelido()));
    }

    @Test
    void consultarEnderecoPorIdInexistenteRetornaNotFound() throws Exception {
        given(consultaService.consultarPorId(10L, 10L)).willThrow(EnderecoNotFoundException.class);
        mockMvc.perform(get("/enderecos/10/usuario/10")).andExpect(status().isNotFound());
    }

    @Test
    void consultarEnderecoPorIdInvalidoThrowsException() throws Exception {
        mockMvc.perform(get("/enderecos/a/usuario/a")).andExpect(status().isBadRequest()).andExpectAll(
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
