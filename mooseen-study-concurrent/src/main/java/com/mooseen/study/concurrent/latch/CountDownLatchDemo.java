package com.mooseen.study.concurrent.latch;

import java.util.concurrent.CountDownLatch;

/**
 * Created by mthink on 2017/2/16.
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch startGate=new CountDownLatch(1);
        final CountDownLatch endGate=new CountDownLatch(3);
        for(int i=0;i<3;i++){
            final int index = i;
            new Thread(new Runnable() {
                public void run() {
                    Runner runner = new Runner(10,"R-"+index);
                    //runner.prepare();
                    try {
                        startGate.await();
                        runner.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        endGate.countDown();
                    }
                }
            }).start();
        }
        // 释放起始门
        startGate.countDown();
        endGate.await();
        System.out.println("All runner finished.");
    }


}

class Runner{
    private int speed;
    private String name;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Runner(int speed, String name){
        this.speed=speed;
        this.name=name;
        this.prepare();
    }

    public void run(){
        System.out.println("Runner: " + name + " start running");
        System.out.println("Runner: " + name + " finished");
    }
    public void prepare(){
        System.out.println("Runner "+name +"prepared.");
        //System.out.println("Runner: "+name+" prepared,Time:"+System.nanoTime());
    }
}
