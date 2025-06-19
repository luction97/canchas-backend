package com.reservas.canchas.backend.turno;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.reservas.canchas.backend.cancha.Cancha;
import com.reservas.canchas.backend.cancha.CanchaRepository;
import com.reservas.canchas.backend.cancha.horario.DiaSemana;
import com.reservas.canchas.backend.cancha.horario.HorarioDisponible;
import com.reservas.canchas.backend.cancha.horario.HorarioDisponibleRepository;
import com.reservas.canchas.backend.turno.dto.CrearTurnoDto;
import com.reservas.canchas.backend.turno.dto.GrillaTurnoDTO;
import com.reservas.canchas.backend.turno.dto.TurnoDto;

@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final CanchaRepository canchaRepository;
    private final TurnoMapper turnoMapper;
    private final HorarioDisponibleRepository horarioRepository;

    public TurnoService(TurnoRepository turnoRepository, CanchaRepository canchaRepository, TurnoMapper turnoMapper,
            HorarioDisponibleRepository horarioRepository) {
        this.turnoRepository = turnoRepository;
        this.canchaRepository = canchaRepository;
        this.turnoMapper = turnoMapper;
        this.horarioRepository = horarioRepository;
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

    public Map<LocalDate, List<GrillaTurnoDTO>> generarGrillaSemanal(Long idCancha, LocalDate fechaInicioSemana) {
        // 1. Buscamos la entidad Cancha completa para tener acceso a su horario general.
        Cancha cancha = canchaRepository.findById(idCancha)
                .orElseThrow(() -> new RuntimeException("Cancha no encontrada con ID: " + idCancha));

        // 2. Obtenemos todos los turnos reservados para la semana.
        LocalDateTime inicioSemana = fechaInicioSemana.atStartOfDay();
        LocalDateTime finSemana = fechaInicioSemana.plusDays(7).atStartOfDay();
        List<Turno> turnosDeLaSemana = turnoRepository.findByCanchaIdAndFechaHoraInicioBetween(idCancha, inicioSemana, finSemana);
        
        // Creamos un mapa donde el valor es el objeto Turno completo para poder acceder al nombre del cliente.
        Map<LocalDateTime, Turno> mapaTurnosOcupados = turnosDeLaSemana.stream()
                .collect(Collectors.toMap(Turno::getFechaHoraInicio, turno -> turno));

        Map<LocalDate, List<GrillaTurnoDTO>> grillaSemanal = new LinkedHashMap<>();

        // 3. Iteramos por cada día de la semana.
        for (int i = 0; i < 7; i++) {
            LocalDate diaActual = fechaInicioSemana.plusDays(i);
            DiaSemana nuestroDiaDeLaSemana = DiaSemana.fromJavaDayOfWeek(diaActual.getDayOfWeek());
            
            // 4. Lógica para determinar el horario de apertura y cierre.
            List<HorarioDisponible> horariosEspecificos = horarioRepository.findByCanchaIdAndDiaSemana(idCancha, nuestroDiaDeLaSemana);
            LocalTime horaInicio;
            LocalTime horaFin;

            if (!horariosEspecificos.isEmpty()) {
                horaInicio = horariosEspecificos.get(0).getHoraInicio();
                horaFin = horariosEspecificos.get(0).getHoraFin();
            } else {
                horaInicio = cancha.getHoraAperturaGeneral();
                horaFin = cancha.getHoraCierreGeneral();
            }

            // 5. Lógica para generar los slots con el cliente.
            List<GrillaTurnoDTO> turnosDelDia = new ArrayList<>();
            if (horaInicio != null && horaFin != null) {
                LocalTime horaActual = horaInicio;
                while (horaActual.isBefore(horaFin)) {
                    LocalDateTime fechaHoraActual = diaActual.atTime(horaActual);
                    
                    // --- ¡AQUÍ ESTÁ LA CORRECCIÓN! ---
                    if (mapaTurnosOcupados.containsKey(fechaHoraActual)) {
                        // Si el turno está ocupado, obtenemos el objeto Turno completo.
                        Turno turnoOcupado = mapaTurnosOcupados.get(fechaHoraActual);
                        turnosDelDia.add(new GrillaTurnoDTO(horaActual, turnoOcupado.getEstado().name(), turnoOcupado.getNombreCliente()));
                        // Creamos el DTO con los 3 argum
                    } else {
                        // Si el turno está libre, creamos el DTO con el estado LIBRE y null para el cliente.
                        turnosDelDia.add(new GrillaTurnoDTO(horaActual, "LIBRE", null));
                    }
                    
                    horaActual = horaActual.plusHours(1);
                }
            }
            grillaSemanal.put(diaActual, turnosDelDia);
        }
        return grillaSemanal;
    }
}
