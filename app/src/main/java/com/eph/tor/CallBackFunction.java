package com.eph.tor;

@FunctionalInterface
public interface CallBackFunction {
    <T> void callback(T... values);
}
