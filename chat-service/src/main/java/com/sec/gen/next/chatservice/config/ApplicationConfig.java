package com.sec.gen.next.chatservice.config;

import com.next.gen.sec.model.KafkaChatServiceModel;
import com.sec.gen.next.chatservice.model.User;
import com.sec.gen.next.chatservice.service.MessageDispatcher;
import com.sec.gen.next.chatservice.service.MessageExecutor;
import com.sec.gen.next.chatservice.service.executors.BroadcastMessageExecutor;
import com.sec.gen.next.chatservice.service.executors.NotificationMessageSender;
import com.sec.gen.next.chatservice.service.executors.SendToAdminEntrance;
import com.sec.gen.next.chatservice.service.executors.SingleMessageSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Configuration
public class ApplicationConfig {

    @Bean
    public Consumer<KafkaChatServiceModel> messageDispatcher(
            final MessageExecutor broadcastMessageExecutor,
            final MessageExecutor sendToAdminEntranceMessageExecutor,
            final MessageExecutor notificationMessageSender
            ) {
        return new MessageDispatcher(List.of(
                broadcastMessageExecutor,
                sendToAdminEntranceMessageExecutor,
                notificationMessageSender
        ));
    }

    @Bean
    public MessageExecutor broadcastMessageExecutor(
            final BiConsumer<User, KafkaChatServiceModel> simpleMessageSender
    ) {
        return new BroadcastMessageExecutor(simpleMessageSender);
    }

    @Bean
    public MessageExecutor sendToAdminEntranceMessageExecutor(
            final BiConsumer<User, KafkaChatServiceModel> simpleMessageSender
    ) {
        return new SendToAdminEntrance(simpleMessageSender);
    }

    @Bean
    public MessageExecutor notificationMessageSender(
            final BiConsumer<User, KafkaChatServiceModel> simpleMessageSender
    ) {
        return new NotificationMessageSender(simpleMessageSender);
    }

    @Bean
    public BiConsumer<User, KafkaChatServiceModel> simpleMessageSender(
//            final SimpMessagingTemplate simpMessagingTemplate
    ) {
        return new SingleMessageSender(null);
    }
}
