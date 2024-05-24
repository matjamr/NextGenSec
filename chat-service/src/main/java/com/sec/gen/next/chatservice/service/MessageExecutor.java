package com.sec.gen.next.chatservice.service;


import com.next.gen.sec.model.KafkaChatServiceModel;

public interface MessageExecutor {
    void execute(KafkaChatServiceModel t);
    boolean shouldExecute(KafkaChatServiceModel t);
}
