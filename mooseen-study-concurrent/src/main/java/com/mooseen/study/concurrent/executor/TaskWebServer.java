package com.mooseen.study.concurrent.executor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by mthink on 2017/2/20.
 */
public class TaskWebServer {
    private static  final int NTHREADS=100;
    private static  final Executor executor= Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(80);
        Socket socket=serverSocket.accept();
        Runnable task=new Runnable() {
            public void run() {
                // handler
            }
        };
        executor.execute(task);
    }
}

/**
 * 每个任务开启一个线程
 */
class ThreadPerTaskExecutor implements Executor{
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
