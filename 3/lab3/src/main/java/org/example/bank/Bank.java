package org.example.bank;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts;
    private ReentrantLock lock = new ReentrantLock();
    private boolean isTransfer = false;

    public Bank(int n, int initialBalance) {
        accounts = new int[n];
        Arrays.fill(accounts, initialBalance);
        ntransacts = 0;
    }

//    public void transfer(int from, int to, int amount) {
//        lock.lock();
//
//        if (accounts[from] >= amount) {
//            accounts[from] -= amount;
//            accounts[to] += amount;
//            ntransacts++;
//            if (ntransacts % NTEST == 0)
//                test();
//        }
//
//        lock.unlock();
//    }

//    public synchronized void transfer(int from, int to, int amount) {
//        if (accounts[from] >= amount) {
//            accounts[from] -= amount;
//            accounts[to] += amount;
//            ntransacts++;
//            if (ntransacts % NTEST == 0)
//                test();
//        }
//    }

//    public void transfer(int from, int to, int amount) {
//        accounts[from] -= amount;
//        accounts[to] += amount;
//        ntransacts++;
//        if (ntransacts % NTEST == 0)
//            test();
//    }

    public synchronized void transfer(int from, int to, int amount) {
        while (isTransfer) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isTransfer = true;

        if (accounts[from] >= amount) {
            accounts[from] -= amount;
            accounts[to] += amount;

            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
        }

        isTransfer = false;

        notifyAll();
    }

    public void test() {
        int sum = 0;
        for (int account : accounts) {
            sum += account;
        }
        System.out.println("Transactions:" + ntransacts
                + " Sum: " + sum);
    }

    public int size() {
        return accounts.length;
    }
}