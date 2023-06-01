package org.example.fox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FoxForkMultiplication extends RecursiveAction implements MultiplicationAlgorithm {
    private final int[][] first;
    private final int[][] second;
    private final Result result;
    private int step;

    public FoxForkMultiplication(int[][] first, int[][] second, Result result) {
        this.first = first;
        this.second = second;
        this.result = result;
    }

    private FoxForkMultiplication(int[][] first, int[][] second, Result result, int step) {
        this.first = first;
        this.second = second;
        this.result = result;
        this.step = step;
    }

    public void resetResult() {
        result.reset();
    }

    @Override
    public void multiply() {
        int threadsCount = 100;
        int step = 100;

        ForkJoinPool pool = new ForkJoinPool(threadsCount);
        pool.invoke(new FoxForkMultiplication(first, second, result, step));
    }

    @Override
    public int[][] getResult() {
        return result.getResult();
    }

    @Override
    protected void compute() {
        List<FoxMultiplicationTask> tasks = new ArrayList<>();
        for (int i = 0; i < first.length; i += step) {
            for (int j = 0; j < second[0].length; j += step) {
                int rowFirstSize = step;
                int colSecondSize = step;

                if (i + step > first.length) {
                    rowFirstSize = first.length - i;
                }

                if (j + step > second[0].length) {
                    colSecondSize = second[0].length - j;
                }

                for (int k = 0; k < first.length; k += step) {
                    int colFirstSize = step;
                    int rowSecondSize = step;

                    if (k + step > first[0].length) {
                        colFirstSize = first[0].length - k;
                    }

                    if (k + step > second.length) {
                        rowSecondSize = second.length - k;
                    }

                    FoxMultiplicationTask task = new FoxMultiplicationTask(first, second, i, j,
                            rowFirstSize, colFirstSize, rowSecondSize, colSecondSize, k, result);
                    tasks.add(task);
                    task.fork();
                }
            }
        }

        for (FoxMultiplicationTask task : tasks) {
            task.join();
        }
    }
}
