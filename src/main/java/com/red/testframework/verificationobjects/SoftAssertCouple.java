package com.red.testframework.verificationobjects;

/**
 * Generic class that holds expected and actual value.
 * @param <T> - generic parameter.
 *
 */
public class SoftAssertCouple<T> {
    //actual value of the field
    private T actual;

    //private value of the field
    private T expected;

    public SoftAssertCouple(T actual, T expected) {
        this.actual = actual;
        this.expected = expected;
    }

    public T getActual() {
        return actual;
    }

    public T getExpected() {
        return expected;
    }
}
