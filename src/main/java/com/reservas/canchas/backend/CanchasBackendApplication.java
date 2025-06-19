package com.reservas.canchas.backend;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class CanchasBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CanchasBackendApplication.class, args);

	}

	@PostConstruct
	public void init() {
		// Establecemos la zona horaria por defecto de toda la aplicación a la de
		// Argentina.
		TimeZone.setDefault(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
	}

}
