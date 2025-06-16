package com.reservas.canchas.backend.turno;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.reservas.canchas.backend.cancha.Cancha;
import com.reservas.canchas.backend.cancha.CanchaRepository;
import com.reservas.canchas.backend.turno.dto.CrearTurnoDto;
import com.reservas.canchas.backend.turno.dto.TurnoDto;

@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final CanchaRepository canchaRepository;
    private final TurnoMapper turnoMapper;

    public TurnoService(TurnoRepository turnoRepository, CanchaRepository canchaRepository, TurnoMapper turnoMapper) {
        this.turnoRepository = turnoRepository;
        this.canchaRepository = canchaRepository;
        this.turnoMapper = turnoMapper;
    }

    public TurnoDto crearTurno(Long idCancha, CrearTurnoDto crearTurnoDto) {

        Cancha cancha = canchaRepository.findById(idCancha)
                .orElseThrow(() -> new RuntimeException("Cancha no encontrada con ID: " + idCancha));

        Turno nuevoTurno = turnoMapper.toEntity(crearTurnoDto);
        nuevoTurno.setCancha(cancha);

        Turno turnoGuardado = turnoRepository.save(nuevoTurno);
        return turnoMapper.toDto(turnoGuardado);

    }

    public List<TurnoDto> obtenerTurnosPorDia(Long idCancha, LocalDate fecha) {
        LocalDateTime inicioDelDia = fecha.atStartOfDay(); // Ej: 2025-06-15T00:00:00
        LocalDateTime finDelDia = fecha.plusDays(1).atStartOfDay(); // Ej: 2025-06-16T00:00:00

        List<Turno> turnos = turnoRepository.findByCanchaIdAndFechaHoraInicioBetween(idCancha, inicioDelDia, finDelDia);

        return turnoMapper.toDtoList(turnos);
    }

}
