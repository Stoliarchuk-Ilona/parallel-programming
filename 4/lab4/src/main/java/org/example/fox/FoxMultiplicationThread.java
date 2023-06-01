package org.example.fox;

public class FoxMultiplicationThread extends Thread {
    private int[][] first;
    private int[][] second;
    private int rowOffset;
    private int colOffset;
    private int size;
    private Result result;

    public FoxMultiplicationThread(int[][] first, int[][] second, int rowOffset,
                                   int colOffset, int size, Result result) {
        this.result = result;
        this.first = first;
        this.second = second;
        this.rowOffset = rowOffset;
        this.colOffset = colOffset;
        this.size = size;
    }

    @Override
    public void run() {
        int rowFirstSize = size;
        int colSecondSize = size;

        if (rowOffset + size > first.length) {
            rowFirstSize = first.length - rowOffset;
        }

        if (colOffset + size > second[0].length) {
            colSecondSize = second[0].length - colOffset;
        }

        for (int k = 0; k < first.length; k += size) {
            int colFirstSize = size;
            int rowSecondSize = size;

            if (k + size > first[0].length) {
                colFirstSize = first[0].length - k;
            }

            if (k + size > second.length) {
                rowSecondSize = second.length - k;
            }

            int[][] blockFirst = copyBlock(first, rowOffset, rowOffset + rowFirstSize,
                    k, k + colFirstSize);
            int[][] blockSecond = copyBlock(second, k, k + rowSecondSize,
                    colOffset, colOffset + colSecondSize);

            int[][] resBlock = multiplyBlocks(blockFirst, blockSecond);
            for (int i = 0; i < resBlock.length; i++) {
                for (int j = 0; j < resBlock[i].length; j++) {
                    result.addToElement(i + rowOffset, j + colOffset, resBlock[i][j]);
                }
            }
        }
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
}
