package com.maiconspa.urlShortner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {

    // Jackson serialization library
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, String> handleRequest(Map<String, Object> input, Context context) {
        String body = input.get("body").toString();
        Map<String, String> bodyMap;

        try {
            // Trying to deserialize object, transforming to a Map<String, String>
            bodyMap = objectMapper.readValue(body, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON body: " + e.getMessage(), e);
        }

        // Extracting key values from deserialized Map
        String originalUrl = bodyMap.get("originalUrl");
        String expirationTime = bodyMap.get("expirationTime");
        String shortUrlCode = UUID.randomUUID().toString().substring(0, 8);

        Map<String, String> response = new HashMap<>();
        response.put("code", shortUrlCode);

        return response;
    }
}