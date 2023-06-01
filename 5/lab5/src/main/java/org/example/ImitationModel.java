package org.example;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ImitationModel {
    private final Buffer buffer;
    private final List<Consumer> consumers;
    private final Producer producer;
    private final AtomicInteger processed;
    private final AtomicInteger rejected;
    private final StatCollector statCollector;

    public ImitationModel() {
        int consumersCount = 10;
        long productionIntervalFrom = 50;
        long productionIntervalTo = 200;
        long consumptionIntervalFrom = 1000;
        long consumptionIntervalTo = 2000;
        long queueCapacity = 2;
        long taskThreshold = 1000;

        this.buffer = new Buffer(queueCapacity);
        this.rejected = new AtomicInteger(0);
        this.processed = new AtomicInteger(0);
        this.producer = new Producer(buffer, rejected,
                productionIntervalFrom, productionIntervalTo,
                consumptionIntervalFrom, consumptionIntervalTo);
        this.consumers = IntStream.range(0, consumersCount)
                .mapToObj(x -> new Consumer(buffer, processed))
                .toList();
        this.statCollector = new StatCollector(this, taskThreshold);
    }

    public ImitationModel(int consumersCount, long productionIntervalFrom,
                          long productionIntervalTo, long consumptionIntervalFrom,
                          long consumptionIntervalTo, long queueCapacity,
                          long taskThreshold) {
        this.buffer = new Buffer(queueCapacity);
        this.rejected = new AtomicInteger(0);
        this.processed = new AtomicInteger(0);
        this.producer = new Producer(buffer, rejected,
                productionIntervalFrom, productionIntervalTo,
                consumptionIntervalFrom, consumptionIntervalTo);
        this.consumers = IntStream.range(0, consumersCount)
                .mapToObj(x -> new Consumer(buffer, processed))
                .toList();
        this.statCollector = new StatCollector(this, taskThreshold);
    }

    public void startImitation() {
        try {
            int workersCount = this.consumers.size() + 2;

            ExecutorService executorService = Executors.newFixedThreadPool(workersCount);

            Future<?> stats = executorService.submit(statCollector);
            for (Runnable consumer : consumers) {
                executorService.submit(consumer);
            }
            executorService.submit(producer);
            executorService.shutdown();

            stats.get();
        } catch (InterruptedException e) {
            System.out.println("Model Thread was interrupted!!!");
        } catch (ExecutionException e) {
            System.out.println("Model Thread has thrown execution exception!!!");
        }

    }

    public AtomicInteger getProcessed() {
        return processed;
    }

    public AtomicInteger getRejected() {
        return rejected;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public double getAverageQueueSize() {
        return statCollector.getAverageQueueSize();
    }

    public double getRejectionProbability() {
        return statCollector.getRejectionProbability();
    }
}
