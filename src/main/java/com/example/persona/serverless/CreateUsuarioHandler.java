package com.example.persona.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateUsuarioHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final String TABLE_NAME = System.getenv().getOrDefault("TABLE_NAME", "personas");
    private static final String QUEUE_URL = System.getenv().getOrDefault("QUEUE_URL", "");
    private static final DynamoDbClient DYNAMO_DB = DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .build();
    private static final SqsClient SQS_CLIENT = SqsClient.builder()
            .region(Region.US_EAST_1)
            .build();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            String rawBody = input != null && input.get("body") != null ? String.valueOf(input.get("body")) : "{}";
            Map<String, Object> body = OBJECT_MAPPER.readValue(rawBody, new TypeReference<Map<String, Object>>() {});

            String id = String.valueOf(body.getOrDefault("id", ""));
            String nombre = String.valueOf(body.getOrDefault("nombre", ""));
            String email = String.valueOf(body.getOrDefault("email", ""));

            if (id.isBlank() || nombre.isBlank() || email.isBlank()) {
                return ApiGatewayResponse.builder()
                        .setStatusCode(400)
                        .setRawBody("{\"message\":\"Los campos id, nombre y email son obligatorios\"}")
                        .build();
            }

            Map<String, Object> user = new HashMap<>();
            user.put("id", id);
            user.put("nombre", nombre);
            user.put("email", email);

            DYNAMO_DB.putItem(PutItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .item(Map.of(
                            "id", AttributeValue.fromS(id),
                            "nombre", AttributeValue.fromS(nombre),
                            "email", AttributeValue.fromS(email)
                    ))
                    .build());

            if (!QUEUE_URL.isBlank()) {
                SQS_CLIENT.sendMessage(SendMessageRequest.builder()
                        .queueUrl(QUEUE_URL)
                        .messageBody(OBJECT_MAPPER.writeValueAsString(user))
                        .build());
            }

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
