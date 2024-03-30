package com.sec.gen.next.backend.common.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sec.gen.next.backend.api.external.KafkaNotifModel;
import com.sec.gen.next.backend.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "test", groupId = "1")
@RequiredArgsConstructor
public class KafkaListenerService {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final HistoryService historyService;

    @KafkaHandler
    public void objectHandler(String message) throws JsonProcessingException {
        KafkaNotifModel kafkaNotifModel = objectMapper.readValue(message, KafkaNotifModel.class);

        historyService.addHistoryEntrance(kafkaNotifModel);
    }
}
