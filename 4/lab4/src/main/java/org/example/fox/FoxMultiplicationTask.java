package org.example.fox;

import java.util.concurrent.RecursiveAction;

public class FoxMultiplicationTask extends RecursiveAction {
    private int[][] first;
    private int[][] second;
    private int rowOffset;
    private int colOffset;
    private int rowFirstSize;
    private int colFirstSize;
    private int rowSecondSize;
    private int colSecondSize;
    private int multiplicationOffset;
    private Result result;

    public FoxMultiplicationTask(int[][] first, int[][] second, int rowOffset,
                                 int colOffset, int rowFirstSize, int colFirstSize,
                                 int rowSecondSize, int colSecondSize, int multiplicationOffset,
                                 Result result) {
        this.first = first;
        this.second = second;
        this.rowOffset = rowOffset;
        this.colOffset = colOffset;
        this.rowFirstSize = rowFirstSize;
        this.colFirstSize = colFirstSize;
        this.rowSecondSize = rowSecondSize;
        this.colSecondSize = colSecondSize;
        this.multiplicationOffset = multiplicationOffset;
        this.result = result;
    }

    private int[][] multiplyBlocks(int[][] blockFirst, int[][] blockSecond) {
        int[][] resBlock = new int[blockFirst.length][blockSecond[0].length];
        for (int i = 0; i < blockFirst.length; i++) {
            for (int j = 0; j < blockSecond[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < blockSecond.length; k++) {
                    sum += blockFirst[i][k] * blockSecond[k][j];
                }
                resBlock[i][j] = sum;
            }
        }
        return resBlock;
    }

    private int[][] copyBlock(int[][] src, int rowStart, int rowFinish,
                              int colStart, int colFinish) {
        int[][] copy = new int[rowFinish - rowStart][colFinish - colStart];
        for (int i = 0; i < rowFinish - rowStart; i++) {
            System.arraycopy(src[i + rowStart], colStart, copy[i], 0, colFinish - colStart);
        }
        return copy;
    }

    @Override
    protected void compute() {
        int[][] blockFirst = copyBlock(first, rowOffset, rowOffset + rowFirstSize,
                multiplicationOffset, multiplicationOffset + colFirstSize);
        int[][] blockSecond = copyBlock(second, multiplicationOffset,
                multiplicationOffset + rowSecondSize, colOffset,
                colOffset + colSecondSize);

        int[][] resBlock = multiplyBlocks(blockFirst, blockSecond);
        for (int i = 0; i < resBlock.length; i++) {
            for (int j = 0; j < resBlock[i].length; j++) {
                result.addToElement(i + rowOffset, j + colOffset, resBlock[i][j]);
            }
        }
    }
}
