package com.sec.gen.next.serviceorchestrator.internal.email.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.gen.sec.model.OutboundEmailModel;
import com.sec.gen.next.serviceorchestrator.external.kafka.KafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class EmailBeansConfig {

    @Bean
    KafkaProducer<OutboundEmailModel> outboundEmailModelKafkaProducer(
            final KafkaTemplate<String, String> kafkaTemplate,
            final ObjectMapper objectMapper
            ) {
        return new KafkaProducer<>(kafkaTemplate, "outbound", objectMapper);
    }
}
