package org.example;

import mpi.MPI;
import mpi.Request;

import java.util.ArrayList;
import java.util.List;

public class AsyncStringMatrixMpiMultiplication {
    public static void main(String[] args) {
        MPI.Init(args);

        final int NRA = 1500, NCA = 1500, NCB = 1500;

        final int MASTER_RANK = 0;
        final int FIRST_MATRIX_TAG = 0, FIRST_ROWS_NUM_TAG = 1,
                SECOND_MATRIX_TAG = 2, RES_TAG = 3;

        final int numberProcessors = MPI.COMM_WORLD.Size();
        final int rank = MPI.COMM_WORLD.Rank();

        final int numberWorkers = numberProcessors - 1;

        if (rank == MASTER_RANK) {
            final int[][] first = MatrixUtil.generateMatrix(NRA, NCA, 1, 10);
            final int[][] second = MatrixUtil.generateMatrix(NCA, NCB, 1, 10);
            final int[][] res = new int[NRA][NCB];
            final int[] rows = new int[numberWorkers];
            final int[] offsets = new int[numberWorkers];

//            MatrixUtil.showMatrix(first);
//            System.out.println();
//            MatrixUtil.showMatrix(second);
//            System.out.println();

            long start = System.currentTimeMillis();

            int averageRow = NRA / numberWorkers;
            int extra = NRA % numberWorkers;
            int offset = 0;

            for (int i = 1; i < numberWorkers + 1; i++) {
                int rowsPerProcess = averageRow + (extra > 0 ? 1 : 0);

                rows[i - 1] = rowsPerProcess;
                offsets[i - 1] = offset;

                if (rowsPerProcess == 0)
                    break;

                MPI.COMM_WORLD.Isend(new int[]{rowsPerProcess}, 0, 1, MPI.INT, i, FIRST_ROWS_NUM_TAG);
                MPI.COMM_WORLD.Isend(first, offset, rowsPerProcess, MPI.OBJECT, i, FIRST_MATRIX_TAG);
                MPI.COMM_WORLD.Isend(second, 0, NCA, MPI.OBJECT, i, SECOND_MATRIX_TAG);

                extra--;
                offset += rowsPerProcess;
            }

            Request[] resRequests = new Request[numberWorkers];

            for (int i = 1; i < numberWorkers + 1; i++) {
                Request resRequest = MPI.COMM_WORLD.Irecv(res, offsets[i - 1], rows[i - 1],
                        MPI.OBJECT, i, RES_TAG);
                resRequests[i - 1] = resRequest;
            }

            Request.Waitall(resRequests);

            long finish = System.currentTimeMillis();
            long elapsed = finish - start;

//            MatrixUtil.showMatrix(res);
//            System.out.println();

            System.out.println("Time elapsed: " + (1.0 * elapsed / 1000) + " seconds!");
        } else {
            int[] rowsPerProcess = new int[1];

            Request rowsRequest = MPI.COMM_WORLD.Irecv(rowsPerProcess, 0, 1,
                    MPI.INT, MASTER_RANK, FIRST_ROWS_NUM_TAG);

            rowsRequest.Wait();

            int[][] firstPart = new int[rowsPerProcess[0]][NCA];
            int[][] second = new int[NCA][NCB];
            int[][] res = new int[rowsPerProcess[0]][NCB];

            Request firstRequest = MPI.COMM_WORLD.Irecv(firstPart, 0, rowsPerProcess[0],
                    MPI.OBJECT, MASTER_RANK, FIRST_MATRIX_TAG);
            Request secondRequest = MPI.COMM_WORLD.Irecv(second, 0, NCA,
                    MPI.OBJECT, MASTER_RANK, SECOND_MATRIX_TAG);

            firstRequest.Wait();
            secondRequest.Wait();

            for (int i = 0; i < firstPart.length; i++) {
                for (int j = 0; j < second[0].length; j++) {
                    int sum = 0;
                    for (int k = 0; k < second.length; k++) {
                        sum += firstPart[i][k] * second[k][j];
                    }
                    res[i][j] = sum;
                }
            }

            MPI.COMM_WORLD.Isend(res, 0, res.length, MPI.OBJECT, MASTER_RANK, RES_TAG);
        }

        MPI.Finalize();
    }
}
