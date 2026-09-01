package com.example.persona.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import java.util.HashMap;
import java.util.Map;

public class GetUsersHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final String TABLE_NAME = System.getenv().getOrDefault("TABLE_NAME", "personas");
    private static final DynamoDbClient DYNAMO_DB = DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .build();

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

            Map<String, AttributeValue> item = DYNAMO_DB.getItem(GetItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(Map.of("id", AttributeValue.fromS(id)))
                    .build()).item();

            if (item == null || item.isEmpty()) {
                return ApiGatewayResponse.builder()
                        .setStatusCode(404)
                        .setRawBody("{\"message\":\"Usuario no encontrado\"}")
                        .build();
            }

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(toUserMap(item))
                    .build();
        } catch (Exception e) {
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setRawBody("{\"message\":\"Error al consultar usuario\"}")
                    .build();
        }
    }

    private Map<String, Object> toUserMap(Map<String, AttributeValue> item) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", item.getOrDefault("id", AttributeValue.fromS(" ")).s());
        user.put("nombre", item.getOrDefault("nombre", AttributeValue.fromS(" ")).s());
        user.put("email", item.getOrDefault("email", AttributeValue.fromS(" ")).s());
        return user;
    }
}
