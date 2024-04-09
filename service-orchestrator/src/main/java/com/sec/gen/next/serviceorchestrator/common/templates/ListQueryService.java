package com.sec.gen.next.serviceorchestrator.common.templates;

import org.apache.commons.lang.NotImplementedException;

import java.util.List;

public interface ListQueryService<RS> {
    default List<RS> findAll() {
        throw new NotImplementedException();
    }
}
