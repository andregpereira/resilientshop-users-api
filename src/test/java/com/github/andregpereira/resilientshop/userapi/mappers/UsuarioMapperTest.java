package com.github.andregpereira.resilientshop.userapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapperTest {

	Usuario toUsuario(UsuarioRegistroDto dto);

	UsuarioDetalhesDto toUsuarioDetalhesDto(UsuarioRegistroDto dto);

}
