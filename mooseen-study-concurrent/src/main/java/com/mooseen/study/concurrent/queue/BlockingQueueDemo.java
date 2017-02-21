package com.mooseen.study.concurrent.queue;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by mthink on 2017/2/16.
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        final BlockingQueue<Message> queue=new LinkedBlockingQueue<Message>();
        new Thread(new Runnable() {
            public void run() {
                Producer producer =new Producer(queue,"mooseen");
                int i=0;
                while (true){
                    try {
                        producer.produce(new Message(i++));
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                Consumer consumer=new Consumer(queue,"sweet");
                while (true){
                    try {
                        consumer.consume();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

class Message{
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public  Message(Object data){
        this.data=data;
    }
}

class Producer{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private final  BlockingQueue<Message> queue;
    public  Producer(BlockingQueue<Message> queue,String name){
        this.queue = queue;
        this.name=name;
    }
    public void produce(Message message) throws InterruptedException{
        //阻塞方法,如果阻塞方法被中断
        //则会尝试提前中断阻塞状态
        this.queue.put(message);
        System.out.println("Producer:"+this.name+" produce data:"+message.getData());
    }
}


class Consumer {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private final  BlockingQueue<Message> queue;
    public  Consumer(BlockingQueue<Message> queue,String name){
        this.queue = queue;
        this.name=name;
    }
    public Message consume() throws InterruptedException{
        Message message= this.queue.take();
        System.out.println("Consumer: "+this.name+" consume data:"+message.getData());
        return  message;
    }
}
