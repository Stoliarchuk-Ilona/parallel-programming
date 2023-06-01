package org.example.word_random;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class WordForkTask extends RecursiveTask<Map<Integer, Integer>> {
    private int threshold;
    private List<String> words;
    private int indexFrom;
    private int indexTo;

    public WordForkTask(List<String> words, int indexFrom, int indexTo) {
        this.words = words;
        this.indexFrom = indexFrom;
        this.indexTo = indexTo;
        this.threshold = 2500;
    }

    private Map<Integer, Integer> processWords() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = indexFrom; i < indexTo; i++) {
            String word = words.get(i);
            map.computeIfPresent(word.length(), (x, y) -> y + 1);
            map.putIfAbsent(word.length(), 1);
        }
        return map;
    }

//    private Map<Integer, Integer> processWords() {
//        Map<Integer, Integer> map = new HashMap<>();
//        for (int i = indexFrom; i < indexTo; i++) {
//            List<String> words = TextUtil.getWordsFromLine(linesToProcess.get(i));
//            for (String word : words) {
//                map.computeIfPresent(word.length(), (x, y) -> y + 1);
//                map.putIfAbsent(word.length(), 1);
//            }
//        }
//        return map;
//    }

    @Override
    protected Map<Integer, Integer> compute() {
        Map<Integer, Integer> result = new HashMap<>();
        if (indexTo - indexFrom <= threshold) {
            result.putAll(processWords());
        } else {
            int middle = (indexTo + indexFrom) / 2;
            WordForkTask taskLeft = new WordForkTask(words, indexFrom, middle);
            WordForkTask taskRight = new WordForkTask(words, middle, indexTo);
            taskRight.fork();
            Map<Integer, Integer> leftResult = taskLeft.compute();
            Map<Integer, Integer> rightResult = taskRight.join();

            result.putAll(leftResult);
            for (Map.Entry<Integer, Integer> rightEntry : rightResult.entrySet()) {
                result.computeIfPresent(rightEntry.getKey(), (x, y) -> y + rightEntry.getValue());
                result.putIfAbsent(rightEntry.getKey(), 1);
            }
        }
        return result;
    }
}
