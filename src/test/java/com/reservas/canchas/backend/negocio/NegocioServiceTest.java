package com.reservas.canchas.backend.negocio;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.reservas.canchas.backend.negocio.dto.NegocioDTO;
import com.reservas.canchas.backend.negocio.dto.CrearNegocioDTO;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class) // Activa el soporte de Mockito para pruebas unitarias
public class NegocioServiceTest {

    @Mock // Crea un mock de NegocioMapper para simular su comportamiento
    private NegocioRepository negocioRepository;

    @Mock
    private NegocioMapper negocioMapper;

    @InjectMocks // Inyecta los mocks en la instancia de NegocioService
    private NegocioService negocioService;

    @Test
    void creaNegocio_devuelveNegocioConId() {
        // 1. Preparar los datos de prueba
        CrearNegocioDTO peticionDTO = new CrearNegocioDTO("Nombre Negocio", "identificador-url", "123456789");
        // Preparamos entidades falsas que simulan la respuesta de la base de datos y el
        // mapeo.
        Negocio negocioSinId = new Negocio();
        negocioSinId.setNombre("negocio de prueba");

        Negocio negocioConId = new Negocio();
        negocioConId.setId(1L);
        negocioConId.setNombre("negocio de prueba");

        // 2. Configurar el comportamiento del mock

        // Le enseñamos a nuestros "mocks" cómo deben comportarse.
        // "Cuando se llame al método toEntity del mapper con el DTO de la petición..."
        when(negocioMapper.toEntity(peticionDTO)).thenReturn(negocioSinId);

        // "Cuando se llame al método save del repositorio con el objeto
        // negocioSinId..."
        when(negocioRepository.save(negocioSinId)).thenReturn(negocioConId);

        // "Cuando se llame al método toDto del mapper con el objeto negocioConId..."
        when(negocioMapper.toDto(negocioConId))
                .thenReturn(new NegocioDTO(1L, "Negocio de Prueba", "negocio-prueba", "12345"));

        // --- 2. Act (Actuar) ---

        // Llamamos al método que realmente queremos probar.
        NegocioDTO resultado = negocioService.crearNegocio(peticionDTO);

        // --- 3. Assert (Verificar) ---

        // Verificamos que los resultados sean los que esperábamos.
        assertThat(resultado).isNotNull(); // El resultado no debe ser nulo.
        assertThat(resultado.id()).isEqualTo(1L); // El ID debe ser el que simulamos.
        assertThat(resultado.nombre()).isEqualTo("Negocio de Prueba");

        // (Opcional pero recomendado) Verificamos que las interacciones ocurrieron.
        verify(negocioRepository).save(negocioSinId); // Verificamos que el método save() fue llamado una vez.
        verify(negocioMapper).toDto(negocioConId); // Verificamos que el método toDto() fue llamado.

    }


    @Test
    void obtenerTodosLosNegocios_devuelveListaDeNegocios() {
        // 1. Preparar los datos de prueba
        Negocio negocio1 = new Negocio();
        negocio1.setId(1L);
        negocio1.setNombre("Negocio 1");

        Negocio negocio2 = new Negocio();
        negocio2.setId(2L);
        negocio2.setNombre("Negocio 2");

        // Simulamos una lista de negocios que sería la respuesta de la base de datos.
        List<Negocio> listaNegocios = List.of(negocio1, negocio2);

        // 2. Configurar el comportamiento del mock
        when(negocioRepository.findAll()).thenReturn(listaNegocios);

        // Simulamos el mapeo de los negocios a DTOs.
        when(negocioMapper.toDtoList(listaNegocios))
                .thenReturn(List.of(new NegocioDTO(1L, "Negocio 1", null, null),
                        new NegocioDTO(2L, "Negocio 2", null, null)));

        // --- 2. Act (Actuar) ---

        // Llamamos al método que queremos probar.
        List<NegocioDTO> resultado = negocioService.obtenerTodosLosNegocios();

        // --- 3. Assert (Verificar) ---

        // Verificamos que el resultado no sea nulo y tenga el tamaño esperado.
        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(2); // Debe haber dos negocios en la lista.

        // Verificamos que los nombres de los negocios sean los esperados.
        assertThat(resultado.get(0).nombre()).isEqualTo("Negocio 1");
        assertThat(resultado.get(1).nombre()).isEqualTo("Negocio 2");

        // Verificamos que las interacciones ocurrieron.
        verify(negocioRepository).findAll(); // Verificamos que se llamó al método findAll().
    }

}
