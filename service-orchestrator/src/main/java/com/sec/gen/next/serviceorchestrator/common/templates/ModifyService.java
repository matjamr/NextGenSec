package com.sec.gen.next.serviceorchestrator.common.templates;

import org.apache.commons.lang.NotImplementedException;

public interface ModifyService <RQ> {
    default void update(RQ rq) {
        throw new NotImplementedException();
    }
}
