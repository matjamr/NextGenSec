package com.sec.gen.next.backend.common.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sec.gen.next.backend.api.external.KafkaChatServiceModel;
import com.sec.gen.next.backend.api.external.OutboundEmailModel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaOutboundEmailProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public void sendMessage(OutboundEmailModel outboundEmailModel) {
        kafkaTemplate.send("outbound", objectMapper.writeValueAsString(outboundEmailModel));
    }
}
