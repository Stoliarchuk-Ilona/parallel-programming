package org.example.word_keywords;

import org.example.file_entities.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordSearcher {

    private static String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    public static Map<String, List<String>> occurrencesCount(Document document,
                                                             List<String> searchedWords) {
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < document.getLines().size(); i++) {
            String line = document.getLines().get(i);
            for (String word : wordsIn(line)) {
                if (searchedWords.contains(word)) {
                    List<String> value;
                    if (map.containsKey(word)) {
                        value = map.get(word);
                    } else {
                        value = new ArrayList<>();
                    }
                    value.add(document.getName() + " - line " + (i + 1));
                    map.put(word, value);
                }
            }
        }
        return map;
    }
}
