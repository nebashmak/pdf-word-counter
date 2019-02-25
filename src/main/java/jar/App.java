package jar;

import file_path.ResourceAware;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

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
            Map<String, Integer> hashmap = new HashMap<>();

            Scanner sc = new Scanner(text).useDelimiter("\\d*\\b?\\n*\\s*\\W?\\W{1,3}\\W?\\s*\\n*\\b?\\d*");
            while (sc.hasNext()){
                String word = sc.next().toLowerCase();
                Integer frequency = hashmap.get(word);
                hashmap.put(word, frequency == null ? 1 : frequency + 1);
                }

            for (Map.Entry<String, Integer> entry: hashmap.entrySet())
                System.out.println(entry.getKey() + " = " + entry.getValue());

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
