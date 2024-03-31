package com.sec.gen.next.backend.common.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sec.gen.next.backend.api.external.KafkaChatServiceModel;
import com.next.gen.api.HistoryEntrance;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaChatServiceProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public void sendMessage(KafkaChatServiceModel kafkaChatServiceModel) {
        kafkaTemplate.send("chat-service", objectMapper.writeValueAsString(kafkaChatServiceModel));
    }
}
