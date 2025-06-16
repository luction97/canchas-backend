package com.reservas.canchas.backend.cancha.horario;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.reservas.canchas.backend.cancha.horario.dto.CrearHorarioDTO;
import com.reservas.canchas.backend.cancha.horario.dto.HorarioDTO;

@Mapper(componentModel = "spring")
public interface HorarioMapper {

    HorarioDTO toDto(HorarioDisponible horarioDisponible);

    // --- INICIO DE LA CORRECCIÓN CLAVE ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cancha", ignore = true)
    // Le damos una orden directa a MapStruct para cada campo.
    @Mapping(target = "diaSemana", source = "diaSemana")
    @Mapping(target = "horaInicio", source = "horaInicio")
    @Mapping(target = "horaFin", source = "horaFin")
    HorarioDisponible toEntity(CrearHorarioDTO crearHorarioDTO);
    // --- FIN DE LA CORRECCIÓN CLAVE ---

    List<HorarioDTO> toDtoList(List<HorarioDisponible> horarios);
}
