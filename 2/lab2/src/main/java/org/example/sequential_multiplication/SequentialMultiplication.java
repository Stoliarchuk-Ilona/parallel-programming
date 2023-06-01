package org.example.sequential_multiplication;

import org.example.MultiplicationAlgorithm;
import org.example.common.Result;

public class SequentialMultiplication implements MultiplicationAlgorithm {
    private int[][] first;
    private int[][] second;
    private Result result;

    public SequentialMultiplication(int[][] first, int[][] second) {
        this.first = first;
        this.second = second;
        this.result = new Result(first.length, second[0].length);
    }

    public void multiply() {
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < second[i].length; j++) {
                int sum = 0;
                for (int k = 0; k < second.length; k++) {
                    sum += first[i][k] * second[k][j];
                }
                result.setElement(i, j, sum);
            }
        }
    }

    @Override
    public int[][] getResult() {
        return result.getResult();
    }
}
