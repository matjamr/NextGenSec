package com.sec.gen.next.backend.internal.common;

public interface Validator<T> {
    void validate(T context);
}
