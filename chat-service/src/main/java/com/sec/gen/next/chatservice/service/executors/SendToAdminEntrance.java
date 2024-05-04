package com.sec.gen.next.chatservice.service.executors;

import com.sec.gen.next.chatservice.model.KafkaChatServiceModel;
import com.sec.gen.next.chatservice.model.Topic;
import com.sec.gen.next.chatservice.model.User;
import com.sec.gen.next.chatservice.service.MessageExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.function.BiConsumer;

import static com.sec.gen.next.chatservice.controller.WebSocketEventListener.connectedUsers;

@RequiredArgsConstructor
public class SendToAdminEntrance implements MessageExecutor {

    private final BiConsumer<User, KafkaChatServiceModel> simpleMessageSender;

    @Override
    public boolean shouldExecute(KafkaChatServiceModel t) {
        return Topic.ADMIN_ENTRANCES.equals(t.getTopic());
    }

    @Override
    public void execute(KafkaChatServiceModel kafkaChatServiceModel) {
        connectedUsers.keySet().stream()
                .filter(pair -> kafkaChatServiceModel.getAdminsEmails().contains(pair.getSecond()))
                .forEach(pair -> simpleMessageSender.accept(connectedUsers.get(pair), kafkaChatServiceModel));
    }
}
