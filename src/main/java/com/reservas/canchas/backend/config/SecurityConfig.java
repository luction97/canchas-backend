package com.reservas.canchas.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configuración de seguridad básica
        http
                .csrf().disable() // Deshabilita seguridad para desarrollo local
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Permite todas las peticiones
                .formLogin().disable(); // Deshabilita el formulario de inicio de sesión
        return http.build();
    }

}
