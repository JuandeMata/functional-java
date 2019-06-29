package com.juandemata.stream;

import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class StreamsTest {

    private static final Supplier<String> SUPPLIER = () -> "supplier";

    @Test
    public void ofIterableOKWithNull() {
        Stream stream = Streams.ofIterable(null);
        assertEquals(stream.count(), 0);
    }

    @Test
    public void ofIterableOKWithValue() {
        Stream stream = Streams.ofIterable(Collections.singletonList(""));
        assertEquals(stream.count(), 1);
    }

    @Test
    public void ofNullableOKWithNull() {
        Stream stream = Streams.ofNullable(Optional.empty());
        assertEquals(stream.count(), 0);
    }

    @Test
    public void ofNullableOKWithValue() {
        Stream stream = Streams.ofNullable(Optional.of(new Object()));
        assertEquals(stream.count(), 1);
    }

    @Test
    public void ofNullablesOKWithNull() {
        Stream stream = Streams.ofNullables(null);
        assertEquals(stream.count(), 0);
    }

    @Test
    public void ofNullablesOKWithMultipleValues() {
        Stream stream = Streams.ofNullables(Optional.of(new Object()), Optional.of(new Object()));
        assertEquals(stream.count(), 2);
    }

    @Test
    public void ofNullablesOKWithMultipleEmptyValues() {
        Stream stream = Streams.ofNullables(Optional.of(new Object()), Optional.empty());
        assertEquals(stream.count(), 1);
    }

    @Test
    public void ofNullablesWithFallbackOKWithNull() {
        Stream<String> stream = Streams.ofNullablesWithFallback(SUPPLIER, null);
        assertEquals(stream.count(), 0);
    }

    @Test
    public void ofNullablesWithFallbackCountOKWithMultipleValues() {
        Stream<String> stream = Streams.ofNullablesWithFallback(SUPPLIER, Optional.of(""), Optional.of(""));
        assertEquals(stream.count(), 2);
    }

    @Test
    public void ofNullablesWithFallbackCountOKWithMultipleEmptyValues() {
        Stream<String> stream = Streams.ofNullablesWithFallback(SUPPLIER, Optional.of(""), Optional.empty());
        assertEquals(stream.count(), 2);
    }

    @Test
    public void ofNullablesWithFallbackCountValuesWithMultipleValues() {
        Stream<String> stream = Streams.ofNullablesWithFallback(SUPPLIER, Optional.of(""), Optional.of(""));
        assertTrue(stream.noneMatch(string -> string.equals(SUPPLIER.get())));
    }

    @Test
    public void ofNullablesWithFallbackCountValuesWithMultipleEmptyValues() {
        Stream<String> stream = Streams.ofNullablesWithFallback(SUPPLIER, Optional.of(""), Optional.empty());
        assertTrue(stream.anyMatch(string -> string.equals(SUPPLIER.get())));
    }

}
