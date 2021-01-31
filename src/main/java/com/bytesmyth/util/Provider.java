package com.bytesmyth.util;

public interface Provider<T> {
    static <T> Provider<T> constant(T t) {
        return () -> t;
    }

    T get();
}
