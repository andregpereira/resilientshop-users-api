package com.github.andregpereira.resilientshop.userapi.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.github.andregpereira.resilientshop.userapi.repositories.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioConsultaServiceTest {

	@InjectMocks
	private UsuarioConsultaService consultaService;
	
	@MockBean
	private UsuarioRepository repository;
	
	
}
