package com.reservas.canchas.backend.cancha.horario.dto;

import java.time.LocalTime;

import com.reservas.canchas.backend.cancha.horario.DiaSemana;

public record HorarioDTO(Long id,
                DiaSemana diaSemana,
                LocalTime horaInicio,
                LocalTime horaFin) {
}
