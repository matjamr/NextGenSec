package com.sec.next.gen.userservice.service.external.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.gen.sec.model.OutboundEmailModel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public void sendMessage(Object object) {
        kafkaTemplate.send("outbound", objectMapper.writeValueAsString(object));
    }
}
