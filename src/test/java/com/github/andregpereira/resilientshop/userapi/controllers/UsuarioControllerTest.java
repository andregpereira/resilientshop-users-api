package com.github.andregpereira.resilientshop.userapi.controllers;

import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioConstants.*;
import static com.github.andregpereira.resilientshop.userapi.constants.UsuarioDtoConstants.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;
import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;
import com.github.andregpereira.resilientshop.userapi.services.UsuarioConsultaService;
import com.github.andregpereira.resilientshop.userapi.services.UsuarioManutencaoService;

import jakarta.persistence.EntityNotFoundException;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

	@MockBean
	private UsuarioManutencaoService manutencaoService;

	@MockBean
	private UsuarioConsultaService consultaService;

	@MockBean
	public UsuarioRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void criarUsuarioComDadosValidosRetorna201() throws Exception {
		when(manutencaoService.registrar(USUARIO_REGISTRO_DTO)).thenReturn(USUARIO_DETALHES_DTO);
		mockMvc.perform(MockMvcRequestBuilders. post("/usuarios").content(objectMapper.writeValueAsString(USUARIO_REGISTRO_DTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(jsonPath("$",is(USUARIO_DETALHES_DTO)));
	}

	@Test
	public void criarUsuarioComDadosInvalidosRetorna422() throws Exception {
		mockMvc.perform(post("/usuarios").content(objectMapper.writeValueAsString(USUARIO_REGISTRO_DTO_INVALIDO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity());
		mockMvc.perform(post("/usuarios").content(objectMapper.writeValueAsString(USUARIO_ATUALIZACAO_DTO_INVALIDO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void criarUsuarioComCpfExistenteRetorna409() throws Exception {
		when(manutencaoService.registrar(any())).thenThrow(DataIntegrityViolationException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/usuarios").content(objectMapper.writeValueAsString(USUARIO_REGISTRO_DTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
	}

	@Test
	public void consultarUsuarioPorIdExistenteRetornaUsuario() throws Exception {
		when(consultaService.consultarPorId(1L)).thenReturn(USUARIO_DETALHES_DTO);
		mockMvc.perform(get("/usuarios/1")).andExpect(status().isFound());
	}

	@Test
	public void consultarUsuarioPorIdInexistenteRetorna404() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/10")).andReturn();
		System.out.println(result.getResponse().getStatus());
		mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/10")).andExpect(status().isNotFound()).andExpect(null);
	}

	@Test
	public void consultarUsuarioPorNomeExistenteRetornaUsuario() throws Exception {
		PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
		List<UsuarioDto> listaUsuarios = new ArrayList<>();
		Page<UsuarioDto> pageUsuarios = new PageImpl<>(listaUsuarios, pageable, 10);
		when(consultaService.consultarPorNome(USUARIO_DTO.nome(), USUARIO_DTO.sobrenome(), pageable))
				.thenReturn(pageUsuarios);
		mockMvc.perform(MockMvcRequestBuilders.get("/usuarios")).andExpect(status().isOk());
	}

	@Test
	public void consultarUsuarioPorNomeInexistenteRetorna404() throws Exception {
//		PageRequest pageable = PageRequest.of(0, 10, Direction.ASC, "nome");
//		List<UsuarioDto> listaUsuarios = new ArrayList<>();
//		Page<UsuarioDto> pageUsuarios = new PageImpl<>(listaUsuarios, pageable, 10);
//		when(consultaService.consultarPorNome("", "", pageable)).thenReturn(pageUsuarios);
		mockMvc.perform(MockMvcRequestBuilders.get("/usuarios").param("nome", "aaaaaaaaa")).andDo(print())
				.andExpect(status().isNotFound());
	}

}
