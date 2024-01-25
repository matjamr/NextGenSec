package com.sec.gen.next.backend.internal.common;

public interface Dispatcher<T, R, S> {
    T dispatch(R payload, S enumRoute);
}
