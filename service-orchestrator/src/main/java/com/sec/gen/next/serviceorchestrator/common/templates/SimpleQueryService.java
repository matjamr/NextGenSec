package com.sec.gen.next.serviceorchestrator.common.templates;

import org.apache.commons.lang.NotImplementedException;

public interface SimpleQueryService<ID, RS> {
    default RS findBy(ID id) {
        throw new NotImplementedException();
    };
}
