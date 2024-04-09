package com.sec.gen.next.serviceorchestrator.common.templates;

import org.apache.commons.lang.NotImplementedException;

import java.util.List;

public interface CrudService<RQ, RS> {
    default RS save(RQ rq) {
        throw new NotImplementedException();
    }

    default RS update(RQ rq) {
        throw new NotImplementedException();
    }

    default RS find(RQ rq) {
        throw new NotImplementedException();
    }

    default List<RS> findAll() {
        throw new NotImplementedException();
    }

}
