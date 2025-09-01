package com.reservas.canchas.backend.turno;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservas.canchas.backend.turno.dto.CrearTurnoDTO;
import com.reservas.canchas.backend.turno.dto.TurnoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/canchas/{idCancha}/turnos")
@Tag(name = "Turnos", description = "API para gestionar turnos de canchas")
public class TurnoController {

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @Operation(summary = "Crear un nuevo turno", description = "Crea un nuevo turno para una cancha específica")
    @PostMapping
    public ResponseEntity<TurnoDto> crearTurno(
            @PathVariable Long idCancha,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del turno a crear", required = true) CrearTurnoDTO crearTurnoDto) {
        TurnoDto turnoCreado = turnoService.crearTurno(idCancha, crearTurnoDto);
        return new ResponseEntity<>(turnoCreado, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener turnos por día", description = "Obtiene todos los turnos de una cancha para un día específico")
    @GetMapping
    public List<TurnoDto> obtenerTurnosPorDia(
            @PathVariable Long idCancha,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return turnoService.obtenerTurnosPorDia(idCancha, fecha);
    }

   /* @GetMapping("/public/grilla-semanal")
    public Map<LocalDate, List<GrillaTurnoDTO>> obtenerGrillaSemanal(
            @PathVariable Long idCancha,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_inicio) {
        return turnoService.generarGrillaSemanal(idCancha, fecha_inicio);
    } */
}
