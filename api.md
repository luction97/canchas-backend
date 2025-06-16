# Documentación de API - MVP Reserva de Canchas

Este documento describe los endpoints disponibles en la API del backend para la gestión de complejos deportivos, canchas y turnos.

## Flujo Lógico y Orden de Ejecución

Debido a las relaciones en la base de datos, los endpoints de creación (`POST`) deben ser llamados en un orden específico para mantener la integridad de los datos.

1.  **Crear `Negocio`:** Es la entidad raíz. Se debe crear primero.
2.  **Crear `Cancha`:** Requiere el `id` de un `Negocio` existente.
3.  **Crear `HorarioDisponible`:** Requiere el `id` de una `Cancha` existente.
4.  **Crear `Turno`:** Requiere el `id` de una `Cancha` existente.

---

## Documentación de Endpoints

### 1. Gestión de Negocios

#### **`POST /api/negocios`**
* **Descripción:** Crea un nuevo complejo deportivo (negocio).
* **Request Body:**
    ```json
    {
        "nombre": "El Potrero de Paraná",
        "identificadorUrl": "el-potrero-parana",
        "nroCelular": "543435123456"
    }
    ```
* **Response Exitoso (201 Created):**
    ```json
    {
        "id": 1,
        "nombre": "El Potrero de Paraná",
        "identificadorUrl": "el-potrero-parana",
        "nroCelular": "543435123456"
    }
    ```

#### **`GET /api/negocios`**
* **Descripción:** Devuelve una lista de todos los negocios registrados.
* **Request Body:** Ninguno.
* **Response Exitoso (200 OK):**
    ```json
    [
        {
            "id": 1,
            "nombre": "El Potrero de Paraná",
            "identificadorUrl": "el-potrero-parana",
            "nroCelular": "543435123456"
        }
    ]
    ```

---

### 2. Gestión de Canchas

#### **`POST /api/canchas`**
* **Descripción:** Crea una nueva cancha y la asocia a un negocio existente.
* **Request Body:**
    ```json
    {
        "nombre": "Cancha 1 - Fútbol 5",
        "descripcion": "Césped sintético nuevo",
        "idNegocio": 1
    }
    ```
* **Response Exitoso (201 Created):**
    ```json
    {
        "id": 1,
        "nombre": "Cancha 1 - Fútbol 5",
        "descripcion": "Césped sintético nuevo",
        "idNegocio": 1
    }
    ```

#### **`GET /api/canchas/{idCancha}`**
* **Descripción:** Obtiene los detalles de una cancha específica por su ID.
* **Request Body:** Ninguno.
* **Response Exitoso (200 OK):** (Ejemplo para `/api/canchas/1`)
    ```json
    {
        "id": 1,
        "nombre": "Cancha 1 - Fútbol 5",
        "descripcion": "Césped sintético nuevo",
        "idNegocio": 1
    }
    ```
---

### 3. Gestión de Horarios Semanales

#### **`POST /api/canchas/{idCancha}/horarios`**
* **Descripción:** Agrega una nueva regla de horario a la plantilla semanal de una cancha específica.
* **Request Body:** (Ejemplo para `/api/canchas/1/horarios`)
    ```json
    {
      "diaSemana": "LUNES",
      "horaInicio": "18:00:00",
      "horaFin": "23:00:00"
    }
    ```
* **Response Exitoso (201 Created):**
    ```json
    {
        "id": 1,
        "diaSemana": "LUNES",
        "horaInicio": "18:00:00",
        "horaFin": "23:00:00"
    }
    ```

#### **`GET /api/canchas/{idCancha}/horarios`**
* **Descripción:** Devuelve una lista con todas las reglas de horario para una cancha específica.
* **Request Body:** Ninguno.
* **Response Exitoso (200 OK):** (Ejemplo para `/api/canchas/1/horarios`)
    ```json
    [
        {
            "id": 1,
            "diaSemana": "LUNES",
            "horaInicio": "18:00:00",
            "horaFin": "23:00:00"
        }
    ]
    ```
---

### 4. Gestión de Turnos (Reservas)

#### **`POST /api/canchas/{idCancha}/turnos`**
* **Descripción:** Crea un nuevo turno (reserva) para una cancha en una fecha y hora específicas.
* **Request Body:** (Ejemplo para `/api/canchas/1/turnos`)
    ```json
    {
        "fechaHoraInicio": "2025-06-23T20:00:00",
        "estado": "RESERVADO"
    }
    ```
* **Response Exitoso (201 Created):**
    ```json
    {
        "id": 1,
        "fechaHoraInicio": "2025-06-23T20:00:00",
        "estado": "RESERVADO"
    }
    ```

#### **`GET /api/canchas/{idCancha}/turnos`**
* **Descripción:** Devuelve una lista de los turnos ya marcados para una cancha en un día específico.
* **URL con Parámetros:** `/api/canchas/1/turnos?fecha=2025-06-23`
* **Request Body:** Ninguno.
* **Response Exitoso (200 OK):**
    ```json
    [
        {
            "id": 1,
            "fechaHoraInicio": "2025-06-23T20:00:00",
            "estado": "RESERVADO"
        }
    ]
    ```