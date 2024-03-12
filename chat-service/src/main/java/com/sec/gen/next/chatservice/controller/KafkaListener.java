package com.sec.gen.next.chatservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sec.gen.next.chatservice.controller.ChatController;
import com.sec.gen.next.chatservice.model.KafkaChatServiceModel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@org.springframework.kafka.annotation.KafkaListener(topics = "chat-service", groupId = "1")
@RequiredArgsConstructor
@Slf4j
public class KafkaListener {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final WebSocketEventListener webSocketEventListener;

    @SneakyThrows
    @KafkaHandler
    public void objectHandler(String message) throws JsonProcessingException {
        KafkaChatServiceModel kafkaChatServiceModel = objectMapper.readValue(message, KafkaChatServiceModel.class);
        log.info("Received message: {}", kafkaChatServiceModel);

        webSocketEventListener.sendMessageToUsers(kafkaChatServiceModel);
    }

}
