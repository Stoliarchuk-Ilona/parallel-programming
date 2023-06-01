package org.example.stripped_multiplication;

import org.example.MultiplicationAlgorithm;
import org.example.common.Result;

public class StrippedMultiplication implements MultiplicationAlgorithm {
    private int[][] first;
    private int[][] second;
    private int threadsCount;
    private Result result;

    public StrippedMultiplication(int[][] first, int[][] second, int threadsCount) {
        this.first = first;
        this.second = second;
        this.result = new Result(first.length, second[0].length);

        if (first.length > second[0].length) {
            if (threadsCount > second[0].length) {
                this.threadsCount = second[0].length;
            } else {
                this.threadsCount = threadsCount;
            }
        } else {
            if (threadsCount > first.length) {
                this.threadsCount = first.length;
            } else {
                this.threadsCount = threadsCount;
            }
        }
    }

    @Override
    public void multiply() {
        int rowsPerTime = this.first.length / threadsCount;
        int rowsLeft = this.first.length % threadsCount;

        int rowFirstStart = 0;
        StrippedMultiplicationThread[] threads = new StrippedMultiplicationThread[threadsCount];
        for (int i = 0; i < threads.length; i++) {
            int rowsPerThread = rowsPerTime;
            rowsPerThread += rowsLeft > 0 ? 1 : 0;

            threads[i] = new StrippedMultiplicationThread(first, second, rowFirstStart,
                    rowsPerThread, i, result);

            rowsLeft--;
            rowFirstStart += rowsPerThread;
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int[][] getResult() {
        return this.result.getResult();
    }
}
