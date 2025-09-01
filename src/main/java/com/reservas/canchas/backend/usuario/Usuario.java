package com.reservas.canchas.backend.usuario;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.reservas.canchas.backend.negocio.Negocio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_id_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_id_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL) // Si se crea un usuario, se crea un negocio asociado
    @JoinColumn(name = "negocio_id", referencedColumnName = "id")
    private Negocio negocio;
    
    // Métodos de la interfaz UserDetails de Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Para el MVP, no usamos roles complejos.
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    // Dejamos todo en true para el MVP
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
