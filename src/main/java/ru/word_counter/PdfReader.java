package ru.word_counter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.util.Objects.isNull;

public class PdfReader {

    private static PdfReader pdfReader;

    private PdfReader() {}

    public static PdfReader getPdfReader() {
        if (isNull(pdfReader)) pdfReader = new PdfReader();
        return pdfReader;
    }

    public void countWordsInPdf(String pdfFilePath) {
        File file = new File(pdfFilePath);
        System.out.println("Файл получен, идет обработка...");
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            Map<String, Integer> wordCountMap = new HashMap<>();

            Scanner sc = new Scanner(text).useDelimiter("[^a-zA-Z]+");
            while (sc.hasNext()) {
                String word = sc.next().toLowerCase();
                Integer frequency = wordCountMap.getOrDefault(word, 0);
                wordCountMap.put(word, ++frequency);
            }
            for (Map.Entry<String, Integer> entry: wordCountMap.entrySet())
                System.out.println(entry.getKey() + " = " + entry.getValue());

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
