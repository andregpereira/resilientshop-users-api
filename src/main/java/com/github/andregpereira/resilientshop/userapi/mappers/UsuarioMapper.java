package com.github.andregpereira.resilientshop.userapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.entities.Usuario;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

	Usuario toUsuario(UsuarioDto usuarioDto);

	Usuario toUsuario(UsuarioRegistroDto usuarioRegistroDto);

	Usuario toUsuario(UsuarioAtualizacaoDto usuarioAtualizacaoDto);

	UsuarioDto toUsuarioDto(Usuario usuario);

	UsuarioDetalhesDto toUsuarioDetalhesDto(Usuario usuario);

}
