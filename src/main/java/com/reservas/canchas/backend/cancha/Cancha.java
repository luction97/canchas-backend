package com.reservas.canchas.backend.cancha;

import com.reservas.canchas.backend.negocio.Negocio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "canchas")
public class Cancha {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cancha_seq")
    @SequenceGenerator(name = "cancha_seq", sequenceName = "cancha_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY) // Muchas (Many) canchas pueden pertenecer a un (one) negocio
    @JoinColumn(name = "negocio_id", nullable = false) // Crea la columna 'negocio_id' en la tabla 'canchas'
    private Negocio negocio; // Relacion con Negocio

}
