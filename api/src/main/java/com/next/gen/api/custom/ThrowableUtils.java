package com.next.gen.api.custom;

import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
public class ThrowableUtils {

    public <X extends RuntimeException> void throwIf(X throwable, Supplier<Boolean> throwableSupplier) throws X {
        if(throwableSupplier.get()) {
            throw throwable;
        }
    }
}
