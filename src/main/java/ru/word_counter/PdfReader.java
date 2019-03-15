package ru.word_counter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class PdfReader {

    private static PdfReader pdfReader;
    private final Map<String, Integer> wordCountMap = new HashMap<>();

    private PdfReader() {
    }

    public static PdfReader getPdfReader() {
        if (isNull(pdfReader)) pdfReader = new PdfReader();
        return pdfReader;
    }

    public Map<String, Integer> getWordCountMap() {
        return wordCountMap;
    }

    public void countWordsInPdf(String pdfFilePath) {
        File file = new File(pdfFilePath);
        System.out.println("Файл получен, идет обработка...");
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            Arrays.stream(text.split("\\PL+"))
                    .map(String::toLowerCase)
                    .forEach(word -> wordCountMap.merge(word, 1, Integer::sum));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void printWordCount(OutputStream out) {
        PrintStream writer = new PrintStream(out);
        wordCountMap.entrySet().stream()
                .filter(pair -> pair.getValue() > 2)
                .filter(pair -> pair.getKey().length() > 2)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(writer::println);
    }
}
