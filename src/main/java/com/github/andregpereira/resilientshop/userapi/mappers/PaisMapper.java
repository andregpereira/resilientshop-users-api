package com.github.andregpereira.resilientshop.userapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.github.andregpereira.resilientshop.userapi.dtos.pais.PaisDto;
import com.github.andregpereira.resilientshop.userapi.dtos.pais.PaisRegistroDto;
import com.github.andregpereira.resilientshop.userapi.entities.Pais;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaisMapper {

	Pais toPais(PaisDto paisDto);

	Pais toPais(PaisRegistroDto paisRegistroDto);

	PaisDto toPaisDto(Pais usuario);

}
