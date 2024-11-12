package com.sec.gen.next.outbound.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.gen.sec.model.WebhookModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
@org.springframework.kafka.annotation.KafkaListener(topics = "outbound", groupId = "1")
@Slf4j
public class WebhookKafkaListener {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private WebClient webClient;

    public WebhookKafkaListener() {
        this.webClient = WebClient.create();
    }

    @SneakyThrows
    @KafkaHandler
    public void objectHandler(String message) {
        WebhookModel kafkaWebhookModel = objectMapper.readValue(message, WebhookModel.class);

        webClient.post()
                .uri("")
                .headers(httpHeaders -> addHeaders(kafkaWebhookModel.getHeaders(), httpHeaders))
                .retrieve();
    }

    private void addHeaders(Map<String, String> headers, HttpHeaders httpHeaders) {
        headers.forEach(httpHeaders::add);
    }

}
