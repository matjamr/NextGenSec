package com.sec.gen.next.chatservice.service.executors;

import com.next.gen.sec.model.KafkaChatServiceModel;
import com.sec.gen.next.chatservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class SingleMessageSender implements BiConsumer<User, KafkaChatServiceModel> {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void accept(User user, KafkaChatServiceModel payload) {
        simpMessagingTemplate.convertAndSendToUser(user.getId(), payload.getTopic().getValue(), payload);
    }
}
