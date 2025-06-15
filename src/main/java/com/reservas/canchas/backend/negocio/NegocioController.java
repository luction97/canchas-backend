package com.reservas.canchas.backend.negocio;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservas.canchas.backend.negocio.dto.NegocioDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.reservas.canchas.backend.cancha.dto.CrearCanchaDTO;
import com.reservas.canchas.backend.negocio.dto.CrearNegocioDTO;

@RestController
@RequestMapping("/api/negocios")
@Tag(name = "Gestión de Negocios", description = "Endpoints para crear y consultar los complejos deportivos.")
public class NegocioController {

    private final NegocioService negocioService;

    public NegocioController(NegocioService negocioService) {
        this.negocioService = negocioService;
    }

    @Operation(summary = "Crear nuevo negocio", description = "Crea un nuevo complejo de canchas en el sistema.")
    @PostMapping // Maneja las peticiones POST de /api/negocios
    public ResponseEntity<NegocioDTO> crearNegocio(@RequestBody CrearNegocioDTO crearDto) {

        NegocioDTO negocioCreado = negocioService.crearNegocio(crearDto);
        return new ResponseEntity<>(negocioCreado, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los negocios", description = "Devuelve una lista de todos los negocios registrados.") // Descripción
                                                                                                                              // del
                                                                                                                              // endpoint
    @GetMapping
    public List<NegocioDTO> obtenerTodosLosNegocios() {
        return negocioService.obtenerTodosLosNegocios();
    }

    // --- MÉTODO DE PRUEBA TEMPORAL ---
    @PostMapping("/test-cancha") // Usamos una URL de prueba para no chocar con la otra
    public void testRecibirCancha(@RequestBody CrearCanchaDTO dtoDePrueba) {
        System.out.println("--- PRUEBA EN NEGOCIO CONTROLLER ---");
        System.out.println("DTO de prueba recibido: " + dtoDePrueba.toString());
        System.out.println("------------------------------------");
    }

}
