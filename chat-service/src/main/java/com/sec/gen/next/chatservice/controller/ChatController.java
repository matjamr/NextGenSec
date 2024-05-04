package com.sec.gen.next.chatservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sec.gen.next.chatservice.model.ChatMessage;
import com.sec.gen.next.chatservice.model.KafkaChatServiceModel;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.function.Consumer;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final Consumer<KafkaChatServiceModel> messageDispatcher;

    @PostMapping("/send")
    public void broadcast(@RequestBody KafkaChatServiceModel kafkaChatServiceModel) {
        messageDispatcher.accept(kafkaChatServiceModel);
    }

}
