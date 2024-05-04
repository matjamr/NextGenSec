package com.sec.gen.next.chatservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Topic {
    ADMIN_ENTRANCES("/topic/admin/entrances"),
    BROADCAST("/topic/broadcast");

    private final String topicName;

}
