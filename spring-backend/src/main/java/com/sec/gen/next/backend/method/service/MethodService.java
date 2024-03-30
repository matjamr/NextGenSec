package com.sec.gen.next.backend.method.service;

import com.sec.gen.next.backend.api.external.MethodModel;

import java.util.List;

public interface MethodService {
    void add(MethodModel methodModel);

    void delete(MethodModel methodModel);

    List<MethodModel> findAll();

    void update(MethodModel methodModel);
}
