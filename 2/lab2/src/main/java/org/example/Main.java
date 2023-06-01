package org.example;

import org.example.fox_multiplication.FoxMultiplication;
import org.example.sequential_multiplication.SequentialMultiplication;
import org.example.stripped_multiplication.StrippedMultiplication;
import org.example.utils.MatrixUtil;

public class Main {
    public static void main(String[] args) {
//        testResult();
        testOne();
//        testThreadsCount();
//        testMatrixSize();
    }

    public static void testResult() {
        int[][] first = MatrixUtil.generateMatrix(4, 4, 10, 99);
        int[][] second = MatrixUtil.generateMatrix(4, 4, 10, 99);

        System.out.println("First matrix: ");
        MatrixUtil.showMatrix(first);
        System.out.println("\nSecond matrix: ");
        MatrixUtil.showMatrix(second);

        StrippedMultiplication sm = new StrippedMultiplication(first, second, first.length);
        sm.multiply();
        System.out.println("\nStripped multiplication. Result: ");
        MatrixUtil.showMatrix(sm.getResult());

        FoxMultiplication fm = new FoxMultiplication(first, second, 4);
        fm.multiply();
        System.out.println("\nFox multiplication. Result: ");
        MatrixUtil.showMatrix(fm.getResult());

        SequentialMultiplication seqMul = new SequentialMultiplication(first, second);
        seqMul.multiply();
        System.out.println("\nSequential multiplication. Result: ");
        MatrixUtil.showMatrix(seqMul.getResult());
    }

    public static void testOne() {
        int[][] first = MatrixUtil.generateMatrix(1500, 1500, 10, 99);
        int[][] second = MatrixUtil.generateMatrix(1500, 1500, 10, 99);

        StrippedMultiplication sm = new StrippedMultiplication(first, second, 12);
        long time = getTimeOfExperiment(sm);
        System.out.println(time);

//        FoxMultiplication fm = new FoxMultiplication(first, second, 8);
//        long time = getTimeOfExperiment(fm);
//        System.out.println(time);

//        SequentialMultiplication seqMul = new SequentialMultiplication(first, second);
//        long time = getTimeOfExperiment(seqMul);
//        System.out.println(time);
    }

    public static void testThreadsCount() {
        int size = 1000;
        int min = 10;
        int max = 99;

        int[] threadsCounts = {4, 6, 8, 9};
        int[][] first = MatrixUtil.generateMatrix(size, size, min, max);
        int[][] second = MatrixUtil.generateMatrix(size, size, min, max);
        int experimentsCount = 4;

        for (int threadsCount : threadsCounts) {
            StrippedMultiplication stripped = new StrippedMultiplication(first, second, threadsCount);

            long experimentsTime = 0;
            for (int i = 0; i < experimentsCount; i++) {
                experimentsTime += getTimeOfExperiment(stripped);
            }
            long average = experimentsTime / experimentsCount;

            System.out.println("Stripped (" + size + "x" + size + "): " +
                    "threads count - " + threadsCount + "; time - " + average + " ms;");
        }

//        System.out.println();
//
//        for (int threadsCount : threadsCounts) {
//            FoxMultiplication fox = new FoxMultiplication(first, second, threadsCount);
//
//            long experimentsTime = 0;
//            for (int i = 0; i < experimentsCount; i++) {
//                experimentsTime += getTimeOfExperiment(fox);
//                fox.resetResult();
//            }
//            long average = experimentsTime / experimentsCount;
//
//            System.out.println("Fox (" + size + "x" + size + "): " +
//                    "threads count - " + threadsCount + "; time - " + average + " ms;");
//        }
        System.out.println();

        SequentialMultiplication seqMul = new SequentialMultiplication(first, second);

        long experimentsTime = 0;
        for (int i = 0; i < experimentsCount; i++) {
            experimentsTime += getTimeOfExperiment(seqMul);
        }
        long average = experimentsTime / experimentsCount;

        System.out.println("Sequential (" + size + "x" + size + "): " +
                "threads count - 1; time - " + average + " ms;");
    }

    public static void testMatrixSize() {
        int min = 10;
        int max = 99;

        int[] sizes = {500, 1000, 1500, 2000};
        int experimentsCount = 4;
        int threadsCount = 16;

        for (int matrixSize : sizes) {
            int[][] first = MatrixUtil.generateMatrix(matrixSize, matrixSize, min, max);
            int[][] second = MatrixUtil.generateMatrix(matrixSize, matrixSize, min, max);

            StrippedMultiplication stripped = new StrippedMultiplication(first, second, threadsCount);

            long experimentsTime = 0;
            for (int i = 0; i < experimentsCount; i++) {
                experimentsTime += getTimeOfExperiment(stripped);
            }
            long average = experimentsTime / experimentsCount;

            System.out.println("Stripped: matrix size - " + matrixSize + "x"
                    + matrixSize + "; time - " + average + " ms;");

            FoxMultiplication fox = new FoxMultiplication(first, second, threadsCount);

            experimentsTime = 0;
            for (int i = 0; i < experimentsCount; i++) {
                experimentsTime += getTimeOfExperiment(fox);
                fox.resetResult();
            }
            average = experimentsTime / experimentsCount;

            System.out.println("Fox: matrix size - " + matrixSize + "x"
                    + matrixSize + "; time - " + average + " ms;");

            SequentialMultiplication seqMul = new SequentialMultiplication(first, second);

            experimentsTime = 0;
            for (int i = 0; i < experimentsCount; i++) {
                experimentsTime += getTimeOfExperiment(seqMul);
            }
            average = experimentsTime / experimentsCount;

            System.out.println("Sequential: matrix size - " + matrixSize + "x"
                    + matrixSize + "; time - " + average + " ms;");
            System.out.println();
        }
    }

    public static long getTimeOfExperiment(MultiplicationAlgorithm algorithm) {
        long start = System.currentTimeMillis();
        algorithm.multiply();
        long finish = System.currentTimeMillis();
        return finish - start;
    }
}