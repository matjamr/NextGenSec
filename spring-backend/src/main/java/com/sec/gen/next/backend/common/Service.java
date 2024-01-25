package com.sec.gen.next.backend.common;

public interface Service<T, R> {
    T doService(R context);
    default void validate(R context) {};
}
