package com.sec.gen.next.chatservice.controller;

import com.sec.gen.next.chatservice.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ChatMessage getMessages(ChatMessage message) {
        return message;
    }

    @MessageMapping("/private-message")
    @SendToUser("/topic/private-messages")
    public String getPrivateMessage(ChatMessage message, final Principal principal) {
        return "Sending message to " + principal.getName();
    }
}
