package com.example.persona.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class CreateUsuarioHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Map<String, Map<String, Object>> USERS = new HashMap<>();

    static {
        USERS.put("1", Map.of("id", "1", "nombre", "Ana", "email", "ana@email.com"));
        USERS.put("2", Map.of("id", "2", "nombre", "Luis", "email", "luis@email.com"));
    }

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            Map<String, Object> body = input == null ? new HashMap<>() : input;
            String id = String.valueOf(body.getOrDefault("id", "3"));
            String nombre = String.valueOf(body.getOrDefault("nombre", "Nuevo"));
            String email = String.valueOf(body.getOrDefault("email", "nuevo@email.com"));

            Map<String, Object> user = new HashMap<>();
            user.put("id", id);
            user.put("nombre", nombre);
            user.put("email", email);
            USERS.put(id, user);

            return ApiGatewayResponse.builder()
                    .setStatusCode(201)
                    .setObjectBody(user)
                    .build();
        } catch (Exception e) {
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setRawBody("{\"message\":\"Error al crear usuario\"}")
                    .build();
        }
    }
}
