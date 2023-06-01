package org.example.word_common;

import org.example.file_entities.Folder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws IOException {
        Folder folder = Folder.fromDirectory(
                new File("D:\\KPI\\6 семестр\\Технології паралельних обчислень\\Лаби\\3"));

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        Map<String, List<String>> result = pool.invoke(new FolderSearchTask(folder));

        result.forEach((key, value) ->
                System.out.println(key + ": " + value.size() + " occurrences"));
    }
}
