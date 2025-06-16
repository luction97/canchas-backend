package com.reservas.canchas.backend.cancha;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.reservas.canchas.backend.cancha.dto.CanchaDTO;
import com.reservas.canchas.backend.cancha.dto.CrearCanchaDTO;

@Mapper(componentModel = "spring")
public interface CanchaMapper {

    @Mapping(source = "negocio.id", target = "negocioId")
    CanchaDTO toDto(Cancha cancha);

    // Le decimos a MapStruct: "Cuando conviertas el DTO a Entidad, ignora por
    // completo
    // el campo 'negocio'. Déjalo en null. El CanchaService se encargará de él".
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "negocio", ignore = true)
    @Mapping(target = "horariosDisponibles", ignore = true)
    Cancha toEntity(CrearCanchaDTO canchaDTO);
}
