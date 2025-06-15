package com.reservas.canchas.backend.negocio;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.reservas.canchas.backend.negocio.dto.NegocioDTO;
import com.reservas.canchas.backend.negocio.dto.CrearNegocioDTO;

@Mapper(componentModel = "spring")
public interface NegocioMapper {

    NegocioDTO toDto(Negocio negocio);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "canchas", ignore = true)

    Negocio toEntity(CrearNegocioDTO crearNegocioDTO);

    List<NegocioDTO> toDtoList(List<Negocio> negocios);

}