package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    private final Buffer buffer;
    private final AtomicInteger processed;

    public Consumer(Buffer buffer, AtomicInteger processed) {
        this.buffer = buffer;
        this.processed = processed;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Task task;
                synchronized (buffer) {
                    while (buffer.isEmpty()) {
                        buffer.wait();
                    }
                    task = buffer.take();
                }

                task.execute();
                processed.incrementAndGet();
            }
        } catch (InterruptedException e) {
            System.out.println("Consumer on thread " +
                    Thread.currentThread().getName() + " was interrupted!!!");
        }
    }
}
