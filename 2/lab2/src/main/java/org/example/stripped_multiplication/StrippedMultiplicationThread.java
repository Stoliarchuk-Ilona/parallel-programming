package org.example.stripped_multiplication;

import org.example.common.Result;

public class StrippedMultiplicationThread extends Thread {
    private int rowFirstStart;
    private int rowsPerThread;
    private int columnSecond;
    private int[][] first;
    private int[][] second;
    private Result result;

    public StrippedMultiplicationThread(int[][] first, int[][] second,
                                        int rowFirstStart, int rowsPerThread,
                                        int columnSecond, Result result) {
        this.first = first;
        this.second = second;
        this.rowFirstStart = rowFirstStart;
        this.rowsPerThread = rowsPerThread;
        this.columnSecond = columnSecond;
        this.result = result;
    }

    @Override
    public void run() {
        for (int rowFirst = rowFirstStart; rowFirst < rowFirstStart + rowsPerThread; rowFirst++) {
            for (int i = 0; i < second[0].length; i++) {
                int sum = 0;
                int firstElemRow = rowFirst;
                int firstElemColumn = 0;
                int secondElemRow = 0;
                int secondElemColumn = columnSecond;

                while (firstElemColumn != first[firstElemRow].length ||
                        secondElemRow != second.length) {
                    sum += first[firstElemRow][firstElemColumn]
                            * second[secondElemRow][secondElemColumn];
                    firstElemColumn++;
                    secondElemRow++;
                }
                result.setElement(rowFirst, columnSecond, sum);

                if (columnSecond == 0) {
                    columnSecond = second[0].length - 1;
                } else {
                    columnSecond--;
                }
            }
        }
    }
}
