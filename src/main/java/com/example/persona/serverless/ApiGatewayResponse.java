package com.example.persona.serverless;

import java.util.HashMap;
import java.util.Map;

public class ApiGatewayResponse {
    private final int statusCode;
    private final String body;
    private final Map<String, String> headers;

    private ApiGatewayResponse(int statusCode, String body, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public static class Builder {
        private int statusCode = 200;
        private String rawBody = "";
        private Object objectBody;
        private final Map<String, String> headers = new HashMap<>();

        public Builder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder setRawBody(String rawBody) {
            this.rawBody = rawBody;
            return this;
        }

        public Builder setObjectBody(Object objectBody) {
            this.objectBody = objectBody;
            return this;
        }

        public ApiGatewayResponse build() {
            headers.put("Content-Type", "application/json");
            String finalBody = rawBody;
            if (objectBody != null && (finalBody == null || finalBody.isEmpty())) {
                try {
                    finalBody = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(objectBody);
                } catch (Exception e) {
                    finalBody = "{}";
                }
            }
            return new ApiGatewayResponse(statusCode, finalBody, headers);
        }
    }
}
