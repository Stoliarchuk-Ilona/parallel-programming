package org.example.word_keywords;

import org.example.file_entities.Document;

import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class DocumentSearchTask extends RecursiveTask<Map<String, List<String>>> {
    private final Document document;
    private final List<String> searchedWords;

    public DocumentSearchTask(Document document, List<String> searchedWords) {
        super();
        this.document = document;
        this.searchedWords = searchedWords;
    }

    @Override
    protected Map<String, List<String>> compute() {
        return WordSearcher.occurrencesCount(document, searchedWords);
    }
}
