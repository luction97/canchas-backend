package com.reservas.canchas.backend.turno;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    // "WHERE cancha_id = ? AND fecha_hora_inicio BETWEEN ? AND ?"
    List<Turno> findByCanchaIdAndFechaHoraInicioBetween(Long canchaId, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin);

}
