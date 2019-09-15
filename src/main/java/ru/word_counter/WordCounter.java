package ru.word_counter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class WordCounter {

    public static final String SYBMOLS_BETWEEN_WORDS_PATTERN = "\\PL+";

    public WordCounter() {
    }

    public Map<String, Integer> countWordsInPdf(final String pdfFilePath) {
        System.out.println("Файл получен, идет обработка...");
        try (final PDDocument pdfDocument = PDDocument.load(new File(pdfFilePath))) {
            final String documentText = new PDFTextStripper().getText(pdfDocument);
            return countWordsInText(documentText);
        } catch (IOException ex) {
            System.out.println("Ошибка при делении тескта файла на слова с сообщением: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private Map<String, Integer> countWordsInText(String text) {
        return Arrays.stream(splitTextIntoWords(text))
                .map(String::toLowerCase)
                .collect(toMap(Function.identity(), word -> 1, Integer::sum));
    }

    private String[] splitTextIntoWords(String text) {
        return text.split(SYBMOLS_BETWEEN_WORDS_PATTERN);
    }
}
