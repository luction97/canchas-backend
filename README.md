Canchas Backend - API de Reserva de Turnos
Este repositorio contiene el código fuente del backend para un micro-SaaS de reserva de turnos para complejos deportivos. La aplicación está construida como una API RESTful utilizando el stack de Spring Boot y sigue las mejores prácticas de la industria como Clean Code, Arquitectura en Capas y Testing.

✨ Características del MVP
El estado actual del proyecto (MVP) permite las siguientes funcionalidades:

Gestión Multi-Tenancy (Negocios): Permite registrar múltiples complejos deportivos, cada uno con su propia información.

Gestión de Canchas: Cada negocio puede crear y administrar sus propias canchas.

Gestión de Horarios Semanales: Los dueños de los negocios pueden definir sus horarios de apertura y cierre para cada día de la semana en cada una de sus canchas.

Gestión de Turnos: Permite marcar turnos específicos en la grilla como RESERVADO o BUSCA_RIVAL.

Consulta de Disponibilidad: Ofrece endpoints para consultar los turnos ocupados de un día específico, permitiendo al frontend generar una grilla de disponibilidad.

Documentación de API Interactiva: Integración con Swagger UI para una documentación clara y la posibilidad de probar los endpoints directamente desde el navegador.

🛠️ Stack Tecnológico
Componente

Tecnología

Propósito

Lenguaje

Java 17

Lenguaje de programación principal.

Framework

Spring Boot 3.x

Framework principal para el desarrollo rápido y robusto de la API.

Persistencia

Spring Data JPA / Hibernate

Capa de abstracción para el acceso a la base de datos (ORM).

Base de Datos

PostgreSQL

Base de datos relacional para producción y desarrollo.

Seguridad

Spring Security

Gestión de autenticación y autorización.

Documentación

Springdoc OpenAPI (Swagger UI)

Generación automática de documentación de API interactiva.

Herramientas de Build

Maven

Gestor de dependencias y construcción del proyecto.

Desarrollo Local

Docker & Docker Compose

Para ejecutar la base de datos en un entorno aislado y consistente.

Testing

JUnit 5 & Mockito

Frameworks para pruebas unitarias.

Mapeo de Objetos

MapStruct

Generación de código para mapeo entre DTOs y Entidades.

Utilidades

Lombok

Reducción de código boilerplate (getters, setters, etc.).

🚀 Cómo Empezar (Desarrollo Local)
Sigue estos pasos para levantar el proyecto en tu máquina local.

Prerrequisitos
JDK 17 (o superior) instalado.

Maven instalado (o puedes usar el Maven Wrapper incluido).

Docker y Docker Compose instalados y corriendo.

Pasos de Instalación
Clonar el repositorio:

git clone https://github.com/tu-usuario/canchas-backend.git
cd canchas-backend

Levantar la Base de Datos con Docker:
En la raíz del proyecto, ejecuta el siguiente comando. Esto iniciará un contenedor de PostgreSQL con la configuración necesaria.

docker compose up -d

Configurar el application.properties:
El proyecto está configurado para conectarse a la base de datos de Docker por defecto. Asegúrate de que tu archivo src/main/resources/application.properties contenga:

spring.datasource.url=jdbc:postgresql://localhost:5433/canchas_db
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=create

Nota: ddl-auto=create borrará y recreará la base de datos en cada reinicio, ideal para desarrollo.

Ejecutar la Aplicación:
Puedes ejecutar la aplicación de dos maneras:

Desde tu IDE: Abre la clase CanchasBackendApplication.java y ejecútala.

Desde la terminal:

./mvnw spring-boot:run

¡Listo! La API estará corriendo en http://localhost:8080.

📖 Documentación de la API
Una vez que la aplicación esté corriendo, puedes acceder a la documentación interactiva de la API generada por Swagger UI.

URL de la UI: http://localhost:8080/swagger-ui.html

Desde esta interfaz podrás ver todos los endpoints disponibles, sus parámetros, los cuerpos de las peticiones y las respuestas esperadas. También puedes probar cada endpoint directamente desde el navegador.

📂 Estructura del Proyecto
El proyecto sigue una arquitectura de Paquetes por Funcionalidad para mantener el código organizado, cohesivo y escalable. En lugar de agrupar las clases por su capa técnica (controllers, services, etc.), se agrupan por el dominio de negocio al que pertenecen (negocio, cancha, turno). Esto facilita enormemente la navegación y el mantenimiento del código.

com.reservas.canchas.backend
├── negocio/
│   ├── Negocio.java
│   ├── NegocioController.java
│   └── dto/
│       └── NegocioDTO.java
├── cancha/
│   ├── Cancha.java
│   └── horario/
│       └── HorarioDisponible.java
└── turno/
    └── Turno.java
