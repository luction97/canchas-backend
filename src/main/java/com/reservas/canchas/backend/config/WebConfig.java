package com.reservas.canchas.backend.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // --- CONFIGURACIÓN PARA DESARROLLO ---
        // Permite peticiones desde cualquier origen. Esto es ideal para las pruebas locales
        // y en dispositivos móviles, ya que no tenemos que preocuparnos por la IP cambiante.
        configuration.setAllowedOriginPatterns(List.of("*"));
        
        // --- CONFIGURACIÓN PARA PRODUCCIÓN (EJEMPLO) ---
        // Cuando despliegues tu aplicación, deberías comentar la línea de arriba y
        // descomentar esta, reemplazando la URL por tu dominio real.
        // configuration.setAllowedOrigins(List.of("https://www.tuapp.com"));

        
        // Configuración estándar de métodos, cabeceras y credenciales.
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplicamos esta configuración a todas las rutas que comiencen con /api/
        source.registerCorsConfiguration("/api/**", configuration);
        
        return source;
    }
}