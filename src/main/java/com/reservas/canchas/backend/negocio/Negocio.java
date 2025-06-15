package com.reservas.canchas.backend.negocio;

import java.util.List;

import com.reservas.canchas.backend.cancha.Cancha;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "negocios")
public class Negocio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "negocio_seq")
    @SequenceGenerator(name = "negocio_seq", sequenceName = "negocio_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(unique = true, nullable = false, length = 50)
    private String identificadorUrl;

    private String nroCelular;

    // CascadeType.ALL permite que las operaciones de persistencia, actualización y
    // eliminación se propaguen a las canchas asociadas
    // orphanRemoval = true permite que las canchas que ya no están asociadas al
    // negocio se eliminen automáticamente
    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<Cancha> canchas; // Relación con Cancha

}
