package org.example.word_common;

import org.example.file_entities.Document;

import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class DocumentSearchTask extends RecursiveTask<Map<String, List<String>>> {
    private final Document document;

    DocumentSearchTask(Document document) {
        super();
        this.document = document;
    }

    @Override
    protected Map<String, List<String>> compute() {
        return WordSearcher.occurrencesCount(document);
    }
}
