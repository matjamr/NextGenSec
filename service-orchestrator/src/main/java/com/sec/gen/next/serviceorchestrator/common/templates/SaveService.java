package com.sec.gen.next.serviceorchestrator.common.templates;

import org.apache.commons.lang.NotImplementedException;

import java.util.List;

public interface SaveService<RQ, RS> {
    default RQ save(RS rs) {
        throw new NotImplementedException();
    }
    default List<RQ> saveAll(List<RS> rs) {
        throw new NotImplementedException();
    }
}
