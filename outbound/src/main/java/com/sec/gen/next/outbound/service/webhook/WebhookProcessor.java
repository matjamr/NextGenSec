package com.sec.gen.next.outbound.service.webhook;

import com.next.gen.sec.model.OutboundKafkaWebhookModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class WebhookProcessor implements Function<OutboundKafkaWebhookModel, Mono<ResponseEntity<Void>>> {

    private final WebClient webClient;

    @Override
    public Mono<ResponseEntity<Void>> apply(OutboundKafkaWebhookModel outboundKafkaWebhookModel) {
        return webClient.post()
                .uri(outboundKafkaWebhookModel.getDetails().getUrl())
                .header("action", outboundKafkaWebhookModel.getDetails().getAction().getValue())
                .body(Mono.just(outboundKafkaWebhookModel.getAdditionalContent()), Map.class)
                .retrieve()
                .toBodilessEntity();
    }



}
