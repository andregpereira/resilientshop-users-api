package com.github.andregpereira.resilientshop.userapi.cross.mappers;

import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioDetalhesDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.usuario.UsuarioRegistroDto;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Usuario;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, uses = EnderecoMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    @Mapping(target = "enderecos", expression = """
            java(usuarioRegistroDto.endereco() == null ? java.util.Collections.emptyList()
            : java.util.Collections.singletonList(enderecoMapper.toEndereco(usuarioRegistroDto.endereco())))
            """)
    Usuario toUsuario(UsuarioRegistroDto usuarioRegistroDto);

    UsuarioDto toUsuarioDto(Usuario usuario);

    UsuarioDetalhesDto toUsuarioDetalhesDto(Usuario usuario);

}
