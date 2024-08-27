package com.sec.gen.next.outbound.config;

import com.sec.gen.next.outbound.model.ConsumerSample;
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

//    @Bean
//    public <K,V> KafkaReceiver<K,V> kafkaReceiver(ObservationRegistry registry, KafkaProperties kafkaProperties) {
//        var properties = kafkaProperties.buildConsumerProperties();
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//
//        var receiverOptions = ReceiverOptions.<K, V>create(properties)
//                .withObservation(registry, new KafkaReceiverObservation.DefaultKafkaReceiverObservationConvention())
//                .subscription(Collections.singletonList("webhook"));
//
//        return KafkaReceiver.create(receiverOptions);
//    }

    @Bean
    public JsonMessageConverter jsonMessageConverter() {
        return new ByteArrayJsonMessageConverter();
    }

    @Bean
    public ReceiverOptions<String, ConsumerSample> kafkaReceiver() {

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "1");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, true);
        config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.sec.gen.next.outbound.model.ConsumerSample");

        ReceiverOptions<String, ConsumerSample> basicReceiverOptions = ReceiverOptions.create(config);
        return basicReceiverOptions.subscription(Collections.singletonList("webhook"));

    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, ConsumerSample> reactiveKafkaConsumer(ReceiverOptions<String, ConsumerSample> kafkaReceiver) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiver);
    }
}
