package org.example;

public class StatCollector implements Runnable {
    private final long WAIT_TIME = 1000;
    private final ImitationModel model;
    private final long taskProcessedThreshold;
    private long queueTimesChecked;
    private long queueTotalLength;
    private double rejectionProbability;
    private double averageQueueSize;

    public StatCollector(ImitationModel model, long taskProcessedThreshold) {
        this.model = model;
        this.taskProcessedThreshold = taskProcessedThreshold;
    }

    @Override
    public void run() {
        try {
            while (model.getProcessed().get() < taskProcessedThreshold) {
                long processed = model.getProcessed().get();
                long rejected = model.getRejected().get();

                queueTotalLength += model.getBuffer().getSize();
                queueTimesChecked++;

                rejectionProbability = 1.0 * rejected / (rejected + processed);
                averageQueueSize = 1.0 * queueTotalLength / queueTimesChecked;

                synchronized (System.out) {
                    System.out.println();
                    System.out.println(rejected);

                    System.out.println("Thread " + Thread.currentThread().getName());
                    System.out.println("Processed Tasks: " + processed);
                    System.out.println("Rejection Probability: " + rejectionProbability);
                    System.out.println("Average Queue Length: " + averageQueueSize);
                }

                Thread.sleep(WAIT_TIME);
            }
        } catch (InterruptedException e) {
            System.out.println("Stat Collector was interrupted!!!");
        } finally {
            long processed = model.getProcessed().get();
            long rejected = model.getRejected().get();

            rejectionProbability = 1.0 * rejected / (rejected + processed);
            averageQueueSize = 1.0 * queueTotalLength / queueTimesChecked;

            System.out.println("\nStat Collector work is done!!!");
            System.out.println("Final Rejection Probability: " + rejectionProbability);
            System.out.println("Final Average Queue Length: " + averageQueueSize);
        }
    }

    public double getRejectionProbability() {
        return rejectionProbability;
    }

    public double getAverageQueueSize() {
        return averageQueueSize;
    }
}
