package com.reservas.canchas.backend.turno;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EstadoTurno {
    RESERVADO("RESERVADO"),
    BUSCA_RIVAL("BUSCA_RIVAL");

    private final String estado;

    EstadoTurno(String estado) {
        this.estado = estado;
    }

    @JsonValue
    public String getEstado() {
        return estado;
    }

    // @JsonCreator le dice a Jackson cómo convertir un String del JSON a una
    // instancia del Enum
    @JsonCreator
    public static EstadoTurno fromString(String text) {
        // Recorre todos los valores del enum (RESERVADO, BUSCA_RIVAL)
        for (EstadoTurno b : EstadoTurno.values()) {
            // Compara el texto del JSON con el valor de cada enum, ignorando
            // mayúsculas/minúsculas
            if (b.estado.equalsIgnoreCase(text)) {
                return b; // Devuelve la instancia correcta del enum
            }
        }
        // Si no encuentra ninguna coincidencia, podrías devolver null o lanzar una
        // excepción
        throw new IllegalArgumentException("Estado no válido: " + text);
    }

}
