package jar;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        File file = new File(ResourceAware.getPath());
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            Scanner scanner = new Scanner(text);
//            System.out.println(text);
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
