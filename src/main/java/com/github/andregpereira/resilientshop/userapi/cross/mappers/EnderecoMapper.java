package com.github.andregpereira.resilientshop.userapi.cross.mappers;

import com.github.andregpereira.resilientshop.userapi.app.dtos.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.app.dtos.endereco.EnderecoRegistroDto;
import com.github.andregpereira.resilientshop.userapi.infra.entities.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnderecoMapper {

    List<Endereco> listaEnderecoRegistroDtoToListaEnderecos(List<EnderecoRegistroDto> dtos);

    List<Endereco> listaEnderecoDtoToListaEnderecos(List<EnderecoDto> dtos);

    List<EnderecoDto> toListaEnderecosDto(List<Endereco> enderecos);

    Endereco enderecoRegistroDtoToEndereco(EnderecoRegistroDto dto);

    Endereco enderecoDtoToEndereco(EnderecoDto dto);

    EnderecoDto toEnderecoDto(Endereco endereco);

}
