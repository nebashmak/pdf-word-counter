package ru.word_counter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import ru.word_counter.file_source.ResourceAware;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        File file = new File(ResourceAware.getPath());
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
