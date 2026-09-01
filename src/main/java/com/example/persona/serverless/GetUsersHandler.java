package com.example.persona.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;
import java.util.Map;

public class GetUsersHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final List<Map<String, Object>> USERS = List.of(
            Map.of("id", "1", "nombre", "Ana", "email", "ana@email.com"),
            Map.of("id", "2", "nombre", "Luis", "email", "luis@email.com")
    );

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
            String id = pathParameters != null ? pathParameters.get("id") : null;

            if (id == null || id.isEmpty()) {
                return ApiGatewayResponse.builder()
                        .setStatusCode(400)
                        .setRawBody("{\"message\":\"Se requiere el id del usuario\"}")
                        .build();
            }

            var usuario = USERS.stream()
                    .filter(u -> u.get("id").equals(id))
                    .findFirst();

            if (usuario.isEmpty()) {
                return ApiGatewayResponse.builder()
                        .setStatusCode(404)
                        .setRawBody("{\"message\":\"Usuario no encontrado\"}")
                        .build();
            }

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(usuario.get())
                    .build();
        } catch (Exception e) {
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setRawBody("{\"message\":\"Error al consultar usuario\"}")
                    .build();
        }
    }
}
