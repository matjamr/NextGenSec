package com.next.gen.api.custom;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BetterOptional<T> {
    private final T base;

    public static <T> BetterOptional<T> of(T obj) {
        return new BetterOptional<>(obj);
    }

    public Optional<T> checkIfNotNull(Supplier<T> action, RuntimeException throwable) {
        if(action.get() == null) {
            throw throwable;
        }

        return Optional.of(action.get());
    }

    public BetterOptional<T> verify(Supplier<Boolean> condition, RuntimeException throwable) {
        if(!condition.get()) {
            throw throwable;
        }

        return this;
    }

    public Optional<T> checkIfNotEmpty(Supplier<Optional<T>> action, RuntimeException throwable) {
        if(action.get().isEmpty()) {
            throw throwable;
        }

        return action.get();
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(base);
    }

    public <S> BetterOptional<S> map(S object) {
        return BetterOptional.of(object);
    }

    public <S> BetterOptional<S> map(Function<T, S> function) {
        return BetterOptional.of(function.apply(this.base));
    }

    public <S> Optional<S> optionalMap(Function<T, S> function) {
        return Optional.of(function.apply(this.base));
    }

}
