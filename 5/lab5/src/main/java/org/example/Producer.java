package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private final Buffer buffer;
    private final AtomicInteger rejected;
    private final long producingIntervalFrom;
    private final long producingIntervalTo;
    private final long consumptionDelayFrom;
    private final long consumptionDelayTo;
    private final Random random = new Random();

    public Producer(Buffer buffer, AtomicInteger rejected,
                    long producingIntervalFrom, long producingIntervalTo,
                    long consumptionDelayFrom, long consumptionDelayTo) {
        this.buffer = buffer;
        this.rejected = rejected;
        this.producingIntervalFrom = producingIntervalFrom;
        this.producingIntervalTo = producingIntervalTo;
        this.consumptionDelayFrom = consumptionDelayFrom;
        this.consumptionDelayTo = consumptionDelayTo;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (buffer) {
                    if (buffer.isFull()) {
                        rejected.incrementAndGet();
                    } else {
                        boolean isEmpty = buffer.isEmpty();

                        long delay = (long) (random.nextGaussian()
                                * (1.0 * (consumptionDelayTo - consumptionDelayFrom) / 2)
                                + 1.0 * (consumptionDelayTo + consumptionDelayFrom) / 2);
                        Task task = new Task(delay);
                        buffer.put(task);

                        if (isEmpty) {
                            buffer.notifyAll();
                        }
                    }
                }

                long prodDelay = random.nextLong(producingIntervalFrom, producingIntervalTo);
                Thread.sleep(prodDelay);
            }
        } catch (InterruptedException e) {
            System.out.println("Producer was interrupted!!!");
        }
    }
}