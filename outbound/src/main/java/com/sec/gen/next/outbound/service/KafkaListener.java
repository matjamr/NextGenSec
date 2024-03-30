package com.sec.gen.next.outbound.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sec.gen.next.outbound.model.KafkaReceiveModel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
@org.springframework.kafka.annotation.KafkaListener(topics = "outbound", groupId = "1")
@RequiredArgsConstructor
@Slf4j
public class KafkaListener {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final EmailService emailService;
    private final Map<String, String> templateDispatcher;

    @SneakyThrows
    @KafkaHandler
    public void objectHandler(String message) throws JsonProcessingException {
        KafkaReceiveModel kafkaReceiveModel = objectMapper.readValue(message, KafkaReceiveModel.class);
        log.info("Received message: {}", message);

        Context context = new Context();
        kafkaReceiveModel.getParams().forEach(context::setVariable);

        emailService.sendHtmlMail(kafkaReceiveModel.getEmail(),
                "Mail from next gen sec",
                templateDispatcher.get(kafkaReceiveModel.getStrategy()),
                context);
    }

}
