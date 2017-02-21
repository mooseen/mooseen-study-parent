package com.mooseen.study.concurrent.cache;

import java.util.concurrent.*;

/**
 * Created by mthink on 2017/2/20.
 */
public class Memorizer<A,V> {
    private final ConcurrentHashMap<A,Future<V>> cache=new ConcurrentHashMap<A, Future<V>>();
    private final  Computable<A,V> computation;

    public Memorizer(Computable<A, V> computable) {
        this.computation = computable;
    }

    public V compute(final A arg) throws InterruptedException{
        while (true) {
            Future<V> future = cache.get(arg);
            if (future == null) {
                //
                Callable<V> callable = new Callable<V>() {
                    public V call() throws Exception {
                        return computation.compute(arg);
                    }
                };

                // task
                FutureTask<V> task = new FutureTask<V>(callable);

                future = cache.putIfAbsent(arg, task);

                if (future == null) {
                    future = task;
                    task.run();
                }
            }
            try {
                return future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
