package com.sec.gen.next.outbound.service;

import com.sec.gen.next.outbound.model.ConsumerSample;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
public class ReactiveConsumerService<T> {

    private final ReactiveKafkaConsumerTemplate<String, T> reactiveKafkaConsumer;

    private Flux<T> consume() {
        return reactiveKafkaConsumer
                .receiveAutoAck()
                .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(message -> log.info("successfully consumed {}", message))
                .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));
    }

    @PostConstruct
    public void init() {
        consume().subscribe();
    }

}
