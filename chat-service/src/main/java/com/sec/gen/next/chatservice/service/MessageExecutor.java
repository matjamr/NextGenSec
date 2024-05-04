package com.sec.gen.next.chatservice.service;

import com.sec.gen.next.chatservice.model.KafkaChatServiceModel;

public interface MessageExecutor {
    void execute(KafkaChatServiceModel t);
    boolean shouldExecute(KafkaChatServiceModel t);
}
