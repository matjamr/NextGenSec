package com.sec.gen.next.serviceorchestrator.common.templates;

import org.apache.commons.lang.NotImplementedException;

public interface DeleteService<RQ, RS> {
    default RS delete(RQ rq) {
        throw new NotImplementedException();
    }
}
