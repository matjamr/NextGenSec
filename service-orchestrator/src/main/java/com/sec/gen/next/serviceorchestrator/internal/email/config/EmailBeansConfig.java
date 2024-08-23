package com.sec.gen.next.serviceorchestrator.internal.email.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.gen.api.Email;
import com.next.gen.sec.model.MailModel;
import com.next.gen.sec.model.OutboundEmailModel;
import com.sec.gen.next.serviceorchestrator.external.kafka.KafkaProducer;
import com.sec.gen.next.serviceorchestrator.internal.email.mapper.EmailMapper;
import com.sec.gen.next.serviceorchestrator.internal.email.service.SaveEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Configuration
public class EmailBeansConfig {

    @Bean
    KafkaProducer<OutboundEmailModel> outboundEmailModelKafkaProducer(
            final KafkaTemplate<String, String> kafkaTemplate,
            final ObjectMapper objectMapper
            ) {
        return new KafkaProducer<>(kafkaTemplate, "outbound", objectMapper);
    }

    @Bean
    public Consumer<MailModel> saveEmailService(
            final EmailMapper emailMapper,
            final List<BiConsumer<Email, MailModel>> saveMailFlowConsumers,
            final Consumer<MailModel> senderProvider
    ) {
        return new SaveEmailService(emailMapper, saveMailFlowConsumers, senderProvider);
    }

    @Bean
    List<BiConsumer<Email, MailModel>> saveMailFlowConsumers(
            BiConsumer<Email, MailModel> emailNotifier,
            BiConsumer<Email, MailModel> saveEmailConsumer,
            BiConsumer<Email, MailModel> senderProvider,
            BiConsumer<Email, MailModel> notificationConsumer
    ) {
        return List.of(
                senderProvider,
                saveEmailConsumer,
                emailNotifier,
                notificationConsumer
        );
    }
}
