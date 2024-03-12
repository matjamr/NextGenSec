package com.sec.gen.next.chatservice.controller;

import com.sec.gen.next.chatservice.model.KafkaChatServiceModel;
import com.sec.gen.next.chatservice.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.util.Pair;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketEventListener {

    public static Map<Pair<Object, String>, User> connectedUsers = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());

        String userEmail = headerAccessor.getNativeHeader("user").get(0);

        String sessionId = headerAccessor.getSessionId();
        connectedUsers.put(Pair.of(sessionId, userEmail), new User().setEmail(userEmail).setId(event.getUser().getName()));

        log.info("Connected user: " + userEmail + " with session ID: " + sessionId);

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());


        String sessionId = headerAccessor.getSessionId();
        connectedUsers.keySet().stream()
                        .filter(pair -> pair.getFirst().equals(sessionId))
                        .findFirst()
                        .ifPresent(connectedUsers::remove);


        log.info("Disconnected user: " + " with session ID: " + sessionId);
    }

    public void sendMessageToUsers(KafkaChatServiceModel kafkaChatServiceModel) {
        connectedUsers.keySet().stream()
                .filter(pair -> kafkaChatServiceModel.getAdminsEmails().contains(pair.getSecond()))
                .forEach(pair -> sendMessageToSessionId(connectedUsers.get(pair), kafkaChatServiceModel));
    }

    private void sendMessageToSessionId(User user, Object payload) {
        simpMessagingTemplate.convertAndSendToUser(user.getId(), "/topic/admin/entrances", payload);

    }
}