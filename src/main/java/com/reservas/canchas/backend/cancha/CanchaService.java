package com.reservas.canchas.backend.cancha;

import org.springframework.stereotype.Service;

import com.reservas.canchas.backend.cancha.dto.CanchaDTO;
import com.reservas.canchas.backend.cancha.dto.CrearCanchaDTO;
import com.reservas.canchas.backend.negocio.Negocio;
import com.reservas.canchas.backend.negocio.NegocioRepository;

@Service
public class CanchaService {

    private final CanchaRepository canchaRepository;
    private final CanchaMapper canchaMapper;
    private final NegocioRepository negocioRepository;

    public CanchaService(CanchaRepository canchaRepository, CanchaMapper canchaMapper,
            NegocioRepository negocioRepository) {
        this.canchaRepository = canchaRepository;
        this.canchaMapper = canchaMapper;
        this.negocioRepository = negocioRepository;
    }

    public CanchaDTO crearCancha(CrearCanchaDTO canchaDTO) {

        // 1. Buscamos la entidad "padre" (el Negocio) a la que pertenecerá esta cancha.
        Negocio negocioAsociado = negocioRepository.findById(canchaDTO.negocioId())
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado con ID: " + canchaDTO.negocioId()));

        // 2. Usamos el mapper UNA SOLA VEZ para crear la nueva entidad Cancha.
        Cancha nuevaCancha = canchaMapper.toEntity(canchaDTO);

        // 3. Establecemos la relación: le decimos a la nueva cancha quién es su dueño.
        nuevaCancha.setNegocio(negocioAsociado);

        Cancha canchaGuardada = canchaRepository.save(nuevaCancha);

        // 5. Mapeamos la entidad ya guardada (con su ID y todo) a un DTO para la
        // respuesta.
        return canchaMapper.toDto(canchaGuardada);

    }

}
