package com.github.andregpereira.resilientshop.userapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.github.andregpereira.resilientshop.userapi.dtos.endereco.EnderecoDto;
import com.github.andregpereira.resilientshop.userapi.dtos.endereco.EnderecoRegistroDto;
import com.github.andregpereira.resilientshop.userapi.entities.Endereco;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnderecoMapper {

	Endereco toEndereco(EnderecoDto enderecoDto);

	Endereco toEndereco(EnderecoRegistroDto enderecoRegistroDto);

	EnderecoDto toEnderecoDto(Endereco endereco);

}
