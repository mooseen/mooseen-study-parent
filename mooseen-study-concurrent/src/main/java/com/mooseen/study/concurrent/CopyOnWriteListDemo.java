package com.mooseen.study.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mthink on 2017/2/13.
 */
public class CopyOnWriteListDemo {

    private static final int THREAD_COUNT=10;
    public static void main(String[] args) {
        final CopyOnWriteArrayList<String> list=new CopyOnWriteArrayList<String>();
        for (int i=0;i<THREAD_COUNT;i++){
            final String index = String.valueOf(i);
            new Thread(new Runnable() {
                public void run() {
                    list.add(index);
                   // list.get()
                }
            }).start();
        }
    }
}
