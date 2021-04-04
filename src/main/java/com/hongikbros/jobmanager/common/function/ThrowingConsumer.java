package com.hongikbros.jobmanager.common.function;

@FunctionalInterface
public interface ThrowingConsumer<T, U, K, E extends Exception> {
    void accept(T t, U u, K k) throws E;
}