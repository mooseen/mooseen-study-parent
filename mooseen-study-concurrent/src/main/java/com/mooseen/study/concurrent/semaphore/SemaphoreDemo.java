package com.mooseen.study.concurrent.semaphore;

import java.util.HashSet;
import java.util.concurrent.Semaphore;

/**
 * Created by mthink on 2017/2/17.
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        final BoundedHashSet<String> strings=new BoundedHashSet<String>(3);
        new Thread(new Runnable() {
            public void run() {
                int index = 0;
                while (true){
                    try {
                        strings.add(String.valueOf(index++));
                        //Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                int index = 0;
                while (true){
                    try {
                        strings.remove(String.valueOf(index++));
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

class BoundedHashSet<T>{
    private final HashSet<T> hashSet;
    private final Semaphore semaphore;

    public BoundedHashSet(int capacity) {
        this.hashSet = new HashSet<T>();
        this.semaphore = new Semaphore(capacity);
    }

    public boolean add(T t) throws InterruptedException {
        //插入前获取许可
        this.semaphore.acquire();
        boolean success = false;
        try {
            success=this.hashSet.add(t);
            System.out.println("Add Element:"+t);
            return success;
        }finally { // 如果添加不成功释放许可
            if(!success){
                this.semaphore.release();
            }
        }
    }

    public boolean remove(T t){
        boolean success = this.hashSet.remove(t);
        if(success){
            System.out.println("Remove Element:"+t);
            this.semaphore.release();
        }
        return  success;
    }
}
