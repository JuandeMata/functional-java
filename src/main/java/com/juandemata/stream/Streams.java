package com.juandemata.stream;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Streams {

    private Streams() {
        throw new AssertionError("No " + Streams.class.getName() + "instances for you");
    }

    public static <T> Stream<T> ofIterable(Iterable<T> iterable) {
        return Optional.ofNullable(iterable)
                .map(Iterable::spliterator)
                .map(spliterator -> StreamSupport.stream(spliterator, false))
                .orElseGet(Stream::empty);
    }

    public static <T> Stream<T> ofNullable(Optional<T> optional) {
        return optional.map(Stream::of).orElseGet(Stream::empty);
    }

    @SafeVarargs
    public static <T> Stream<T> ofNullables(Optional<T>... optionals) {
        return Optional.ofNullable(optionals)
                .map(Arrays::stream)
                .orElseGet(Stream::empty)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    @SafeVarargs
    public static <T> Stream<T> ofNullablesWithFallback(Supplier<T> fallback, Optional<T>... optionals) {
        return Optional.ofNullable(optionals)
                .map(Arrays::stream)
                .orElseGet(Stream::empty)
                .map(optional -> optional.orElseGet(fallback));
    }

}
