package com.sec.gen.next.serviceorchestrator.external.kafka;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.gen.sec.model.KafkaAsyncHistoryNotif;
import com.next.gen.sec.model.KafkaChatServiceModel;
import com.next.gen.sec.model.KafkaNotifModel;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${kafka.url}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = Map.of(
                "bootstrap.servers", bootstrapAddress
        );

        return new KafkaAdmin(configs);
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG, 1);
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaProducer<KafkaAsyncHistoryNotif> kafkaAsyncHistoryNotifKafkaProducer(
            final KafkaTemplate<String, String> kafkaTemplate,
            final ObjectMapper objectMapper
            ) {
        return new KafkaProducer<>(kafkaTemplate, "async-history-notif", objectMapper);
    }

    @Bean
    public KafkaProducer<KafkaChatServiceModel> kafkaChatServiceProducer(
            final KafkaTemplate<String, String> kafkaTemplate,
            final ObjectMapper objectMapper
    ) {
        return new KafkaProducer<>(kafkaTemplate, "chat-service", objectMapper);
    }

    @Bean
    @org.springframework.kafka.annotation.KafkaListener(topics = "kafka-history-notif", groupId = "1")
    public KafkaListener<KafkaNotifModel> kafkaNotifModelKafkaListener(
            final ObjectMapper objectMapper,
            final Consumer<KafkaNotifModel> consumer
            ) {
        return new KafkaListener<>(objectMapper, consumer, KafkaNotifModel.class);
    }
}
