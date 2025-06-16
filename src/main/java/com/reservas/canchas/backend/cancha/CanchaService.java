package com.reservas.canchas.backend.cancha;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reservas.canchas.backend.cancha.dto.CanchaDTO;
import com.reservas.canchas.backend.cancha.dto.CrearCanchaDTO;
import com.reservas.canchas.backend.cancha.horario.HorarioDisponible;
import com.reservas.canchas.backend.cancha.horario.HorarioDisponibleRepository;
import com.reservas.canchas.backend.cancha.horario.HorarioMapper;
import com.reservas.canchas.backend.cancha.horario.dto.CrearHorarioDTO;
import com.reservas.canchas.backend.cancha.horario.dto.HorarioDTO;
import com.reservas.canchas.backend.negocio.Negocio;
import com.reservas.canchas.backend.negocio.NegocioRepository;

@Service
public class CanchaService {

    private final CanchaRepository canchaRepository;
    private final CanchaMapper canchaMapper;
    private final NegocioRepository negocioRepository;
    private final HorarioDisponibleRepository horarioRepository;
    private final HorarioMapper horarioMapper;

    public CanchaService(CanchaRepository canchaRepository, NegocioRepository negocioRepository,
            CanchaMapper canchaMapper, HorarioDisponibleRepository horarioRepository,
            HorarioMapper horarioMapper) { // <-- Se agregan al constructor
        this.canchaRepository = canchaRepository;
        this.negocioRepository = negocioRepository;
        this.canchaMapper = canchaMapper;
        this.horarioRepository = horarioRepository;
        this.horarioMapper = horarioMapper;
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

    public HorarioDTO agregarHorario(Long idCancha, CrearHorarioDTO horarioDTO) {
        // 1. Buscamos la cancha a la que le agregaremos el horario.
        Cancha cancha = canchaRepository.findById(idCancha)
                .orElseThrow(() -> new RuntimeException("Cancha no encontrada con ID: " + idCancha));

        // 2. Convertimos el DTO a la entidad HorarioDisponible.
        HorarioDisponible nuevoHorario = horarioMapper.toEntity(horarioDTO);

        // 3. Establecemos la relación.
        nuevoHorario.setCancha(cancha);

        // 4. Guardamos la nueva regla de horario.
        HorarioDisponible horarioGuardado = horarioRepository.save(nuevoHorario);

        // 5. Devolvemos el DTO de respuesta.
        return horarioMapper.toDto(horarioGuardado);
    }

    public List<HorarioDTO> obtenerHorariosPorCancha(Long idCancha) {
        // Verificamos que la cancha exista antes de buscar sus horarios
        if (!canchaRepository.existsById(idCancha)) {
            throw new RuntimeException("Cancha no encontrada con ID: " + idCancha);
        }
        List<HorarioDisponible> horarios = horarioRepository.findByCanchaId(idCancha);
        return horarioMapper.toDtoList(horarios);
    }

}
