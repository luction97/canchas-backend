package com.reservas.canchas.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para el registro de un nuevo usuario y su negocio asociado.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    // Datos del Usuario
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    // Datos del Negocio asociado
    @NotBlank(message = "El nombre del negocio es obligatorio")
    private String nombreNegocio;
    
    @NotBlank(message = "El identificador URL es obligatorio")
    private String identificadorUrl;
    
    @NotBlank(message = "El número de celular es obligatorio")
    private String nroCelular;
}
