package com.sec.gen.next.chatservice.websocket;

import com.next.gen.sec.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class WebSocketHandlerImpl extends TextWebSocketHandler {
    public static Map<String, WebSocketHelper> connectedUsers = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        connectedUsers.put(session.getId(),
                new WebSocketHelper(session, (UserModel) session.getAttributes().get("PRINCIPAL"))
        );
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        connectedUsers.remove(session.getId());
        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    record WebSocketHelper(WebSocketSession webSocketSession, UserModel user) {}
}
