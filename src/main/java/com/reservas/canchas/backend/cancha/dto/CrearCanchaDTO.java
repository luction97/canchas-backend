package com.reservas.canchas.backend.cancha.dto;

public record CrearCanchaDTO(String nombre, String descripcion, Long negocioId) { // negocioId para saber a qué negocio pertenece la cancha

    // Constructor, getters y setters generados automáticamente por el record
    // No es necesario agregar más lógica, ya que los records son inmutables
    // y proporcionan automáticamente los métodos equals(), hashCode() y toString().


}
