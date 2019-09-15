package ru.word_counter;

import ru.word_counter.file_source.ResourceAware;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.Map;

public class WordCounterApp {

    private static final InputStream DEFAULT_INPUT = System.in;
    private static final OutputStream DEFAULT_OUTPUT = System.out;

    public static void main(String[] args) {
        final WordCounter wordCounter = new WordCounter();
        final Map<String, Integer> result = wordCounter
                .countWordsInPdf(new ResourceAware(DEFAULT_INPUT, DEFAULT_OUTPUT).getPath());
        print(result);
    }

    private static void print(Map<String, Integer> result) {
        result.entrySet().stream()
                .filter(pair -> pair.getValue() > 2)
                .filter(pair -> pair.getKey().length() > 2)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(System.out::println);
    }
}
