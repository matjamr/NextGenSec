package com.sec.gen.next.serviceorchestrator.common.templates;

import com.next.gen.sec.model.NewsModel;
import org.apache.commons.lang.NotImplementedException;

import java.lang.reflect.Field;

public interface UpdateService <RQ, RS> {
    default RS update(RQ rq) {
        throw new NotImplementedException();
    }
}
