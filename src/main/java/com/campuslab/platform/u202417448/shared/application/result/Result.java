package com.campuslab.platform.u202417448.shared.application.result;

import java.util.function.Function;

public sealed interface Result<T, E> permits Result.Success, Result.Failure {
    static <T, E> Result<T, E> success(T value) {
        return new Success<>(value);
    }

    static <T, E> Result<T, E> failure(E error) {
        return new Failure<>(error);
    }

    <R> R fold(Function<? super T, ? extends R> onSuccess, Function<? super E, ? extends R> onFailure);

    record Success<T, E>(T value) implements Result<T, E> {
        @Override
        public <R> R fold(Function<? super T, ? extends R> onSuccess, Function<? super E, ? extends R> onFailure) {
            return onSuccess.apply(value);
        }
    }

    record Failure<T, E>(E error) implements Result<T, E> {
        @Override
        public <R> R fold(Function<? super T, ? extends R> onSuccess, Function<? super E, ? extends R> onFailure) {
            return onFailure.apply(error);
        }
    }
}
