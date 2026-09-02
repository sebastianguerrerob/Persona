package com.example.persona.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.Map;

public class EnviarCorreosHandler implements RequestHandler<SQSEvent, String> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String TOPIC_ARN = System.getenv().getOrDefault("TOPIC_ARN", "");
    private static final SnsClient SNS_CLIENT = SnsClient.builder()
            .region(Region.US_EAST_1)
            .build();

    @Override
    public String handleRequest(SQSEvent event, Context context) {
        if (event == null || event.getRecords() == null || event.getRecords().isEmpty()) {
            return "No messages";
        }

        for (SQSMessage message : event.getRecords()) {
            try {
                Map<String, Object> user = OBJECT_MAPPER.readValue(message.getBody(), new TypeReference<Map<String, Object>>() {});
                String subject = "Usuario creado";
                String body = "Usuario creado correctamente: " + user.get("nombre") + " - " + user.get("email");

                SNS_CLIENT.publish(PublishRequest.builder()
                        .topicArn(TOPIC_ARN)
                        .subject(subject)
                        .message(body)
                        .build());
            } catch (Exception e) {
                context.getLogger().log("Error processing message: " + e.getMessage());
            }
        }

        return "Processed";
    }
}
