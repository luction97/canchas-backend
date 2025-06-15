package com.reservas.canchas.backend.cancha;

import com.reservas.canchas.backend.cancha.dto.CanchaDTO;
import com.reservas.canchas.backend.cancha.dto.CrearCanchaDTO;
import io.swagger.v3.oas.annotations.Operation;
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
}