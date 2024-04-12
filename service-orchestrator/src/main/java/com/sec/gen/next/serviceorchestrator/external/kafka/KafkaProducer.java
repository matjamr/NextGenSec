package com.sec.gen.next.serviceorchestrator.external.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class KafkaProducer<T> {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topicName;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void sendMessage(T object) {
        kafkaTemplate.send(topicName, objectMapper.writeValueAsString(object));
    }
}
