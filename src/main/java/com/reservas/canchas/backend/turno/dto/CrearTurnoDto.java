package com.reservas.canchas.backend.turno.dto;

import java.time.LocalDateTime;

import com.reservas.canchas.backend.turno.EstadoTurno;

public record CrearTurnoDto(LocalDateTime fechaHoraInicio, EstadoTurno estado, String nombreCliente) {
}
