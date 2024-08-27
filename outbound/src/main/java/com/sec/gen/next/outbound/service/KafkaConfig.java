//package com.sec.gen.next.outbound.service;
//
//
//
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.admin.AdminClient;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.core.KafkaAdmin;
//import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
//import reactor.kafka.receiver.KafkaReceiver;
//import reactor.kafka.receiver.ReceiverOptions;
//import reactor.kafka.receiver.ReceiverPartition;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@EnableKafka
//@Configuration
//@RequiredArgsConstructor
//public class KafkaConfig {
//    private String bootstrapAddress = "localhost:9092";
//
//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> configs = Map.of(
//                "bootstrap.servers", bootstrapAddress
//        );
//
//        return new KafkaAdmin(configs);
//    }
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                bootstrapAddress);
//        props.put(
//                ConsumerConfig.GROUP_ID_CONFIG, 1);
//        props.put(
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        props.put(
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String>
//    kafkaListenerContainerFactory() {
//
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public NewTopic chatServiceTopic() {
//        return new NewTopic("chat-service", 1, (short) 1);
//    }
//
//    @Bean
//    public NewTopic webhookTopic() {
//        return new NewTopic("webhook", 1, (short) 1);
//    }
//
//    @Bean
//    public NewTopic outboundTopic() {
//        return new NewTopic("outbound", 1, (short) 1);
//    }
//
//}
