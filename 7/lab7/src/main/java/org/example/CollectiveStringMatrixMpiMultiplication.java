package org.example;

import mpi.MPI;

public class CollectiveStringMatrixMpiMultiplication {
    public static void main(String[] args) {
        MPI.Init(args);

        final int NRA = 1500, NCA = 1500, NCB = 1500;

        final int rank = MPI.COMM_WORLD.Rank();
        final int numberProcessors = MPI.COMM_WORLD.Size();
        final int MASTER_RANK = 0;

        if (NRA % numberProcessors != 0) {
            System.out.println("Number of rows and number of processors " +
                    "must be equally distributed!!!");
            MPI.COMM_WORLD.Abort(-1);
            return;
        }

        final int rowsPerProcess = NRA / numberProcessors;

        final int[][] firstPart = new int[rowsPerProcess][NCA];
        final int[][] resPart = new int[rowsPerProcess][NCB];

        int[][] first = new int[NRA][NCA];
        int[][] second = new int[NCA][NCB];
        int[][] res = new int[NRA][NCB];

        long start;

        if (rank == MASTER_RANK) {
            first = MatrixUtil.generateMatrix(NRA, NCA, 1, 10);
            second = MatrixUtil.generateMatrix(NCA, NCB, 1, 10);

//            MatrixUtil.showMatrix(first);
//            System.out.println();
//            MatrixUtil.showMatrix(second);

            start = System.currentTimeMillis();
        } else {
            start = 0;
        }

        MPI.COMM_WORLD.Bcast(second, 0, second.length, MPI.OBJECT, MASTER_RANK);
        MPI.COMM_WORLD.Scatter(first, 0, rowsPerProcess, MPI.OBJECT,
                firstPart, 0, rowsPerProcess, MPI.OBJECT, MASTER_RANK);

        for (int i = 0; i < rowsPerProcess; i++) {
            for (int j = 0; j < NCB; j++) {
                int sum = 0;
                for (int k = 0; k < NCA; k++) {
                    sum += firstPart[i][k] * second[k][j];
                }
                resPart[i][j] = sum;
            }
        }

        MPI.COMM_WORLD.Gather(resPart, 0, rowsPerProcess, MPI.OBJECT,
                res, 0, rowsPerProcess, MPI.OBJECT, MASTER_RANK);

        if (rank == MASTER_RANK) {
            long finish = System.currentTimeMillis();
            long elapsed = finish - start;

//            MatrixUtil.showMatrix(res);
//            System.out.println();

            System.out.println("Time elapsed: " + (1.0 * elapsed / 1000) + " seconds!");
        }

        MPI.Finalize();
    }
}
