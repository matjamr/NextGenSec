package com.sec.gen.next.outbound.service;

import com.next.gen.sec.model.OutboundKafkaWebhookModel;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class ReactiveConsumerService<T> {

    private final ReactiveKafkaConsumerTemplate<String, T> reactiveKafkaConsumer;
    private final Function<T, Mono<ResponseEntity<Void>>> onReceiveActionProcessor;

    private Flux<Void> consume() {
        return reactiveKafkaConsumer
                .receiveAutoAck()
                .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .flatMap(ret -> onReceiveActionProcessor.apply(ret).then())
                .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));
    }

    @PostConstruct
    public void init() {
        consume().subscribe();
    }

}
