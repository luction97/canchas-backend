package com.reservas.canchas.backend.turno;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.reservas.canchas.backend.turno.dto.CrearTurnoDto;
import com.reservas.canchas.backend.turno.dto.TurnoDto;

@Mapper(componentModel = "spring")
public interface TurnoMapper {

    TurnoDto toDto(Turno turno);

    @Mapping(target = "id", ignore = true) // Ignorar el ID al crear un nuevo Turno
    @Mapping(target = "cancha", ignore = true) // Ignorar la cancha al crear un nuevo Turno
    Turno toEntity(CrearTurnoDto crearTurnoDto);

    List<TurnoDto> toDtoList(List<Turno> turnos);

}
