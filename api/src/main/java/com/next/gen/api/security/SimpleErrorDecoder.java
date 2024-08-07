package com.next.gen.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class SimpleErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        Map error = null;
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            error = mapper.readValue(bodyIs, Map.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }

        return switch (response.status()) {
            case 401 -> new RuntimeException(String.valueOf(error.get("message")));
            default -> errorDecoder.decode(methodKey, response);
        };
    }
}
