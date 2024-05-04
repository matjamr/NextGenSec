package com.sec.gen.next.chatservice.service;

import com.sec.gen.next.chatservice.model.KafkaChatServiceModel;
import com.sec.gen.next.chatservice.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.proxy.Dispatcher;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public class MessageDispatcher implements Consumer<KafkaChatServiceModel> {

    private final List<MessageExecutor> messageExecutors;

    @Override
    public void accept(KafkaChatServiceModel kafkaChatServiceModel) {
        messageExecutors.stream()
                .filter(executor -> executor.shouldExecute(kafkaChatServiceModel))
                .forEach(executor -> executor.execute(kafkaChatServiceModel));
    }
}
