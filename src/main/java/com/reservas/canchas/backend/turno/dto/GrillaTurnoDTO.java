package com.reservas.canchas.backend.turno.dto;

import java.time.LocalTime;

public record GrillaTurnoDTO(LocalTime hora,
        String estado // "LIBRE", "RESERVADO" o "BUSCA_RIVAL"
) {

}
