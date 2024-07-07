package com.sec.gen.next.serviceorchestrator.common.templates;

import org.apache.commons.lang.NotImplementedException;

import java.util.List;

public interface ConditionalListQueryService<RS, T> {
    default List<RS> findAll(T params) {
        throw new NotImplementedException();
    }
}
