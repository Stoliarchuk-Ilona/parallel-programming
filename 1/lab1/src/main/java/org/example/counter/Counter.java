package org.example.counter;

import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    private final Object lock = new Object();
    private ReentrantLock reentrantLock = new ReentrantLock();
    private int counter;

    public Counter(int counter) {
        this.counter = counter;
    }

    public void syncObjectIncrement() {
        synchronized (lock) {
            increment();
        }
    }

    public void syncObjectDecrement() {
        synchronized (lock) {
            decrement();
        }
    }

    public void reentrantLockIncrement() {
        reentrantLock.lock();
        increment();
        reentrantLock.unlock();
    }

    public void reentrantLockDecrement() {
        reentrantLock.lock();
        decrement();
        reentrantLock.unlock();
    }

    public synchronized void syncMethodIncrement() {
        increment();
    }

    public synchronized void syncMethodDecrement() {
        decrement();
    }

    public void increment() {
        counter++;
    }

    public void decrement() {
        counter--;
    }

    public int getCounter() {
        return counter;
    }
}
