package com.reservas.canchas.backend.negocio.dto;

import com.reservas.canchas.backend.negocio.Negocio;

public record NegocioDTO(long id, String nombre, String identificadorUrl, String nroCelular) {

    // Constructor, getters y setters generados automáticamente por el record
    // No es necesario agregar más lógica, ya que los records son inmutables
    // y proporcionan automáticamente los métodos equals(), hashCode() y toString().

    public NegocioDTO(Negocio negocio) {
        this(negocio.getId(), negocio.getNombre(), negocio.getIdentificadorUrl(), negocio.getNroCelular());
    }

}
