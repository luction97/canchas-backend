package com.reservas.canchas.backend.turno.dto;

import java.time.LocalTime;

public record GrillaTurnoDTO(LocalTime hora,
                String estado, String cliente) {

}
