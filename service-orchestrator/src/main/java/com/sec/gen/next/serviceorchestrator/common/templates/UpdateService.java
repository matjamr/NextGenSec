package com.sec.gen.next.serviceorchestrator.common.templates;

import org.apache.commons.lang.NotImplementedException;

public interface UpdateService <RQ, RS> {
    default RS update(RQ rq) {
        throw new NotImplementedException();
    }
}
