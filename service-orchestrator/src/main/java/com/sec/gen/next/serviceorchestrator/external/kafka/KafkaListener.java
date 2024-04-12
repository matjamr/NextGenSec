package com.sec.gen.next.serviceorchestrator.external.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class KafkaListener<T> {
    private final ObjectMapper objectMapper;
    private final Consumer<T> callback;
    private final Class<T> tClass;

    @KafkaHandler
    public void objectHandler(String message) throws JsonProcessingException {
        callback.accept(objectMapper.readValue(message, tClass));
    }
}
