# Persona API

API REST en Java 17, Spring Boot, arquitectura hexagonal y PostgreSQL en AWS RDS.

## Configuracion de RDS

Copie `.env.example` como `.env` y complete los datos de su RDS:

```text
DB_HOST=tu-endpoint.rds.amazonaws.com
DB_PORT=5432
DB_NAME=persona
DB_USERNAME=tu-usuario
DB_PASSWORD=tu-contrasena
DB_SSLMODE=require
```

El archivo `.env` es ignorado por Git y no debe publicarse.

La base de datos debe permitir conexiones entrantes desde el equipo o servidor
donde se ejecuta la API, mediante el puerto `5432`.

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
- `infrastructure/adapter/out/persistence`: adaptador JPA y conexión PostgreSQL.
