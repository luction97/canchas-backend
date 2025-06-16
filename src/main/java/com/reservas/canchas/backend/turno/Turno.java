package com.reservas.canchas.backend.turno;

import java.time.LocalDateTime;

import com.reservas.canchas.backend.cancha.Cancha;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "turnos")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turno_seq")
    @SequenceGenerator(name = "turno_seq", sequenceName = "turno_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaHoraInicio; // Guarda la fecha y hora exactas del inicio del turno

    @Enumerated(EnumType.STRING) // Le dice a JPA que guarde el nombre del estado ("RESERVADO") como texto en la                            // BD
    @Column(nullable = false)
    private EstadoTurno estado;

    @ManyToOne(fetch = FetchType.LAZY) // Relación con Cancha, se carga de forma perezosa
    @JoinColumn(name = "cancha_id", nullable = false) // Columna que referencia a la cancha
    private Cancha cancha;

}
