package com.reservas.canchas.backend.turno;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    // --- MÉTODO FINAL Y CORREGIDO PARA GENERAR LA GRILLA SEMANAL ---
    public Map<LocalDate, List<GrillaTurnoDTO>> generarGrillaSemanal(Long idCancha, LocalDate fechaInicioSemana) {
        // 1. Buscamos todos los turnos ya reservados para la semana completa en una
        // sola consulta.
        LocalDateTime inicioSemana = fechaInicioSemana.atStartOfDay();
        LocalDateTime finSemana = fechaInicioSemana.plusDays(7).atStartOfDay();
        List<Turno> turnosDeLaSemana = turnoRepository.findByCanchaIdAndFechaHoraInicioBetween(idCancha, inicioSemana,
                finSemana);

        // 2. Creamos un mapa para poder buscar rápidamente si un turno está ocupado. La
        // clave será la fecha y hora exactas, y el valor será el estado del turno.
        Map<LocalDateTime, String> mapaTurnosOcupados = turnosDeLaSemana.stream()
                .collect(Collectors.toMap(Turno::getFechaHoraInicio, turno -> turno.getEstado().name()));

        // 3. Preparamos el mapa que devolveremos. Usamos LinkedHashMap para que los
        // días se mantengan en orden.
        Map<LocalDate, List<GrillaTurnoDTO>> grillaSemanal = new LinkedHashMap<>();

        // 4. Iteramos por cada uno de los 7 días de la semana que queremos mostrar.
        for (int i = 0; i < 7; i++) {
            LocalDate diaActual = fechaInicioSemana.plusDays(i);

            // Obtenemos el DayOfWeek estándar de Java para poder buscar en nuestras reglas.
            DayOfWeek diaDeLaSemanaJava = diaActual.getDayOfWeek();
            DiaSemana nuestroDiaDeLaSemana = DiaSemana.fromJavaDayOfWeek(diaDeLaSemanaJava);

            List<GrillaTurnoDTO> turnosDelDia = new ArrayList<>();

            // Buscamos las reglas de horario (ej: "Lunes de 18 a 23") para este día, usando
            // nuestro Enum.
            List<HorarioDisponible> rangosHorario = horarioRepository.findByCanchaIdAndDiaSemana(idCancha,
                    nuestroDiaDeLaSemana);

            // 5. Generamos los "slots" de una hora para cada rango de horario que
            // encontramos.
            for (HorarioDisponible rango : rangosHorario) {
                LocalTime horaActual = rango.getHoraInicio();
                while (horaActual.isBefore(rango.getHoraFin())) {
                    LocalDateTime fechaHoraActual = diaActual.atTime(horaActual);

                    // Usamos el mapa para ver si el slot está ocupado. Si no está en el mapa, está
                    // "LIBRE".
                    String estado = mapaTurnosOcupados.getOrDefault(fechaHoraActual, "LIBRE");
                    turnosDelDia.add(new GrillaTurnoDTO(horaActual, estado));

                    horaActual = horaActual.plusHours(1); // Pasamos a la siguiente hora
                }
            }
            grillaSemanal.put(diaActual, turnosDelDia);
        }

        return grillaSemanal;
    }

}
