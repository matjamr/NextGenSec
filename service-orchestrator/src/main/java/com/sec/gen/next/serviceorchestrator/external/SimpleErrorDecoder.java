package com.sec.gen.next.serviceorchestrator.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sec.gen.next.serviceorchestrator.exception.Error;
import com.sec.gen.next.serviceorchestrator.exception.ServiceException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SimpleErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        Map error;

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
