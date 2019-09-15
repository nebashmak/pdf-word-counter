package ru.word_counter;

import ru.word_counter.file_source.ResourceAware;

import java.io.InputStream;
import java.io.OutputStream;

public class WordCounterApp {

    private static final InputStream DEFAULT_INPUT = System.in;
    private static final OutputStream DEFAULT_OUTPUT = System.out;

    public static void main(String[] args) {
        final WordsCount wordsCount = new WordsCount();
        wordsCount.countWordsInPdf(new ResourceAware(DEFAULT_INPUT, DEFAULT_OUTPUT).getPath());
        wordsCount.printWordCount(System.out);
    }
}
