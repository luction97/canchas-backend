package com.reservas.canchas.backend.cancha;

import com.reservas.canchas.backend.cancha.dto.CanchaDTO;
import com.reservas.canchas.backend.cancha.dto.CrearCanchaDTO;
import com.reservas.canchas.backend.cancha.horario.dto.CrearHorarioDTO;
import com.reservas.canchas.backend.cancha.horario.dto.HorarioDTO;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/canchas") // <-- Usando la ruta correcta y sin ambigüedades
public class CanchaController {

    private final CanchaService canchaService;

    public CanchaController(CanchaService canchaService) {
        this.canchaService = canchaService;
    }

    @Operation(summary = "Crear nueva cancha", description = "Crea una nueva cancha relacionada a un complejo.")
    @PostMapping
    public ResponseEntity<CanchaDTO> crearCancha(@RequestBody CrearCanchaDTO crearDto) {
        CanchaDTO canchaCreada = canchaService.crearCancha(crearDto);
        return new ResponseEntity<>(canchaCreada, HttpStatus.CREATED);
    }

    @PostMapping("/{idCancha}/horarios")
    public ResponseEntity<HorarioDTO> agregarHorario(
            @PathVariable Long idCancha,
            @RequestBody CrearHorarioDTO crearHorarioDTO) {
        HorarioDTO horarioCreado = canchaService.agregarHorario(idCancha, crearHorarioDTO);
        return new ResponseEntity<>(horarioCreado, HttpStatus.CREATED);
    }

    @GetMapping("/{idCancha}/horarios")
    public List<HorarioDTO> obtenerHorariosPorCancha(@PathVariable Long idCancha) {
        return canchaService.obtenerHorariosPorCancha(idCancha);
    }

}