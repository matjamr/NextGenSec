package com.sec.gen.next.chatservice.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChatMessage {
    private String from;
    private String toPlace;
    private String responder;
    private String text;
}
