package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        testOneModel();
//        testModelsInParallel();
    }

    private static void testOneModel() {
        ImitationModel model = new ImitationModel();
        model.startImitation();
    }

    private static void testModelsInParallel() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        int modelsCount = 5;
        double[] averageQueueSizes = new double[modelsCount];
        double[] rejectionProbabilities = new double[modelsCount];

        for (int i = 0; i < modelsCount; i++) {
            int threadIndex = i;
            Thread thread = new Thread(() -> {
                ImitationModel model = new ImitationModel();
                model.startImitation();

                averageQueueSizes[threadIndex] = model.getAverageQueueSize();
                rejectionProbabilities[threadIndex] = model.getRejectionProbability();

                synchronized (System.out) {
                    System.out.println("\nThread with index " +
                            threadIndex + " has finished!!!");
                    System.out.println("Rejection Probability: "
                            + model.getRejectionProbability());
                    System.out.println("Average Queue Size: "
                            + model.getAverageQueueSize());
                }
            });

            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        double averageQueueSize = Arrays.stream(averageQueueSizes)
                .average()
                .orElseThrow();

        double averageRejectionProbability = Arrays.stream(rejectionProbabilities)
                .average()
                .orElseThrow();

        System.out.println("\n ----------------------------------------------------- \n");
        System.out.println("Final Results");
        System.out.println("Average Queue Size: " + averageQueueSize);
        System.out.println("Average Rejection Probability: " + averageRejectionProbability);
    }
}