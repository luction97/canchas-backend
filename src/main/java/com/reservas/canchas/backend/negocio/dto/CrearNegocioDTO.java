package com.reservas.canchas.backend.negocio.dto;

public record CrearNegocioDTO(
        String nombre,
        String identificadorUrl,
        String nroCelular) {
}
