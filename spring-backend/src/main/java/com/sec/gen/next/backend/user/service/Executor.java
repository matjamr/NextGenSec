package com.sec.gen.next.backend.user.service;

public interface Executor<T, R> {
    boolean shouldAccept(T t, R r);
    void accept(T t, R r);
}
