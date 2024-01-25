package com.sec.gen.next.backend.internal.common.impl;

import com.sec.gen.next.backend.internal.common.Service;

import java.util.List;

public class MultiEntityService<T, R> implements Service<List<T>, R> {
    @Override
    public List<T> doService(R context) {
        return null;
    }

    @Override
    public void validate(R context) {
        Service.super.validate(context);
    }
}
