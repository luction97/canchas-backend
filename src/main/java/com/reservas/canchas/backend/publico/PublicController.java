package com.reservas.canchas.backend.publico;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservas.canchas.backend.turno.TurnoService;
import com.reservas.canchas.backend.turno.dto.GrillaTurnoDTO;

@RestController
@RequestMapping("/api/public") // Todas las rutas aquí dentro serán públicas
public class PublicController {

    private final TurnoService turnoService;

    public PublicController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping("/canchas/{idCancha}/grilla-semanal")
    public Map<LocalDate, List<GrillaTurnoDTO>> obtenerGrillaSemanal(
            @PathVariable Long idCancha,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_inicio) {
        return turnoService.generarGrillaSemanal(idCancha, fecha_inicio);
    }
}
