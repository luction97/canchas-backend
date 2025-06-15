package com.reservas.canchas.backend.negocio;

import org.springframework.data.jpa.repository.JpaRepository;

/*Al extender JpaRepository, Spring Data JPA agrega los métodos save() (guardar), findById(), findAll(), etc.*/
public interface NegocioRepository extends JpaRepository<Negocio, Long> {
    
    // Método para encontrar un negocio por su identificador URL
    Negocio findByIdentificadorUrl(String identificadorUrl);
    
    // Método para verificar si un negocio con el mismo identificador URL ya existe
    boolean existsByIdentificadorUrl(String identificadorUrl);

}
