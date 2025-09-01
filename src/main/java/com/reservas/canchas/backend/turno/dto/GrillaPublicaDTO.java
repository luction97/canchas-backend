package com.reservas.canchas.backend.turno.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record GrillaPublicaDTO(
    Map<LocalDate, List<GrillaTurnoDTO>> grilla,
    String nroCelular
) {}
