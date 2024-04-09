package com.sec.gen.next.serviceorchestrator.common.templates;

import org.apache.commons.lang.NotImplementedException;

import java.util.List;
import java.util.function.Predicate;

public interface QueryService<RS, ID> extends SimpleQueryService<ID, RS>,
        ListQueryService<RS> {

    default RS findByCriteria(Predicate<RS> predicate) {
        throw new NotImplementedException();
    }

}
