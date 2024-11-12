package com.sec.gen.next.serviceorchestrator.common.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@UtilityClass
public class ConsumerBasedFlowExecutor {

    public <T> void execute(List<Consumer<T>> consumers, T t) {
        consumers.forEach(consumer -> consumer.accept(t));
    }

    public <T, R> void execute(List<BiConsumer<T, R>> consumers, T t, R r) {
        consumers.forEach(consumer -> consumer.accept(t, r));
    }
}
