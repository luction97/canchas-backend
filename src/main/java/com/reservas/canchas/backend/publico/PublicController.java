package com.reservas.canchas.backend.publico;

import com.reservas.canchas.backend.turno.TurnoService;
import com.reservas.canchas.backend.turno.dto.GrillaPublicaDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/public") // Todas las rutas aquí dentro serán públicas
public class PublicController {

    private final TurnoService turnoService;

    public PublicController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping("/canchas/{idCancha}/grilla-semanal")
    public GrillaPublicaDTO obtenerGrillaSemanal(
            @PathVariable Long idCancha,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_inicio) {
        return turnoService.generarGrillaSemanal(idCancha, fecha_inicio);
    }
}
