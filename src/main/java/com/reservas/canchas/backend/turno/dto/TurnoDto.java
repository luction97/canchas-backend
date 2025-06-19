package com.reservas.canchas.backend.turno.dto;

import java.time.LocalDateTime;

import com.reservas.canchas.backend.turno.EstadoTurno;

public record TurnoDto(Long id, LocalDateTime fechaHoraInicio, EstadoTurno estado, String nombreCliente) {

}
