package com.github.andregpereira.resilientshop.userapi.controllers;

import static com.github.andregpereira.resilientshop.userapi.UsuarioConstants.USUARIO_DETALHES_DTO;
import static com.github.andregpereira.resilientshop.userapi.UsuarioConstants.USUARIO_DTO;
import static com.github.andregpereira.resilientshop.userapi.UsuarioConstants.USUARIO_REGISTRO_DTO;
import static com.github.andregpereira.resilientshop.userapi.UsuarioConstants.USUARIO_REGISTRO_DTO_INVALIDO;
import static com.github.andregpereira.resilientshop.userapi.UsuarioConstants.USUARIO_REGISTRO_DTO_VAZIO;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andregpereira.resilientshop.userapi.services.UsuarioConsultaService;
import com.github.andregpereira.resilientshop.userapi.services.UsuarioManutencaoService;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
public class UsuarioControllerTest {

	@MockBean
	private UsuarioManutencaoService manutencaoService;

	@MockBean
	private UsuarioConsultaService consultaService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void criarUsuarioComDadosValidosRetorna201() throws Exception {
		when(manutencaoService.registrar(USUARIO_REGISTRO_DTO)).thenReturn(USUARIO_DETALHES_DTO);
		mockMvc.perform(post("/usuarios").content(objectMapper.writeValueAsString(USUARIO_REGISTRO_DTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	public void criarUsuarioComDadosInvalidosRetorna422() throws Exception {
		mockMvc.perform(post("/usuarios").content(objectMapper.writeValueAsString(USUARIO_REGISTRO_DTO_VAZIO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity());
		mockMvc.perform(post("/usuarios").content(objectMapper.writeValueAsString(USUARIO_REGISTRO_DTO_INVALIDO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void criarUsuarioComCpfExistenteRetorna409() throws Exception {
		when(manutencaoService.registrar(any())).thenThrow(DataIntegrityViolationException.class);
		mockMvc.perform(post("/usuarios").content(objectMapper.writeValueAsString(USUARIO_REGISTRO_DTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
	}

	@Test
	public void consultarUsuarioPorIdExistenteRetornaUsuario() throws Exception {
		when(consultaService.consultarPorId(1L)).thenReturn(USUARIO_DETALHES_DTO);
		mockMvc.perform(get("/usuarios/1")).andExpect(status().isOk());
	}

	@Test
	public void consultarUsuarioPorIdInexistenteRetorna404() throws Exception {
		mockMvc.perform(get("/usuarios/1")).andExpect(status().isNotFound());
	}

	@Test
	public void consultarUsuarioPorNomeExistenteRetornaUsuario() throws Exception {
		when(consultaService.consultarPorNome(USUARIO_DTO.nome(), null, null)).thenReturn(null);
		mockMvc.perform(get("/usuarios/nome?")).andExpect(status().isOk());
	}

}
