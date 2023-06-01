package org.example.word_keywords;

import org.example.file_entities.Folder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws IOException {
        Folder folder = Folder.fromDirectory(
                new File("D:\\KPI\\6 семестр\\Технології паралельних обчислень\\Лаби\\3"));

        List<String> searchedWords = new ArrayList<>();
        searchedWords.add("Java");
        searchedWords.add("Javascript");
        searchedWords.add("Web");
        searchedWords.add("C#");
        searchedWords.add("Development");
        searchedWords.add("Agile");
        searchedWords.add("Wait");
        searchedWords.add("Notify");
        searchedWords.forEach(String::toLowerCase);

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        Map<String, List<String>> result = pool.invoke(new FolderSearchTask(folder, searchedWords));

        result.forEach((key, value) ->
                System.out.println(key + ": " + value.size() + " occurrences"));
    }
}
