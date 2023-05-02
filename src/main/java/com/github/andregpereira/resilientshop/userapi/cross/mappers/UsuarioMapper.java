package com.github.andregpereira.resilientshop.userapi.cross.mappers;

import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioAtualizacaoDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, uses = EnderecoMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    Usuario toUsuario(UsuarioRegistroDto usuarioRegistroDto);

    Usuario toUsuario(UsuarioAtualizacaoDto usuarioAtualizacaoDto);

    UsuarioDto toUsuarioDto(Usuario usuario);

    UsuarioDetalhesDto toUsuarioDetalhesDto(Usuario usuario);

}
