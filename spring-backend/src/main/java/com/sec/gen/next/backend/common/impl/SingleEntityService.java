package com.sec.gen.next.backend.common.impl;

import com.sec.gen.next.backend.api.exception.RecoverableServiceException;
import com.sec.gen.next.backend.common.Service;
import com.sec.gen.next.backend.common.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class SingleEntityService<T, R> implements Service<T, R> {

    private final List<Validator<R>> validators;
    private final List<Consumer<R>> addPlacesFlow;
    private final Function<R, T> resultBuilder;
    private final BiConsumer<R, RecoverableServiceException> recoverableActionConsumer;

    @Override
    public T doService(R context) {
        addPlacesFlow.forEach(func -> func.accept(context));

        return resultBuilder.apply(context);
    }

    @Override
    public void validate(R context) {
        validators.forEach(validator -> {
            try {
                validator.validate(context);
            } catch (RecoverableServiceException recoverableServiceException) {
                log.warn("Recoverable error occured" + recoverableServiceException.getError().getMessage());
                recoverableActionConsumer.accept(context, recoverableServiceException);
            }
        });
    }

}
