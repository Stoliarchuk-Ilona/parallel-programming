package org.example.fox;

public class Main {
    public static void main(String[] args) {
        int[][] first = MatrixUtil.generateMatrix(1500, 1500, 10, 99);
        int[][] second = MatrixUtil.generateMatrix(1500, 1500, 10, 99);
        Result foxForkResult = new Result(1500, 1500);

        FoxForkMultiplication foxForkMultiplication =
                new FoxForkMultiplication(first, second, foxForkResult);

        long total = 0;
        for (int i = 0; i < 4; i++) {
            long start = System.currentTimeMillis();
            foxForkMultiplication.multiply();
            total += System.currentTimeMillis() - start;

            foxForkMultiplication.resetResult();
        }
        long foxAverageTime = total / 4;
        System.out.println("Fork average: " + foxAverageTime + " ms");

        FoxThreadMultiplication foxThreadMultiplication =
                new FoxThreadMultiplication(first, second, 100);

        total = 0;
        for (int i = 0; i < 4; i++) {
            long start = System.currentTimeMillis();
            foxThreadMultiplication.multiply();
            total += System.currentTimeMillis() - start;

            foxThreadMultiplication.resetResult();
        }
        long threadAverageTime = total / 4;
        System.out.println("Thread average: " + threadAverageTime + " ms");

        System.out.println("Speedup is " + (1.0 * threadAverageTime / foxAverageTime));
    }
}
