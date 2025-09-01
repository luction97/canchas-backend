package com.reservas.canchas.backend.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reservas.canchas.backend.auth.dto.AuthResponse;
import com.reservas.canchas.backend.auth.dto.LoginRequest;
import com.reservas.canchas.backend.auth.dto.RegisterRequest;
import com.reservas.canchas.backend.config.JwtService;
import com.reservas.canchas.backend.negocio.Negocio;
import com.reservas.canchas.backend.usuario.Usuario;
import com.reservas.canchas.backend.usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        var negocio = Negocio.builder()
                .nombre(request.getNombreNegocio())
                .identificadorUrl(request.getIdentificadorUrl())
                .nroCelular(request.getNroCelular())
                .build();

        var user = Usuario.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .negocio(negocio)
                .build();

        usuarioRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}
