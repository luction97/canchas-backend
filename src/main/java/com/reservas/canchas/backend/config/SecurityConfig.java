package com.reservas.canchas.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // --- ¡AQUÍ ESTÁ LA NUEVA LÍNEA! ---
                // Le decimos a Spring Security que use la configuración de CORS que definimos
                // en nuestro bean 'corsConfigurationSource'.
                .cors(withDefaults())

                // Deshabilitamos CSRF, una práctica común para APIs REST stateless.
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // Tus reglas de autorización (estas no cambian)
                        .requestMatchers("/api/public/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        //.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(withDefaults());

        return http.build();
    }
}
