package com.sec.gen.next.backend.common;

public interface Validator<T> {
    void validate(T context);
}
