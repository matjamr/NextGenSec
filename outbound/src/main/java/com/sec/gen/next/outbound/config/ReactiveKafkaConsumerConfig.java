package com.sec.gen.next.outbound.config;

import com.next.gen.sec.model.OutboundKafkaWebhookModel;
import com.sec.gen.next.outbound.model.ConsumerSample;
import com.sec.gen.next.outbound.service.ReactiveConsumerService;
import io.micrometer.observation.ObservationRegistry;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.observation.KafkaReceiverObservation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ReactiveKafkaConsumerConfig {

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic webhoookTopic() {
        return new NewTopic("webhook", 1, (short) 1);
    }

    private <T> ReceiverOptions<String, T> kafkaReceiver(Class<T> clazz, String topic) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "1");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, true);
        config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, clazz.getName());

        ReceiverOptions<String, T> basicReceiverOptions = ReceiverOptions.create(config);
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    private <T> ReactiveKafkaConsumerTemplate<String, T> reactiveKafkaConsumer(Class<T> clazz, String topic) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiver(clazz, topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, OutboundKafkaWebhookModel> webhookConsumerTemplate() {
        return reactiveKafkaConsumer(OutboundKafkaWebhookModel.class, "webhook");
    }

    @Bean
    ReactiveConsumerService<OutboundKafkaWebhookModel> reactiveConsumerService(
            ReactiveKafkaConsumerTemplate<String, OutboundKafkaWebhookModel> webhookConsumerTemplate
    ) {
        return new ReactiveConsumerService<>(webhookConsumerTemplate);
    }
}
