package org.example.word_random;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordSingleThread {
    private List<String> words;

    public WordSingleThread(List<String> words) {
        this.words = words;
    }

    public Map<Integer, Integer> process() {
        Map<Integer, Integer> result = new HashMap<>();
        for (String word : words) {
            result.computeIfPresent(word.length(), (x, y) -> y + 1);
            result.putIfAbsent(word.length(), 1);
        }
        return result;
    }
}
