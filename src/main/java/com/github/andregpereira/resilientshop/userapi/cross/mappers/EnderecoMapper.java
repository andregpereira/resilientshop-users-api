package com.github.andregpereira.resilientshop.userapi.cross.mappers;

import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroUsuarioDto;
import com.github.andregpereira.resilientshop.userapi.app.dto.endereco.EnderecoRegistroDto;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnderecoMapper {

    Endereco toEndereco(EnderecoRegistroUsuarioDto dto);

    Endereco toEndereco(EnderecoRegistroDto dto);

    Endereco toEndereco(EnderecoDto dto);

    EnderecoDto toEnderecoDto(Endereco endereco);

    List<Endereco> listaEnderecoRegistroDtoToListaEnderecos(List<EnderecoRegistroUsuarioDto> dtos);

    List<Endereco> listaEnderecoDtoToListaEnderecos(List<EnderecoDto> dtos);

    List<EnderecoDto> toListaEnderecosDto(List<Endereco> enderecos);

}
