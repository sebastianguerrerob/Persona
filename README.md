# Persona API

API REST en Java 17, Spring Boot y arquitectura hexagonal.

## Ejecutar

Requiere Java 17 y Gradle instalado:

```bash
gradle bootRun
```

La API queda disponible en `http://localhost:8080`.

Swagger UI: `http://localhost:8080/swagger-ui.html`

Documentacion OpenAPI: `http://localhost:8080/v3/api-docs`

## Endpoints

### Guardar persona

`POST /api/personas/guardarpersona`

```json
{
  "identificacion": "123456789",
  "nombre": "Ana Perez",
  "email": "ana.perez@example.com"
}
```

Respuesta: `201 Created`.

### Consultar persona

`GET /api/personas/consultarpersona/{identificacion}`

Respuesta: `200 OK`, o `404 Not Found` si no existe.

## Arquitectura

- `domain`: contiene `api`, `exception`, `model`, `spi` y `usecase`, sin dependencias de Spring.
- `application`: casos de uso.
- `infrastructure/adapter/in/web`: adaptador REST.
- `infrastructure/adapter/out/persistence`: adaptador JPA y base H2 en memoria.
