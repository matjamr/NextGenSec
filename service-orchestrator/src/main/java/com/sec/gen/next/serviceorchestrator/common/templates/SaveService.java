package com.sec.gen.next.serviceorchestrator.common.templates;

import org.apache.commons.lang.NotImplementedException;

public interface SaveService<RQ, RS> {
    default RQ save(RS rs) {
        throw new NotImplementedException();
    }
}
