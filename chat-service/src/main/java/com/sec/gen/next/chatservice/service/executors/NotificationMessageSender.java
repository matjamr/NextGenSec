package com.sec.gen.next.chatservice.service.executors;

import com.next.gen.sec.model.KafkaChatServiceModel;
import com.next.gen.sec.model.Topic;
import com.sec.gen.next.chatservice.model.User;
import com.sec.gen.next.chatservice.service.MessageExecutor;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;


@RequiredArgsConstructor
public class NotificationMessageSender implements MessageExecutor {

    private final BiConsumer<User, KafkaChatServiceModel> simpleMessageSender;

    @Override
    public boolean shouldExecute(KafkaChatServiceModel t) {
        return Topic.NOTIFICATION.equals(t.getTopic());
    }

    @Override
    public void execute(KafkaChatServiceModel kafkaChatServiceModel) {
//        connectedUsers.keySet().stream()
//                .filter(pair -> kafkaChatServiceModel.getCommonRecipients().contains(pair.getSecond()))
//                .forEach(pair -> simpleMessageSender.accept(connectedUsers.get(pair), kafkaChatServiceModel));
    }
}
