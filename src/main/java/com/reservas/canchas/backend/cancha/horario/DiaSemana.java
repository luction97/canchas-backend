package com.reservas.canchas.backend.cancha.horario;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DiaSemana {
    LUNES("LUNES"),
    MARTES("MARTES"),
    MIERCOLES("MIERCOLES"),
    JUEVES("JUEVES"),
    VIERNES("VIERNES"),
    SABADO("SABADO"),
    DOMINGO("DOMINGO");

    private final String nombre;

    DiaSemana(String nombre) {
        this.nombre = nombre;
    }

    // @JsonValue le dice a Jackson: "Cuando conviertas este Enum a JSON, usa este
    // valor".
    @JsonValue
    public String getNombre() {
        return nombre;
    }

    // @JsonCreator es la clave: "Cuando recibas un texto de un JSON, usa este
    // método
    // para encontrar la instancia correcta del Enum".
    @JsonCreator
    public static DiaSemana fromString(String text) {
        if (text == null) {
            return null;
        }
        // Recorre todos los valores del enum (LUNES, MARTES, etc.)
        for (DiaSemana dia : DiaSemana.values()) {
            // Comparamos el texto del JSON con el nombre del enum (ej: "LUNES") o su valor
            // (ej: "Lunes")
            // ignorando mayúsculas/minúsculas y espacios.
            if (dia.name().equalsIgnoreCase(text.trim()) || dia.nombre.equalsIgnoreCase(text.trim())) {
                return dia; // Devuelve la instancia correcta del enum
            }
        }
        // Si no encuentra ninguna coincidencia, lanza un error claro.
        throw new IllegalArgumentException("Día de la semana no válido: " + text);
    }
}