package com.sec.gen.next.backend.method.service;

import com.sec.gen.next.backend.api.external.MethodModel;
import com.sec.gen.next.backend.api.internal.ClaimsUser;

import java.util.List;

public interface MethodService {
    void add(MethodModel methodModel);

    void delete(MethodModel methodModel);

    List<MethodModel> findAll(ClaimsUser principal);

    void update(MethodModel methodModel);
}
