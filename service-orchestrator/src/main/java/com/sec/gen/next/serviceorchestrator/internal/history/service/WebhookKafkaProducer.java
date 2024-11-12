package com.sec.gen.next.serviceorchestrator.internal.history.service;

import com.next.gen.sec.model.OutboundEmailModel;
import com.next.gen.sec.model.WebhookModel;
import com.sec.gen.next.serviceorchestrator.external.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WebhookKafkaProducer {
    private final KafkaProducer<WebhookModel> kafkaProducer;



}
