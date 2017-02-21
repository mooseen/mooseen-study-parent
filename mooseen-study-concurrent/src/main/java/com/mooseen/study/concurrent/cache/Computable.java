package com.mooseen.study.concurrent.cache;

/**
 * Created by mthink on 2017/2/20.
 */
public interface Computable<A,V> {
    V compute(A a);
}
