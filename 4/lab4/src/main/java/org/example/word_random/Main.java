package org.example.word_random;

import org.example.utils.FileUtil;
import org.example.utils.MathUtil;
import org.example.utils.TextUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        List<String> lines = FileUtil.getTextFromFile("task1.txt");

        List<String> words = lines.stream()
                .parallel()
                .flatMap(x -> TextUtil.getWordsFromLine(x).stream())
                .toList();

        measureSpeedUp(words);
//        showProperties(words);
    }

    private static void measureSpeedUp(List<String> words) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        int experimentsCount = 4;

        long totalTime = 0;
        for (int i = 0; i < experimentsCount; i++) {
            long start = System.currentTimeMillis();
            pool.invoke(new WordForkTask(words, 0, words.size()));
            totalTime += System.currentTimeMillis() - start;
        }
        long poolAverageTime = totalTime / 4;
        System.out.println("Average Time (ForkJoinPool): " + poolAverageTime + " ms");

        totalTime = 0;
        for (int i = 0; i < experimentsCount; i++) {
            long start = System.currentTimeMillis();
            new WordSingleThread(words).process();
            totalTime += System.currentTimeMillis() - start;
        }
        long singleAverageTime = totalTime / 4;
        System.out.println("Average Time (SingleThread): " + singleAverageTime + " ms");

        System.out.println("Speedup is " + 1.0 * singleAverageTime / poolAverageTime);
    }

    private static void showProperties(List<String> words) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        System.out.println("Fork Join Pool: ");
        Map<Integer, Integer> poolMap = pool.invoke(new WordForkTask(words, 0, words.size()));
        poolMap.forEach((x, y) -> System.out.println("Length " + x + ": " + y));

        System.out.println("\nSingle Thread: ");
        Map<Integer, Integer> singleMap = new WordSingleThread(words).process();
        singleMap.forEach((x, y) -> System.out.println("Length " + x + ": " + y));

        double mean = MathUtil.getMean(poolMap);
        double variance = MathUtil.getVariance(poolMap);
        double deviation = MathUtil.getDeviation(poolMap);

        System.out.println("\nMean: " + mean);
        System.out.println("Variance: " + variance);
        System.out.println("Deviation: " + deviation);
    }

}
