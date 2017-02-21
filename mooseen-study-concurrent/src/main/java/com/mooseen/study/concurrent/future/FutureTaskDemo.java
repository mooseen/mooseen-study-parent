package com.mooseen.study.concurrent.future;

import java.util.Calendar;
import java.util.concurrent.*;

/**
 * Created by mthink on 2017/2/17.
 */
public class FutureTaskDemo {

    static  ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        Task task = new Task();
        MyFutureTask<Product> futureTask = new MyFutureTask<Product>(task);
        // 提交任务
        System.out.println("Time:"+ Calendar.getInstance().get(Calendar.SECOND)+" - Submit Task...");
        executor.submit(futureTask);
        System.out.println("Time:"+ Calendar.getInstance().get(Calendar.SECOND)+" - Submit Task Completed.");
        executor.shutdown();
//        try {
//            futureTask.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }
}

class Product{
    private String name;
    private int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "name:"+this.name+", price:"+this.price;
    }
}

class ProductService{
    /**
     * Long time running Task.
     * @return
     */
    public static Product getProduct(){
        return  new Product("apple",10);
    }
}

class Task implements Callable<Product>{

    public Product call() throws Exception {
        Thread.sleep(2000);
        return ProductService.getProduct();
    }
}

class MyFutureTask<Product> extends FutureTask<Product>{

    public MyFutureTask(Callable<Product> callable) {
        super(callable);
    }

    public MyFutureTask(Runnable runnable, Product result) {
        super(runnable, result);
    }

    /**
     * 计算结果完成事件
     */
    @Override
    protected void done() {
        try {
            System.out.println("Time: "+Calendar.getInstance().get(Calendar.SECOND)+" - Calculate Completed:"+this.get().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
