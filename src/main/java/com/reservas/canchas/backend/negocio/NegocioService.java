package com.reservas.canchas.backend.negocio;

import java.util.List;
import org.springframework.stereotype.Service;

import com.reservas.canchas.backend.negocio.dto.NegocioDTO;
import com.reservas.canchas.backend.negocio.dto.CrearNegocioDTO;

@Service
public class NegocioService {

    private final NegocioRepository negocioRepository;
    private final NegocioMapper negocioMapper;

    public NegocioService(NegocioRepository negocioRepository, NegocioMapper negocioMapper) {
        this.negocioRepository = negocioRepository;
        this.negocioMapper = negocioMapper;
    }

    // El método ahora recibe el DTO correcto: CrearNegocioDTO
    public NegocioDTO crearNegocio(CrearNegocioDTO crearDto) {
        Negocio nuevoNegocio = negocioMapper.toEntity(crearDto);
        Negocio negocioGuardado = negocioRepository.save(nuevoNegocio);
        return negocioMapper.toDto(negocioGuardado);
    }

    public List<NegocioDTO> obtenerTodosLosNegocios() {
        List<Negocio> negocios = negocioRepository.findAll();
        return negocioMapper.toDtoList(negocios);
    }
}
